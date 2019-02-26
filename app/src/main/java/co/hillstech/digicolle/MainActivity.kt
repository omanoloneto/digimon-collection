package co.hillstech.digicolle

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.telephony.TelephonyManager
import android.text.format.Time
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import co.hillstech.digicolle.Classes.Monster
import co.hillstech.digicolle.DataBases.DBAlbum
import co.hillstech.digicolle.DataBases.DBCodes
import co.hillstech.digicolle.DataBases.DBHandler
import co.hillstech.digicolle.DataBases.DBOthers
import co.hillstech.digicolle.RecyclerView.MonstersAdapter
import co.hillstech.digicolle.RecyclerView.ScanningAdapter
import kotlinx.android.synthetic.main.activity_main.*
import net.glxn.qrgen.android.QRCode
import java.util.*




class MainActivity : AppCompatActivity() {

    var db: DBHandler = DBHandler(this, null, null, 1)
    var dbc: DBCodes = DBCodes(this, null, null, 1)
    var dbo: DBOthers = DBOthers(this, null, null, 1)
    var dba: DBAlbum = DBAlbum(this, null, null, 1)

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navi_partner -> {
                showHome()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navi_digimons -> {
                showMonsters()
                return@OnNavigationItemSelectedListener true
            }
            R.id.layoutMenu -> {
                showScans()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        /*if(item?.itemId == R.id.btnWallet){

            var num = 0
            try{
                var coins = dbo.getOther("coins")
                if(coins != null){
                    num = Integer.parseInt(coins.value)
                }
            }catch (ex: Exception){
                Log.e("",ex.message)
            }

            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle(getString(R.string.title_wallet))
            dialogBuilder.setMessage(getString(R.string.text_wallet_1)+" $num "+getString(R.string.text_wallet_2))
            dialogBuilder.setPositiveButton("OK", null)
            dialogBuilder.show()

        }else if (item?.itemId == R.id.btnAlbum){

            /*var banco = dba.select()
            var mons = Monster.species()

            var resto = mons.size - banco.size

            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle(getString(R.string.title_album))
            dialogBuilder.setMessage(getString(R.string.text_seen_1)+" ${banco.size} "+getString(R.string.text_seen_2)+"\n\n" +
                    getString(R.string.text_total_1)+" ${mons.size} "+getString(R.string.text_total_2)+"\n\n" +
                    getString(R.string.text_left_1)+" $resto "+getString(R.string.text_left_2))
            dialogBuilder.setPositiveButton("OK", null)
            dialogBuilder.show()*/

            startActivity(Intent(applicationContext, AlbumActivity::class.java))

        }else if (item?.itemId == R.id.btnScan){
            startActivity(Intent(applicationContext, BarcodeScanningActivity::class.java))
        }else if(item?.itemId == R.id.btnQRCodes){
            startActivity(Intent(applicationContext, QRCodesActivity::class.java))
        }else if(item?.itemId == R.id.btnAbout){

            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle(getString(R.string.about))

            dialogBuilder.setMessage(getString(R.string.about_version)+" "+
                                     getString(R.string.about_date)+"\n\n"+
                                     getString(R.string.about_text)+"\n\n"+
                                     getString(R.string.about_creator))

            dialogBuilder.setPositiveButton("OK", null)
            dialogBuilder.show()

        }*/
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        /*if(Session.code != null){
            val code = Session.code.toString().split(",")
            Session.code = null
            var mon = db.getToScan(code[0])

            var co = Codes(code[0], code[1])
            var dbco = dbc.find(code[0], code[1])

            var date = Integer.parseInt(code[2])

            var save: Boolean = false

            if(dbco != null){
                if(date == dbco?.date){
                    Toast.makeText(this,getString(R.string.qr_today),Toast.LENGTH_LONG).show()
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
                        dbc.add(co)
                    }
                }else{
                    mon = Monster.getSpecie(code[0])
                    mon.scan = mon.percent
                    if(mon.name != null){
                        db.add(mon)
                        dbc.add(co)
                        dba.add(mon!!.name.toString())
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

            if(mon!!.scan >= 100 && mon?.name != null){
                var partner = db.partner("true")
                partner?.lvl = partner?.lvl?.plus(1)
                partner?.scan = 100

                txtLvl.text = partner?.lvl.toString()

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

                Toast.makeText(this,"${mon.name} scan is complete!",Toast.LENGTH_SHORT).show()
                Toast.makeText(this,getString(R.string.uploadLvl),Toast.LENGTH_SHORT).show()
            }else{
                if(mon?.name != null && save == true) Toast.makeText(this,"The scan of ${mon.name} now is ${mon.scan}%",Toast.LENGTH_LONG).show()
                else Toast.makeText(this,"Sorry, an error occurred while trying to read the code.",Toast.LENGTH_LONG).show()
            }

            inflateScanning()
            inflateMonsters()
            inflatePartner()

        }*/

        inflateScanning()
        inflateMonsters()
        inflatePartner()

        super.onResume()
    }

    fun showHome(){
        hideAll()
        ctlHome.animate().alpha(1.0f)
        ctlHome.visibility = View.VISIBLE
    }

    fun showMonsters(){
        hideAll()
        ctlMons.animate().alpha(1.0f)
        ctlMons.visibility = View.VISIBLE
    }

    fun showScans(){
        hideAll()
        ctlScans.animate().alpha(1.0f)
        ctlScans.visibility = View.VISIBLE
    }

    fun hideAll(){
        ctlHome.animate().alpha(0.0f)
        ctlHome.visibility = View.GONE
        ctlScans.animate().alpha(0.0f)
        ctlScans.visibility = View.GONE
        ctlMons.animate().alpha(0.0f)
        ctlMons.visibility = View.GONE
    }

    private fun inflatePartner(): Monster?{
        var mon: Monster? = db.partner("true")

        mon?.let {

            txtName.text = it.name

            imgDigimon.setImageResource(Monster.getSpecie(it.name.toString()).image)

            txtLvl.text = getString(R.string.level)+": ${it.lvl}"

            if(it.scan < 100){
                it.scan = 100
                db.updateScan(it)
            }

            var partner = db.partner("true")
            var xp = dbo.getOther("digi_i"+partner?.id)

            var taxa = ((Integer.parseInt(partner!!.lvl.toString())*100)/2)

            if(xp?.value != null){
                txtXperience.text = xp?.value+"/"+taxa
            }else{
                dbo.add("digi_i"+partner?.id,"0")
                txtXperience.text = "0/"+taxa
            }

            //Toast.makeText(this, "${Monster.evolution(it?.name.toString())}",Toast.LENGTH_SHORT).show()

        }?: run{

            var myDmon: Int = (Random().nextInt(Monster.size()))

            var myDmonName: String? = Session.monsters[myDmon].name

            var mymon = Monster(1, myDmonName.toString(), "true", 0)
            mymon.scan = 100

            db.add(mymon)

            txtName.text = mymon.name

            imgDigimon.setImageResource(Monster.getSpecie(mymon.name.toString()).image)

            txtLvl.text = getString(R.string.level)+": 0"
            txtXperience.text = "0/0"

            Toast.makeText(this, getString(R.string.first_buddy),Toast.LENGTH_LONG).show()

            mon = mymon

            dba.add(mon!!.name.toString())

        }

        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        var SIM = ""

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_PHONE_STATE)) {} else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_PHONE_STATE), 2)
            }
        }else{
            btnPermissions.visibility = View.GONE
            imgQR.visibility = View.VISIBLE
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

            val code = QRCode.from("${mon?.name.toString()},$SIM,$date").withSize(450, 450).bitmap()
            imgQR.setImageBitmap(code)
        }catch (ex: Exception){
            Log.e("",ex.message)
        }

        return mon
    }

    private fun inflateScanning(){
        val mons: List<Monster> = db.getScanning()

        rcvScanning.adapter =  ScanningAdapter(mons, this@MainActivity)
        val layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        rcvScanning.layoutManager = layoutManager
    }

    private fun inflateMonsters(){
        val mons: List<Monster> = db.getMonsters()

        rcvMons.adapter =  MonstersAdapter(mons, this@MainActivity)
        val layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        rcvMons.layoutManager = layoutManager
    }

    fun ajustaDokunemon(){
        var mons = db.getMonsters()
        for (m in mons){
            if(m.name == "Dokunemon"){
                m.name = "DoKunemon"
                db.updateScan(m)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Session.monsters = Monster.inflateMonsters()
        Session.seens = dba.select()

        //remove on next version (RONVe)
        ajustaDokunemon()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        var mon = inflatePartner()

        btnPermissions.setOnClickListener {

            val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

            var SIM = ""

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_PHONE_STATE), 2)
            }else{
                btnPermissions.visibility = View.GONE
                imgQR.visibility = View.VISIBLE
            }

            val td = Time(Time.getCurrentTimezone())
            td.setToNow()

            var today = td.year.toString()+
                    td.month.toString()+
                    td.monthDay.toString()

            var date = Integer.parseInt(today)

            try {
                SIM = tm.simSerialNumber
                Session.mysim = SIM
            }catch (ex: Exception){
                Log.e("",ex.message)
            }

            //Toast.makeText(this, "$SIM",Toast.LENGTH_SHORT).show()

            val code = QRCode.from("${mon?.name.toString()},$SIM,$date").withSize(450, 450).bitmap()
            imgQR.setImageBitmap(code)
        }

        imgDigimon.setOnClickListener {
            Session.specie = mon!!.name
            val intent = Intent(this, EvolutionsActivity::class.java)
            startActivity(intent)
        }

        inflateScanning()
        inflateMonsters()

    }
}
