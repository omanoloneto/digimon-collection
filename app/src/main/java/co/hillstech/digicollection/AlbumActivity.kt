package co.hillstech.digicollection

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import co.hillstech.digicollection.RecyclerView.AlbumAdapter
import kotlinx.android.synthetic.main.activity_album.*

class AlbumActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        rcvAlbum.adapter =  AlbumAdapter(Session.monsters, this)
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        rcvAlbum.layoutManager = layoutManager

        var ac = supportActionBar
        ac?.title = getString(R.string.title_album)
        ac?.setDisplayHomeAsUpEnabled(true)

        txtFaltam.text = getString(R.string.alb_faltam)+": "+(Session.monsters.size - Session.seens.size).toString()
        txtTotal.text = getString(R.string.alb_total)+": "+(Session.monsters.size).toString()
        txtVistos.text = getString(R.string.alb_vistos)+": "+(Session.seens.size).toString()

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

}
