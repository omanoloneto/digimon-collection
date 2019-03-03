package co.hillstech.digicollection

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import co.hillstech.digicollection.Classes.Codes
import co.hillstech.digicollection.Classes.Monster
import co.hillstech.digicollection.DataBases.DBAlbum
import co.hillstech.digicollection.DataBases.DBCodes
import co.hillstech.digicollection.DataBases.DBHandler
import co.hillstech.digicollection.DataBases.DBOthers
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView




class BarcodeScanningActivity : Activity(), ZXingScannerView.ResultHandler {
    private var mScannerView: ZXingScannerView? = null

    var db: DBHandler = DBHandler(this, null, null, 1)
    var dbc: DBCodes = DBCodes(this, null, null, 1)
    var dbo: DBOthers = DBOthers(this, null, null, 1)
    var dba: DBAlbum = DBAlbum(this, null, null, 1)

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        mScannerView = ZXingScannerView(this)   // Programmatically initialize the scanner view
        setContentView(mScannerView)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),2)
        }else{

        }// Set the scanner view as the content view
    }

    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera()          // Start camera on resume
    }

    public override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()           // Stop camera on pause
    }

    override fun handleResult(rawResult: Result) {
        // Do something with the result here
        //Log.v(TAG, rawResult.getText())
        //Log.v(TAG, rawResult.getBarcodeFormat().toString())

        Session.code = rawResult.text

        val dialog = ProgressDialog.show(this, "",
                getString(R.string.loading), true)

        dialog.show()

        // If you would like to resume scanning, call this method below:
        Handler().postDelayed({
            dialog.cancel()
            // This method will be executed once the timer is over
            mScannerView!!.resumeCameraPreview(this)
            getCode()
        }, 1000)
    }

    fun getCode(){
        if(Session.code != null){
            val code = Session.code.toString().split(",")

            if(code[1] == Session.mysim){
                Toast.makeText(this,getString(R.string.mysim),Toast.LENGTH_SHORT).show()
            }else{
                try{
                    Session.code = null
                    var mon = db.getToScan(code[0])

                    var co = Codes(code[0],code[1])
                    var dbco = dbc.find(code[0], code[1])

                    var date = Integer.parseInt(code[2])

                    var save: Boolean = false

                    if(dbco != null){
                        if(date <= dbco?.date){
                            Toast.makeText(this,getString(R.string.qr_today),Toast.LENGTH_SHORT).show()
                        }else{
                            save = true
                        }
                    }else{
                        save = true
                    }

                    if(save == true){
                        if(mon != null){
                            mon.percent = Monster.getSpecie(code[0]).percent
                            mon.scan = mon.scan?.plus(mon.percent)
                            if(mon.name != null){
                                db.updateScan(mon)
                                dbc.delete(co.friend.toString(), co.monster.toString())
                                co.date = date
                                dbc.add(co)
                            }
                        }else{
                            mon = Monster.getSpecie(code[0])
                            mon.scan = mon.percent
                            if(mon.name != null){
                                db.add(mon)
                                dbc.add(co)
                                //dba.add(mon!!.name.toString())
                            }
                        }
                        try {
                            var coins = dbo.getOther("coins")
                            if(coins != null){
                                coins.value = (Integer.parseInt(coins.value) + 100).toString()
                                dbo.setOther(coins)
                            }else{
                                dbo.add("coins","100")
                            }
                        }catch (ex: Exception){
                            dbo.add("coins","100")
                        }


                    }

                    var partner = db.partner("true")

                    try {

                        var xp = dbo.getOther("digi_i"+partner?.id)

                        if(xp!!.value != null && save == true){
                            var perc = mon!!.percent
                            var div = perc/4
                            var ganho = Integer.parseInt((Integer.parseInt(xp.value) + (100 - (perc*2))/div).toString())
                            var taxa = ((Integer.parseInt(partner!!.lvl.toString())*100)/2)
                            if(ganho >= taxa){
                                xp.value = (ganho - taxa).toString()
                                partner?.lvl = partner?.lvl?.plus(1)

                                Toast.makeText(this,getString(R.string.uploadLvl),Toast.LENGTH_SHORT).show()

                                partner?.scan = 100

                                if(Monster.getSpecie(partner?.name.toString()).evoLvl <= Integer.parseInt(partner?.lvl.toString())){
                                    var oldName = partner?.name.toString()
                                    var evoName = Monster.evolution(partner?.name.toString())
                                    if(evoName != null){
                                        partner?.name = evoName

                                        var x = db.getToScan(evoName)

                                        if(x == null){
                                            dba.add(evoName)
                                        }

                                        val dialogBuilder = AlertDialog.Builder(this)
                                        dialogBuilder.setTitle(getString(R.string.title_evolution))
                                        dialogBuilder.setMessage(getString(R.string.text_evolution_1)+" $oldName "+getString(R.string.text_evolution_2)+" $evoName "+getString(R.string.text_evolution_3))
                                        dialogBuilder.setPositiveButton("OK", null)
                                        dialogBuilder.show()
                                    }
                                }

                                partner?.let {
                                    db.updateScan(partner)
                                }

                            }else{
                                xp.value = ganho.toString()
                            }
                            dbo.setOther(xp)
                        }else{
                            dbo.add("digi_i"+partner?.id,"0")
                        }
                    }catch (ex: Exception){
                        dbo.add("digi_i"+partner?.id,"0")
                    }

                    if(mon!!.scan >= 100 && mon?.name != null){
                        Toast.makeText(this,"${mon.name} scan is complete!",Toast.LENGTH_SHORT).show()
                    }else{
                        if(mon?.name != null && save == true) Toast.makeText(this,getString(R.string.text_scan_1)+" ${mon.name} "+getString(R.string.text_scan_2)+" ${mon.scan}%",Toast.LENGTH_SHORT).show()
                        else if(date > dbco!!.date) Toast.makeText(this,getString(R.string.error_code),Toast.LENGTH_SHORT).show()
                    }
                }catch (e: Exception){
                    Toast.makeText(this,getString(R.string.error_inesperado),Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}
