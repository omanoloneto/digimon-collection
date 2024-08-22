package co.hillstech.digicollection.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import co.hillstech.digicollection.BuildConfig
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.activities.bases.BaseFragmentActivity
import co.hillstech.digicollection.databinding.ActivityLobbyBinding
import co.hillstech.digicollection.fragments.HomeFragment
import co.hillstech.digicollection.fragments.LocationsFragment
import co.hillstech.digicollection.fragments.MenuFragment
import co.hillstech.digicollection.ui.eventList.EventListFragment
import com.squareup.picasso.Picasso

class LobbyActivity : BaseFragmentActivity() {

    private lateinit var binding: ActivityLobbyBinding

    private val fragments: Map<Int, Fragment> = mapOf(
        R.id.navi_partner to HomeFragment(),
        //R.id.layoutLocations to LocationsFragment(),
        //R.id.layoutEvents to EventListFragment(),
        //R.id.layoutMenu to MenuFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLobbyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupBottomNavigationMenu()
    }

    override fun onResume() {
        super.onResume()
        updateUserInfo()
    }

    private fun setupUI() {
        Session.user?.crest?.let { crest ->
            setStatusBarColor(crest.color)
            Picasso.get().load(crest.icon)
                .noPlaceholder()
                .into(binding.viewCrest)
        }
    }

    private fun updateUserInfo() {
        Session.user?.let { user ->
            binding.viewVersion.text = "Version ${BuildConfig.VERSION_NAME}"
            binding.viewUserName.text = user.name
            binding.viewWallet.text = "$ ${user.wallet}"
        }
    }

    private fun setupBottomNavigationMenu() {
        binding.viewBottomNavigation.setOnItemSelectedListener { item ->
            fragments[item.itemId]?.let {
                openFragment(it)
                selectedFragment = item.itemId
                true
            } ?: false
        }
        binding.viewBottomNavigation.selectedItemId = R.id.navi_partner
    }
}
