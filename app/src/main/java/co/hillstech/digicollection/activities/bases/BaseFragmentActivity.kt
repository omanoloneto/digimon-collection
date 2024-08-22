package co.hillstech.digicollection.activities.bases

import androidx.fragment.app.Fragment
import co.hillstech.digicollection.R

abstract class BaseFragmentActivity : BaseActivity() {

    protected var selectedFragment: Int? = null

    fun openFragment(fragment: Fragment, previousFragment: Fragment? = null) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)

        val fragmentTag = fragment::class.simpleName

        if (supportFragmentManager.findFragmentByTag(fragmentTag) == null) {
            transaction.replace(R.id.viewContainer, fragment, fragmentTag)
        }

        previousFragment?.let { transaction.hide(it) }

        transaction.show(fragment)
        transaction.commit()
    }
}
