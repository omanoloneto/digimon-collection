package co.hillstech.digicolle.activities

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import co.hillstech.digicolle.R
import co.hillstech.digicolle.Session
import co.hillstech.digicolle.activities.bases.BaseFragmentActivity
import co.hillstech.digicolle.fragments.HomeFragment
import co.hillstech.digicolle.fragments.MenuFragment
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

        Session.user?.crest?.color.let {
            setStatusBarColor(it)
            viewActionBar.setCardBackgroundColor(Color.parseColor(it))
        }

        setupBottomNavigationMenu()
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
