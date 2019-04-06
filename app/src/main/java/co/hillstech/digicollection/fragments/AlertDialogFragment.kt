package co.hillstech.digicollection.fragments

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.view.View
import co.hillstech.digicollection.R
import kotlinx.android.synthetic.main.fragment_alert_dialog.*

class AlertDialogFragment(context: Context) : BottomSheetDialog(context) {

    var title: String = ""
    var message: String = ""
    var confirmButtonLabel: String? = null
    var cancelButtonLabel: String? = null
    var confirmButtonAction: () -> Unit = {}
    var cancelButtonAction: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(layoutInflater.inflate(R.layout.fragment_alert_dialog, null))
        setCancelable(false)

        setupViews()
    }

    private fun setupViews() {
        viewAlertDialogTitle.text = title
        viewAlertDialogMessage.text = message

        viewConfirmButton.text = confirmButtonLabel ?: context.getString(R.string.ok)
        viewConfirmButton.setOnClickListener {
            confirmButtonAction()
            dismiss()
        }

        cancelButtonLabel?.let {
            viewCancelButton.visibility = View.VISIBLE
            viewCancelButton.text = it
            viewCancelButton.setOnClickListener {
                cancelButtonAction()
                dismiss()
            }
        }
    }

}