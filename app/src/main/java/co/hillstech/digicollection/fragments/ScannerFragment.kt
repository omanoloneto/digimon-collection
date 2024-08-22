package co.hillstech.digicollection.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import co.hillstech.digicollection.R
import co.hillstech.digicollection.Session
import co.hillstech.digicollection.databinding.FragmentScannerBinding
import co.hillstech.digicollection.ui.battleResult.BattleResultActivity
import com.squareup.picasso.Picasso

class ScannerFragment : DialogFragment() {

    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!

    var wildId: Int = 0
    var species: String = ""
    var image: String? = null
    var progress: Int = 0
    var coins: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()

        binding.viewConfirmButton.setOnClickListener { getBattle() }
        binding.viewCancelButton.setOnClickListener { dismiss() }

        isCancelable = false
    }

    private fun setupUI() {
        binding.viewScannerSpecies.text = species
        binding.viewProgressBar.progress = progress
        binding.viewScannerPercent.text = "$progress%"
        binding.viewScannerCoins.text = getString(R.string.you_received) + " $ $coins"

        image?.let {
            Picasso.get().load(it)
                .placeholder(R.drawable.placeholder)
                .into(binding.viewScannerImage)
        }
    }

    private fun getBattle() {
        Session.wild = wildId
        startActivity(Intent(activity, BattleResultActivity::class.java))
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
