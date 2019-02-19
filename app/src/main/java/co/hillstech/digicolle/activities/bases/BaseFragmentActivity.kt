package co.hillstech.digicolle.activities.bases

import android.support.v4.app.Fragment
import co.hillstech.digicolle.R

abstract class BaseFragmentActivity : BaseActivity() {

    protected var selectedFragment: Int? = null

    protected fun openFragment(fragment: Fragment, previousFragment: Fragment?) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.setCustomAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom)

        if (supportFragmentManager.findFragmentByTag(fragment.tag) == null) {
            transaction.replace(R.id.viewContainer, fragment, fragment::class.simpleName)
        }

        if (previousFragment != null) transaction.hide(previousFragment)
        transaction.show(fragment)
        transaction.commit()
    }

}