package co.hillstech.digicollection.activities.bases

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import kotlinx.android.synthetic.main.view_action_bar.*

abstract class BaseActivity : AppCompatActivity() {

    private var progressRing: ProgressDialog? = null

    protected open fun setupViews() {}

    //TODO: remove this after refactoring all code
    protected open fun setupActionBar() {
        setupActionBar()
    }

    protected open fun setupActionBar(title: String = this.localClassName) {
        viewActivityTitle.text = title

        viewBackArrow.setOnClickListener { onBackPressed() }

        Session.user?.crest?.color.let {
            setStatusBarColor(it)
            viewActionBar.setCardBackgroundColor(Color.parseColor(it))
        }
    }

    override fun onResume() {
        super.onResume()
        Session.user?.let{
            viewWallet.text = "$ ${it.wallet}"
        }
    }

    protected fun setStatusBarColor(color: String? = null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            color?.let {
                window.statusBarColor = Color.parseColor(color)
            } ?: run {
                window.statusBarColor = ContextCompat.getColor(this, R.color.regal_blue)
            }
        }
    }

    protected fun progressRingCall(context: Context, message: String = getString(R.string.loading)) {
        progressRing = ProgressDialog.show(context, "", message, true)
        progressRing?.show()
    }

    protected fun progressRingDismiss() {
        progressRing?.dismiss()
    }

}