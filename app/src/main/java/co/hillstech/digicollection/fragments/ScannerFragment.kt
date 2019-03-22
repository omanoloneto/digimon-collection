package co.hillstech.digicollection.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.ui.battleResult.BattleResultActivity
import com.squareup.picasso.Picasso
import com.wooplr.spotlight.utils.SpotlightSequence
import kotlinx.android.synthetic.main.fragment_scanner.*

class ScannerFragment : DialogFragment() {

    var wildId: Int = 0
    var species: String = ""
    var image: String? = null
    var progress: Int = 0
    var coins: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scanner, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewScannerSpecies.text = species
        viewProgressBar.progress = progress
        viewScannerPercent.text = "$progress%"
        viewScannerCoins.text = getString(R.string.you_received)+" $ $coins"

        image?.let {
            Picasso.get().load(it)
                    .placeholder(R.drawable.placeholder)
                    .into(viewScannerImage)
        }

        viewConfirmButton.setOnClickListener { getBattle() }
        viewCancelButton.setOnClickListener { dismiss() }

        //showSpotlights()
    }

    private fun getBattle(){
        Session.wild = wildId
        startActivity(Intent(activity,BattleResultActivity::class.java))
        dismiss()
    }

    /*private fun showSpotlights() {
        SpotlightSequence.getInstance(activity, Session.spotlightConfig)
                .addSpotlight(viewScannerPercent, "Scan", "Quando você encontra um Digimon infectado, escaneia um pouco do código dele. Junte 100% de scan de um Digimon para gerar um da mesma espécie para você.", "tutorialScan")
                .addSpotlight(viewConfirmButton, "Batalha", "Clique aqui para ver o resultado de uma batalha entre o seu parceiro e o Digimon encontrado.", "tutorialBattle")
                .addSpotlight(viewScannerCoins, "DigiCoins", "Você sempre ganha alguns DigiCoins quando encontra um Digimon Infectado.", "tutorialBattleDigiCoins")
                .startSequence()
    }*/

}