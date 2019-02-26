package co.hillstech.digicolle.utils

import android.content.Context

fun Context.showMessageDialog(title: String, message: String,
                       positiveButtonLabel: String?, positiveButtonAction: () -> Unit = {},
                       negativeButtonLabel: String?, negativeButtonAction: () -> Unit = {}){
    val dialogBuilder = android.app.AlertDialog.Builder(this)
    dialogBuilder.setTitle(title)
    dialogBuilder.setMessage(message)

    positiveButtonLabel?.let {
        dialogBuilder.setPositiveButton(positiveButtonLabel, android.content.DialogInterface.OnClickListener { dialog, whichButton ->
            positiveButtonAction()
        })
    }

    negativeButtonLabel?.let{
        dialogBuilder.setNegativeButton(negativeButtonLabel, android.content.DialogInterface.OnClickListener { dialog, whichButton ->
            negativeButtonAction()
        })
    }

    dialogBuilder.show()
}