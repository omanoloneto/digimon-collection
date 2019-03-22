package co.hillstech.digicollection.activities

import android.graphics.Color
import android.os.Bundle
import co.hillstech.digicollection.BuildConfig
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.bases.BaseFragmentActivity
import co.hillstech.digicollection.fragments.HomeFragment
import co.hillstech.digicollection.fragments.MenuFragment
import com.squareup.picasso.Picasso
import com.wooplr.spotlight.utils.SpotlightSequence
import kotlinx.android.synthetic.main.activity_lobby.*

class LobbyActivity : BaseFragmentActivity() {

    private val fragments by lazy {
        mapOf(
                R.id.navi_partner to HomeFragment(),
                R.id.layoutMenu to MenuFragment()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)

        Session.user?.crest?.let {
            setStatusBarColor(it.color)
            viewActionBar.setCardBackgroundColor(Color.parseColor(it.color))

            Picasso.get().load(it.icon)
                    .noPlaceholder()
                    .into(viewCrest)
        }

        setupBottomNavigationMenu()
    }

    override fun onResume() {
        super.onResume()
        Session.user?.let {
            viewVersion.text = "Versão ${BuildConfig.VERSION_NAME}"
            viewUserName.text = it.name
            viewWallet.text = "$ ${it.wallet}"
        }
    }

    private fun setupBottomNavigationMenu() {
        viewBottomNavigation.setOnNavigationItemSelectedListener {
            fragments[it.itemId]?.let { frag ->
                openFragment(frag, fragments[selectedFragment])
                selectedFragment = it.itemId

                return@setOnNavigationItemSelectedListener true
            }

            return@setOnNavigationItemSelectedListener false
        }

        viewBottomNavigation.selectedItemId = R.id.navi_partner
    }
}
