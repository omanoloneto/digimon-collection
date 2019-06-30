package co.hillstech.digicollection.utils

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

fun Context.showMessageDialog(title: String, message: String,
                              positiveButtonLabel: String? = "", positiveButtonAction: () -> Unit = {},
                              negativeButtonLabel: String? = "", negativeButtonAction: () -> Unit = {}) {
    val dialogBuilder = android.app.AlertDialog.Builder(this)
    dialogBuilder.setTitle(title)
    dialogBuilder.setMessage(message)

    positiveButtonLabel?.let {
        dialogBuilder.setPositiveButton(positiveButtonLabel, android.content.DialogInterface.OnClickListener { dialog, whichButton ->
            positiveButtonAction()
        })
    }

    negativeButtonLabel?.let {
        dialogBuilder.setNegativeButton(negativeButtonLabel, android.content.DialogInterface.OnClickListener { dialog, whichButton ->
            negativeButtonAction()
        })
    }

    dialogBuilder.show()
}

fun Context.timestampToDate(timestamp: String): String {
    return SimpleDateFormat("dd/MM/yyyy").format(Date(timestamp.toLong() * 1000)).toString()
}

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}