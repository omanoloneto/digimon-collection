package co.hillstech.digicollection.utils

import android.support.design.widget.BottomSheetDialog
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import co.hillstech.digicollection.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_alert_dialog.view.*
import kotlinx.android.synthetic.main.fragment_char_dialog.view.*


fun AppCompatActivity.showBottomSheetDialog(title: String = "",
                                            message: String = "",
                                            image: String = "",
                                            confirmButtonLabel: String? = null,
                                            cancelButtonLabel: String? = null,
                                            confirmButtonAction: () -> Unit = {},
                                            cancelButtonAction: () -> Unit = {},
                                            isCancelable: Boolean = false) {

    val dialog = BottomSheetDialog(this)

    val view = layoutInflater.inflate(R.layout.fragment_alert_dialog, null)

    with(view) {
        viewAlertDialogTitle.text = title
        viewAlertDialogMessage.text = message

        if (image != "") {
            viewAlertDialogTitle.gravity = Gravity.CENTER
            viewAlertDialogMessage.gravity = Gravity.CENTER

            viewAlertDialogImage.visibility = View.VISIBLE

            Picasso.get().load(image)
                    .placeholder(R.drawable.placeholder)
                    .into(viewAlertDialogImage)
        }

        viewConfirmButton.text = confirmButtonLabel ?: context.getString(R.string.ok)
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

    dialog.setContentView(view)
    dialog.setCancelable(isCancelable)
    dialog.show()
}

fun AppCompatActivity.showBottomCharDialog(charName: String,
                                           messages: MutableList<String>,
                                           image: Int,
                                           isCancelable: Boolean = false) {

    val dialog = BottomSheetDialog(this)

    val view = layoutInflater.inflate(R.layout.fragment_char_dialog, null)

    var messagePosition = 0

    with(view) {
        viewCharDialogTitle.text = charName
        viewCharDialogImage.setImageDrawable(context.getDrawable(image))
        viewCharDialogMessage.text = messages[0]

        if (messages.size > 1) {
            viewCharConfirmButton.text = context.getString(R.string.next)
            viewCharConfirmButton.setOnClickListener {
                messagePosition++
                viewCharDialogMessage.text = messages[messagePosition]
                if (messages.size == (messagePosition + 1)) {
                    viewCharConfirmButton.text = context.getString(R.string.close)
                    viewCharConfirmButton.setOnClickListener { dialog.dismiss() }
                }
            }
        } else {
            viewCharConfirmButton.text = context.getString(R.string.close)
            viewCharConfirmButton.setOnClickListener { dialog.dismiss() }
        }
    }

    dialog.setContentView(view)
    dialog.setCancelable(isCancelable)
    dialog.show()
}