package co.hillstech.digicollection.RecyclerView

import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.telephony.TelephonyManager
import android.text.format.Time
import android.util.Log
import android.view.View
import co.hillstech.digicollection.Classes.QRCodes
import kotlinx.android.synthetic.main.view_qrcodes.view.*
import net.glxn.qrgen.android.QRCode


class QRCodesHolder : RecyclerView.ViewHolder {
    fun bindView(code: QRCodes) {

        itemView.txtName.text = "${code.name}"

        val tm = itemView.context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        var SIM = ""

        if (ContextCompat.checkSelfPermission(itemView.context, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

        }

        try {
            SIM = tm.simSerialNumber
            val td = Time(Time.getCurrentTimezone())
            td.setToNow()
            var today = td.year.toString()+
                    td.month.toString()+
                    td.monthDay.toString()
            var date = Integer.parseInt(today)
            //Toast.makeText(this, "$SIM",Toast.LENGTH_SHORT).show()

            val code = QRCode.from("${code.code},$SIM,$date").withSize(450, 450).bitmap()
            itemView.imgMonster.setImageBitmap(code)
        }catch (ex: Exception){
            Log.e("",ex.message)
        }

    }

    constructor(itemView: View):super(itemView){

    }

}