package co.hillstech.digicollection

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import co.hillstech.digicollection.Classes.Monster
import co.hillstech.digicollection.Classes.QRCodes
import co.hillstech.digicollection.DataBases.DBHandler
import co.hillstech.digicollection.RecyclerView.QRCodesAdapter
import kotlinx.android.synthetic.main.activity_qrcodes.*

class QRCodesActivity : AppCompatActivity() {

    var db: DBHandler = DBHandler(this, null, null, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcodes)

        var ac = supportActionBar
        ac?.title = getString(R.string.my_qrcodes)
        ac?.setDisplayHomeAsUpEnabled(true)

        try {
            val mons: List<Monster> = db.getMonstersToMyCodes()
            var codes: MutableList<QRCodes> = mutableListOf()
            for(m in mons){
                codes.add(QRCodes(m.id, m.name.toString(), m.name.toString()))
            }

            rcvCodes.adapter =  QRCodesAdapter(codes, this@QRCodesActivity)
            val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            rcvCodes.layoutManager = layoutManager
        }catch (ex: Exception){
            //to-do
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
