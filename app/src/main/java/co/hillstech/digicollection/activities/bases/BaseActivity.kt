package co.hillstech.digicollection.activities.bases

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.databinding.ViewActionBarBinding

abstract class BaseActivity : AppCompatActivity() {

    private var progressRing: ProgressDialog? = null
    private lateinit var binding: ViewActionBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewActionBarBinding.inflate(layoutInflater)
    }

    protected open fun setupViews() {
        // Este método pode ser sobrescrito nas subclasses para configurar as views.
    }

    protected open fun setupActionBar(title: String = this.localClassName) {
        binding.viewActivityTitle.text = title

        binding.viewBackArrow.setOnClickListener { onBackPressed() }

        Session.user?.crest?.color?.let { crestColor ->
            setStatusBarColor(crestColor)
            binding.viewActionBar.setCardBackgroundColor(Color.parseColor(crestColor))
        }
    }

    override fun onResume() {
        super.onResume()
        updateWallet()
    }

    private fun updateWallet() {
        Session.user?.wallet?.let {
            binding.viewWallet.text = "$ ${it}"
        }
    }

    protected fun setStatusBarColor(color: String? = null) {
        val statusBarColor = color?.let { Color.parseColor(it) }
            ?: ContextCompat.getColor(this, R.color.regal_blue)
        window.statusBarColor = statusBarColor
    }

    protected fun showProgressRing(
        context: Context,
        message: String = getString(R.string.loading)
    ) {
        progressRing = ProgressDialog(context).apply {
            setMessage(message)
            setCancelable(false)
            show()
        }
    }

    protected fun dismissProgressRing() {
        progressRing?.dismiss()
    }
}
