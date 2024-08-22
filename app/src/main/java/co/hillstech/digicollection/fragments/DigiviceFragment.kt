package co.hillstech.digicollection.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import co.hillstech.digicollection.R
import co.hillstech.digicollection.databinding.FragmentDigiviceBinding
import co.hillstech.digicollection.ui.digiviceList.DigiviceListActivity
import com.squareup.picasso.Picasso

class DigiviceFragment : DialogFragment() {

    private var _binding: FragmentDigiviceBinding? = null
    private val binding get() = _binding!!

    var model: String = ""
    var maxLevel: String = ""
    var cooldown: Int = 0
    var resume: String = ""
    var image: String? = null
    var showButtons: Boolean = true
    var blockBack: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDigiviceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupButtons()
    }

    private fun setupUI() {
        binding.viewDigiviceModel.text = model
        binding.viewResume.text = resume
        binding.viewMaxLevel.text = maxLevel
        binding.viewCooldown.text =  "${cooldown} " + getString(R.string.minutes)

        image?.let {
            Picasso.get().load(it)
                .placeholder(R.drawable.placeholder)
                .into(binding.viewDigiviceImage)
        }

        if (blockBack) {
            isCancelable = false
        }
    }

    private fun setupButtons() {
        if (showButtons) {
            binding.viewConfirmButton.setOnClickListener {
                startActivity(Intent(requireActivity(), DigiviceListActivity::class.java))
                dismiss()
            }

            binding.viewCancelButton.setOnClickListener { dismiss() }
        } else {
            binding.viewConfirmButton.visibility = View.GONE
            binding.viewCancelButton.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
