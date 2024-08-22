package co.hillstech.digicollection.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.databinding.FragmentHomeBinding
import co.hillstech.digicollection.ui.evolutionList.EvolutionListActivity
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var preferences: SharedPreferences

    private var isSpotlightShowed: Boolean = false
    private var isDigiviceSpotlightShowed: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences =
            requireActivity().getSharedPreferences("DigiCollePref", AppCompatActivity.MODE_PRIVATE)
        verifySpotlights()
    }

    private fun verifySpotlights() {
        isSpotlightShowed = preferences.getString("spotlights", null) != null
        isDigiviceSpotlightShowed = preferences.getString("digiviceSpotlight", null) != null
    }

    override fun onResume() {
        super.onResume()
        updateUserHome()
    }

    private fun showSpotlights() {
        /* val activity = requireActivity()

        val spotlights = SpotlightSequence.getInstance(activity, Session.spotlightConfig)
            .addSpotlight(
                binding.viewCrest,
                "Crest of ${Session.user?.crest?.virtue}",
                "Este é o seu brasão.\nEle representa uma virtude sua, e será muito importante para evoluir seus Digimons.",
                "tutorialCrest"
            )
            .addSpotlight(
                binding.viewWallet,
                "Carteira",
                "Aqui estão suas DigiCoins.\nVocê vai usá-las para comprar itens e melhorias na loja.",
                "tutorialWallet"
            )
            .addSpotlight(
                binding.viewPartnerImage,
                "Parceiro",
                "Este é o seu parceiro.\nÉ com ele que você fará as primeiras batalhas, e é ele que te ajudará no começo de sua jornada.",
                "tutorialPartner"
            )

        Session.user?.digivice?.let {
            spotlights.addSpotlight(
                binding.viewDigiviceImage,
                "Digivice",
                "Aqui está o seu Digivice.\nToque nele para ver mais informações.",
                "tutorialDigivice"
            )

            preferences.edit()
                .putString("digiviceSpotlight", "showed")
                .apply()
        }

        spotlights.addSpotlight(
            binding.viewExperience,
            "Exp.",
            "Esta é a experiência do seu parceiro. Batalhe para acumular mais experiência para poder evoluir.",
            "tutorialExpHome"
        ).addSpotlight(
            binding.viewLevel,
            "Nível",
            "Aqui você pode ver o nível que seu Digimon está. Para passar de nível, deve encher a barra de experiência primeiro.",
            "tutorialLevelHome"
        )

        preferences.edit()
            .putString("spotlights", "showed")
            .apply()

         */
    }

    private fun updateUserHome() {
        Session.user?.partner?.let { partner ->
            val baseExp =
                (partner.type * ((partner.type * 1000) - ((partner.type * 1000) * 0.5))).toInt()

            binding.viewPartnerName.text = partner.nick ?: partner.species
            binding.viewExperience.text = partner.experience.toString()
            binding.viewExpToEvolve.text = "/ $baseExp"
            binding.viewLevel.text = Session.monsterLevel(requireActivity(), partner.type)

            binding.viewProgressBar.max = baseExp
            binding.viewProgressBar.progress = partner.experience

            Picasso.get().load(partner.image)
                .noPlaceholder()
                .into(binding.viewPartnerImage)

            binding.layoutPartner.setOnClickListener {
                startActivity(Intent(activity, EvolutionListActivity::class.java))
            }
        }

        Session.user?.digivice?.let { digivice ->
            binding.layoutDigivice.visibility = View.VISIBLE
            binding.layoutDigiviceModel.text = "${digivice.model} of ${Session.user?.crest?.virtue}"

            Picasso.get().load(digivice.image)
                .noPlaceholder()
                .into(binding.viewDigiviceImage)

            val digiviceFragment = DigiviceFragment().apply {
                model = digivice.model
                cooldown = digivice.cooldown
                maxLevel = digivice.maxLevel.toString()
                resume = digivice.resume
                image = digivice.image
            }

            binding.layoutDigivice.setOnClickListener {
                digiviceFragment.show(requireActivity().supportFragmentManager, "DIGIVICE_FRAGMENT")
            }

            if (isSpotlightShowed && !isDigiviceSpotlightShowed) {
                showDigiviceSpotlight()
            }
        }
    }

    private fun showDigiviceSpotlight() {
        /*SpotlightView.Builder(requireActivity()).setConfiguration(Session.spotlightConfig)
            .target(binding.viewDigiviceImage)
            .headingTvText("Digivice")
            .subHeadingTvText("Aqui está o seu Digivice.\nToque nele para ver mais informações.")
            .usageId("tutorialDigivice") // UNIQUE ID
            .show()*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
