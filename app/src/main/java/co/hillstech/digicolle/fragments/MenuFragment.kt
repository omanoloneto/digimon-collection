package co.hillstech.digicolle.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.hillstech.digicolle.R
import co.hillstech.digicolle.Session
import co.hillstech.digicolle.activities.LoginActivity
import co.hillstech.digicolle.adapters.EdgeDecorator
import co.hillstech.digicolle.adapters.MenuAdapter
import co.hillstech.digicolle.models.Menu
import co.hillstech.digicolle.utils.showMessageDialog
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
            viewMenuList?.addItemDecoration(EdgeDecorator(8))
            viewMenuList?.adapter = MenuAdapter(inflateMenu(), it)
            viewMenuList?.layoutManager = LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun inflateMenu(): List<Menu> {
        var menus: MutableList<Menu> = mutableListOf()

        activity?.let{
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
            val preferences = it.getPreferences(Context.MODE_PRIVATE)

            preferences.edit()
                    .clear()
                    .apply()

            Session.user = null

            startActivity(Intent(it, LoginActivity::class.java))
        }
    }

}
