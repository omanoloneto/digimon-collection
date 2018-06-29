package co.hillstech.digicolle.DataBases

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBAlbum(context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
        SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_MONSTER TEXT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun add(mon: String){
        val values = ContentValues()
        values.put(COLUMN_MONSTER, mon)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun select() : List<String> {
        val query = "SELECT * FROM $TABLE_NAME GROUP BY $COLUMN_MONSTER"

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        var monsters: MutableList<String?> = mutableListOf()

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(1)
                monsters.add(name)
            } while (cursor.moveToNext())
        }

        db.close()
        return monsters as List<String>
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "digicolledb_albuns.db"
        val TABLE_NAME = "albuns"

        val COLUMN_ID = "_id"
        val COLUMN_MONSTER = "monster"
    }
}