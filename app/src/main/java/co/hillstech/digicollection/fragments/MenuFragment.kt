package co.hillstech.digicollection.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.ui.login.LoginActivity
import co.hillstech.digicollection.activities.StoreActivity
import co.hillstech.digicollection.adapters.EdgeDecorator
import co.hillstech.digicollection.adapters.MenuAdapter
import co.hillstech.digicollection.models.Menu
import co.hillstech.digicollection.ui.digiBank.DigiBankActivity
import co.hillstech.digicollection.ui.digiDex.DigiDexActivity
import co.hillstech.digicollection.ui.scanList.ScanListActivity
import co.hillstech.digicollection.utils.showMessageDialog
import com.onesignal.OneSignal
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMenuList()

    }

    private fun setupMenuList() {
        activity?.let {
            viewMenuList?.addItemDecoration(EdgeDecorator(16))
            viewMenuList?.adapter = MenuAdapter(inflateMenu(), it)
            viewMenuList?.layoutManager = LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun inflateMenu(): List<Menu> {
        var menus: MutableList<Menu> = mutableListOf()

        activity?.let{
            /*menus.add(Menu(getString(R.string.digi_radar), it.getDrawable(R.drawable.ic_globe)) {
                if(Session.user!!.digivice == null){
                    it.showMessageDialog(getString(R.string.warning),
                            getString(R.string.you_must_have_a_digivice_to_use_the_radar),
                            positiveButtonLabel = getString(R.string.ok))
                }else {
                    startActivity(Intent(it, RadarActivity::class.java))
                }
            })*/

            menus.add(Menu(getString(R.string.digidex), it.getDrawable(R.drawable.open_book)) {
                startActivity(Intent(it, DigiDexActivity::class.java))
            })

            menus.add(Menu(getString(R.string.scan_list), it.getDrawable(R.drawable.todo_list)) {
                startActivity(Intent(it, ScanListActivity::class.java))
            })

            menus.add(Menu(getString(R.string.data_radar), it.getDrawable(R.drawable.search_location)) {
                startActivity(Intent(it, ScanListActivity::class.java))
            })

            menus.add(Menu(getString(R.string.digibank), it.getDrawable(R.drawable.box)) {
                startActivity(Intent(it, DigiBankActivity::class.java))
            })

            menus.add(Menu(getString(R.string.store), it.getDrawable(R.drawable.store)) {
                startActivity(Intent(it, StoreActivity::class.java))
            })

            menus.add(Menu(getString(R.string.logout), it.getDrawable(R.drawable.logout)) {
                it.showMessageDialog(getString(R.string.exit), getString(R.string.are_you_sure_you_want_to_logout),
                        positiveButtonLabel = getString(R.string.no),
                        negativeButtonLabel = getString(R.string.yes),
                        negativeButtonAction = ::logout)
            })
        }

        return menus
    }

    private fun logout() {
        activity?.let {
            val preferences = it.applicationContext
                    .getSharedPreferences("DigiCollePref", AppCompatActivity.MODE_PRIVATE)

            preferences.edit()
                    .clear()
                    .commit()

            OneSignal.deleteTag("username")

            Session.user = null

            startActivity(Intent(it, LoginActivity::class.java))
        }
    }

}
