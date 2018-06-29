package co.hillstech.digicolle.DataBases

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import co.hillstech.digicolle.Classes.Other

class DBOthers(context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
        SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TERM TEXT, $COLUMN_VAL TEXT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun add(term: String, value: String){
        val values = ContentValues()
        values.put(COLUMN_TERM, term)
        values.put(COLUMN_VAL, value)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getOther(term: String) : Other? {
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_TERM = \"$term\""

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        var value = Other()

        if (cursor.moveToFirst()) {
            cursor.moveToFirst()

            value.id = Integer.parseInt(cursor.getString(0))
            value.term = cursor.getString(1)
            value.value = cursor.getString(2)
            cursor.close()
        }

        db.close()
        return value
    }

    fun setOther(other: Other): Boolean {

        val values = ContentValues()
        values.put(COLUMN_ID, other!!.id)
        values.put(COLUMN_TERM, other.term)
        values.put(COLUMN_VAL, other.value)

        var selectionArs = arrayOf(other.id.toString())

        val db = this.writableDatabase

        db.update(TABLE_NAME,values,"$COLUMN_ID=?",selectionArs)
        db.close()

        return true
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "digicolledb_others.db"
        val TABLE_NAME = "others"

        val COLUMN_ID = "_id"
        val COLUMN_TERM = "term"
        val COLUMN_VAL = "val"
    }
}