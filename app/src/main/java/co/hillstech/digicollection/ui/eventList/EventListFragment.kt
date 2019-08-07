package co.hillstech.digicollection.ui.eventList

import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.adapters.EdgeDecorator
import co.hillstech.digicollection.bases.BaseFragment
import co.hillstech.digicollection.models.Event
import co.hillstech.digicollection.utils.showBottomCharDialog
import co.hillstech.digicollection.utils.showBottomSheetDialog
import co.hillstech.digicollection.utils.showToast
import co.hillstech.digicollection.utils.timestampToDate
import kotlinx.android.synthetic.main.fragment_event_list.*

class EventListFragment : BaseFragment(), EventListPresenter.View {

    private var presenter = EventListPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_event_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.view = this
        presenter.requestEventList()

        setProgressRingColor()
    }

    private fun setProgressRingColor() {
        Session.user?.crest?.color.let {
            viewProgressRing.indeterminateDrawable.setColorFilter(Color.parseColor(it), PorterDuff.Mode.MULTIPLY)
        }
    }

    override fun showProgressRing() {
        layoutContainer.visibility = View.GONE
        viewProgressRing.visibility = View.VISIBLE
    }

    override fun hideProgressRing() {
        viewProgressRing.visibility = View.GONE
        layoutContainer.visibility = View.VISIBLE
    }

    override fun inflateEventList() {
        viewEventList?.run {
            addItemDecoration(EdgeDecorator(16))
            adapter = EventListAdapter(presenter.getEventList(), ::onEventClick)
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun showTutorial() {
        if (!isTutorialShowed()) {
            (activity as AppCompatActivity).showBottomCharDialog(
                    charName = "Genai",
                    image = R.drawable.genai_dialog_window,
                    messages = mutableListOf(
                            "Esta aqui é a aba de Eventos, nela você pode visualizar todos os eventos antigos e atuais.",
                            "Se clicar em um evento vai ver as informações dele, e a data em que ele terminou, ou terminará no caso dos eventos atuais.",
                            "Falando nisso, os eventos atuais ficam marcados com uma tag \"Ativo\"."
                    )
            )
            setTutorialShowed()
        }
    }

    private fun isTutorialShowed(): Boolean {
        val preferences = activity!!.getSharedPreferences("DigiCollePref", MODE_PRIVATE)
        return preferences.getString("eventListTutorial1", null) != null
    }

    private fun setTutorialShowed() {
        val preferences = activity!!.getSharedPreferences("DigiCollePref", MODE_PRIVATE)

        preferences.edit()
                .putString("eventListTutorial1", "cleared")
                .commit()
    }

    private fun onEventClick(event: Event) {
        activity?.let {
            (it as AppCompatActivity).showBottomSheetDialog(
                    title = event.name,
                    message = "${event.description}\n\nFim do evento: ${it.timestampToDate(event.finishDate)}",
                    isCancelable = true
            )
        } ?: run {
            activity?.showToast(getString(R.string.error_on_show_event_informations))
        }

    }

}
