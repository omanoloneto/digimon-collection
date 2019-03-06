package co.hillstech.digicollection

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.hillstech.digicollection.Classes.Evolution
import co.hillstech.digicollection.Classes.Monster
import kotlinx.android.synthetic.main.activity_evolutions.*

class EvolutionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evolutions)

        txtSpecie.text = Session.specie
        txtScanPer.text = getString(R.string.det_percent)+" "+Monster.getSpecie(Session.specie!!).percent+"%"
        txtNivel.text = getString(R.string.det_evolve)+" "+Monster.getSpecie(Session.specie!!).evoLvl

        var perc = Monster.getSpecie(Session.specie!!).percent
        var div = perc/4

        txtXpLevel.text = getString(R.string.det_xpscan)+" "+((100 - (perc*2))/div).toString()+getString(R.string.det_pts)

        imgImagem.setImageResource(Monster.getSpecie(Session.specie!!).image)

        var monster = Monster.getSpecie(Session.specie.toString())
        var lvl = monster.evoLvl
        var pre = monster.name
        var evos: MutableList<Evolution> = mutableListOf()
        for(e in monster.evolutions){
            evos.add(Evolution(pre, e, lvl))
        }

        /*rcvEvolutions.adapter =  EvolutionsAdapter(evos, this)
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        rcvEvolutions.layoutManager = layoutManager
        rcvEvolutions.adapter.notifyDataSetChanged()*/

        var ac = supportActionBar
        ac?.title = getString(R.string.detalhes)
        ac?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
