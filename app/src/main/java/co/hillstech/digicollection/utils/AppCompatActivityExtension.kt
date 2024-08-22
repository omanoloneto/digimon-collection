package co.hillstech.digicollection.utils

import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import co.hillstech.digicollection.R
import co.hillstech.digicollection.databinding.FragmentAlertDialogBinding
import co.hillstech.digicollection.databinding.FragmentCharDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso

fun AppCompatActivity.showBottomSheetDialog(
    title: String = "",
    message: String = "",
    image: String = "",
    confirmButtonLabel: String? = null,
    cancelButtonLabel: String? = null,
    confirmButtonAction: () -> Unit = {},
    cancelButtonAction: () -> Unit = {},
    isCancelable: Boolean = false
) {
    val dialog = BottomSheetDialog(this)
    val binding = FragmentAlertDialogBinding.inflate(layoutInflater)

    with(binding) {
        viewAlertDialogTitle.text = title
        viewAlertDialogMessage.text = message

        if (image.isNotEmpty()) {
            viewAlertDialogTitle.gravity = Gravity.CENTER
            viewAlertDialogMessage.gravity = Gravity.CENTER

            viewAlertDialogImage.visibility = View.VISIBLE

            Picasso.get().load(image)
                .placeholder(R.drawable.placeholder)
                .into(viewAlertDialogImage)
        }

        //TODO: implement getString again
        viewConfirmButton.text = confirmButtonLabel ?: "Ok"
        viewConfirmButton.setOnClickListener {
            confirmButtonAction()
            dialog.dismiss()
        }

        cancelButtonLabel?.let {
            viewCancelButton.visibility = View.VISIBLE
            viewCancelButton.text = it
            viewCancelButton.setOnClickListener {
                cancelButtonAction()
                dialog.dismiss()
            }
        }
    }

    dialog.setContentView(binding.root)
    dialog.setCancelable(isCancelable)
    dialog.show()
}

fun AppCompatActivity.showBottomCharDialog(
    charName: String,
    messages: MutableList<String>,
    image: Int,
    isCancelable: Boolean = false
) {
    val dialog = BottomSheetDialog(this)
    val binding = FragmentCharDialogBinding.inflate(layoutInflater)

    var messagePosition = 0

    with(binding) {
        viewCharDialogTitle.text = charName
        viewCharDialogImage.setImageDrawable(getDrawable(image))
        viewCharDialogMessage.text = messages[0]

        if (messages.size > 1) {
            viewCharConfirmButton.text = "Next"
            viewCharConfirmButton.setOnClickListener {
                messagePosition++
                viewCharDialogMessage.text = messages[messagePosition]
                if (messages.size == (messagePosition + 1)) {
                    viewCharConfirmButton.text = "Close"
                    viewCharConfirmButton.setOnClickListener { dialog.dismiss() }
                }
            }
        } else {
            viewCharConfirmButton.text = "Close"
            viewCharConfirmButton.setOnClickListener { dialog.dismiss() }
        }
    }

    dialog.setContentView(binding.root)
    dialog.setCancelable(isCancelable)
    dialog.show()
}
