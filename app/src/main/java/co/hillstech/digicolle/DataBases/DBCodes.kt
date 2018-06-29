package co.hillstech.digicolle.DataBases

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import co.hillstech.digicolle.Classes.Codes

class DBCodes(context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
        SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_MONSTER TEXT, $COLUMN_FRIEND TEXT, $COLUMN_DATE INT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun add(line: Codes){
        val values = ContentValues()
        values.put(COLUMN_MONSTER, line.monster)
        values.put(COLUMN_FRIEND, line.friend)
        values.put(COLUMN_DATE, line.date)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun find(mon: String, fri: String) : Codes? {
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_MONSTER = \"$mon\" AND $COLUMN_FRIEND = \"$fri\" "

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        var code: Codes? = null

        if (cursor.moveToFirst()) {
            cursor.moveToFirst()

            val date = Integer.parseInt(cursor.getString(3))
            val monster = cursor.getString(1)
            val friend = cursor.getString(2)
            code = Codes(monster, friend, date)
            cursor.close()
        }

        db.close()
        return code
    }

    fun delete(friend: String, mon: String): Boolean {
        var result = false

        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_FRIEND = \"$friend\" AND $COLUMN_MONSTER = \"$mon\""

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            val id = Integer.parseInt(cursor.getString(0))
            db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                    arrayOf(id.toString()))
            cursor.close()
            result = true
        }
        db.close()
        return result
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "digicolledb_codes.db"
        val TABLE_NAME = "codes"

        val COLUMN_ID = "_id"
        val COLUMN_MONSTER = "monster"
        val COLUMN_FRIEND = "friend"
        val COLUMN_DATE = "date"
    }
}