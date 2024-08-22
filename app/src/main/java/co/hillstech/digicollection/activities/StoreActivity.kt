package co.hillstech.digicollection.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Retrofit.UserService
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.adapters.DigiviceAdapter
import co.hillstech.digicollection.adapters.ItemAdapter
import co.hillstech.digicollection.databinding.ActivityStoreBinding
import co.hillstech.digicollection.fragments.DigiviceFragment
import co.hillstech.digicollection.models.BooleanResponse
import co.hillstech.digicollection.models.Digivice
import co.hillstech.digicollection.models.Item
import co.hillstech.digicollection.models.StoreResponse
import co.hillstech.digicollection.services.apis.UserAPI
import co.hillstech.digicollection.utils.showBottomSheetDialog
import co.hillstech.digicollection.utils.showMessageDialog
import co.hillstech.digicollection.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActivity()
        getItems()
    }

    private fun updateWallet() {
        Session.user?.let {
            UserService().user().updateWallet(it.id.toString(), it.wallet)
                .enqueue(object : Callback<BooleanResponse?> {
                    override fun onResponse(
                        call: Call<BooleanResponse?>,
                        response: Response<BooleanResponse?>
                    ) {
                        response.body()?.let {
                            binding.include.viewWallet.text = "$ ${Session.user?.wallet}"
                        } ?: run {
                            logError("Failed to update wallet: null response body")
                        }
                    }

                    override fun onFailure(call: Call<BooleanResponse?>, t: Throwable) {
                        logError("Failed to update wallet", t)
                    }
                })
        }
    }

    private fun updateDigivice(digiviceId: Int, equip: Boolean = true) {
        Session.user?.let {
            UserService().user().updateDigivice(it.id.toString(), digiviceId, equip)
                .enqueue(object : Callback<BooleanResponse?> {
                    override fun onResponse(
                        call: Call<BooleanResponse?>,
                        response: Response<BooleanResponse?>
                    ) {
                        response.body()?.let {
                            binding.viewDigivicesList.adapter?.notifyDataSetChanged()
                        } ?: run {
                            logError("Failed to update digivice: null response body")
                        }
                    }

                    override fun onFailure(call: Call<BooleanResponse?>, t: Throwable) {
                        logError("Failed to update digivice", t)
                    }
                })
        }
    }

    private fun digiviceDetails(digivice: Digivice) {
        DigiviceFragment().apply {
            model = digivice.model
            cooldown = digivice.cooldown
            maxLevel = digivice.maxLevel.toString(this@StoreActivity)
            resume = digivice.resume
            image = digivice.image
            showButtons = false
            blockBack = false
        }.show(supportFragmentManager, "DIGIVICE_FRAGMENT")
    }

    private fun changeDigivice(digivice: Digivice) {
        showMessageDialog(
            getString(R.string.warning),
            getString(R.string.do_you_want_change_your_digivice) + " ${digivice.model}?",
            positiveButtonLabel = getString(R.string.yes),
            negativeButtonLabel = getString(R.string.no),
            positiveButtonAction = {
                Session.user?.let {
                    it.digivice = digivice
                    updateDigivice(digivice.id, true)
                }
            },
            negativeButtonAction = {
                updateDigivice(digivice.id, false)
            }
        )
    }

    private fun getItems() {
        showProgressRing()

        UserService().store().getItems(Session.user?.id.toString())
            .enqueue(object : Callback<StoreResponse?> {
                override fun onResponse(
                    call: Call<StoreResponse?>,
                    response: Response<StoreResponse?>
                ) {
                    hideProgressRing()
                    response.body()?.let { storeResponse ->
                        setupAdapters(storeResponse)
                    } ?: run {
                        logError("Failed to fetch store items: null response body")
                    }
                }

                override fun onFailure(call: Call<StoreResponse?>, t: Throwable) {
                    hideProgressRing()
                    logError("Failed to fetch store items", t)
                }
            })
    }

    private fun setupAdapters(storeResponse: StoreResponse) {
        binding.apply {
            viewDigivicesList.adapter = DigiviceAdapter(
                storeResponse.digivices,
                this@StoreActivity,
                ::updateWallet,
                ::updateDigivice,
                ::changeDigivice,
                ::digiviceDetails
            )
            viewDigivicesList.layoutManager =
                LinearLayoutManager(this@StoreActivity, LinearLayoutManager.HORIZONTAL, false)

            viewChargeItemList.adapter = ItemAdapter(storeResponse.chargeChips, ::itemDetails)
            viewChargeItemList.layoutManager =
                LinearLayoutManager(this@StoreActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun itemDetails(item: Item) {
        showBottomSheetDialog(
            title = item.name,
            description = item.description,
            imageUrl = item.image,
            confirmButtonLabel = getString(R.string.buy),
            confirmButtonAction = { getItemToUser(item, Session.user!!.id) },
            cancelButtonLabel = getString(R.string.back),
            isCancelable = true
        )
    }

    private fun getItemToUser(item: Item, userId: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = UserAPI.addItemToBag(item.id, userId).await()
                if (response.status) {
                    onAddItemToBagSuccess(item)
                } else {
                    onAddItemToBagError()
                }
            } catch (e: Exception) {
                logError("Failed to add item to bag", e)
                onAddItemToBagError()
            }
        }
    }

    private fun onAddItemToBagSuccess(item: Item) {
        lifecycleScope.launch(Dispatchers.Main) {
            Session.user?.let {
                it.wallet -= item.price
                updateWallet()
                showToast(getString(R.string.purchase_successful))
            }
        }
    }

    private fun onAddItemToBagError() {
        lifecycleScope.launch(Dispatchers.Main) {
            showToast(getString(R.string.error_adding_item))
        }
    }

    private fun setupActivity() {
        binding.apply {
            viewActivityTitle.text = getString(R.string.store)
            viewWallet.text = "$ ${Session.user?.wallet}"
            viewBackArrow.setOnClickListener { onBackPressed() }
        }

        Session.user?.crest?.color?.let { color ->
            setStatusBarColor(color)
            binding.viewActionBar.setCardBackgroundColor(Color.parseColor(color))
        }
    }

    private fun showProgressRing() {
        // Implement the method to show a loading indicator
    }

    private fun hideProgressRing() {
        // Implement the method to hide the loading indicator
    }

    private fun logError(message: String, t: Throwable? = null) {
        Log.e("StoreActivity", message, t)
    }
}
