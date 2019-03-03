package co.hillstech.digicollection.DataBases

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import co.hillstech.digicollection.Classes.Monster

class DBHandler(context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
        SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_PARTNER TEXT, $COLUMN_LVL TEXT, $COLUMN_SCAN INT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun add(line: Monster){
        val values = ContentValues()
        values.put(COLUMN_NAME, line.name)
        values.put(COLUMN_PARTNER, line.partner)
        values.put(COLUMN_LVL, line.lvl)
        values.put(COLUMN_SCAN, line.scan)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun find(term: String) : Monster? {
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_NAME = \"$term\""

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        var monster: Monster? = null

        if (cursor.moveToFirst()) {
            cursor.moveToFirst()

            val id = Integer.parseInt(cursor.getString(0))
            val name = cursor.getString(1)
            val partner = cursor.getString(2)
            monster = Monster(id, name, partner)
            cursor.close()
        }

        db.close()
        return monster
    }

    fun partner(term: String) : Monster? {
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_PARTNER = \"$term\""

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        var monster: Monster? = null

        if (cursor.moveToFirst()) {
            cursor.moveToFirst()

            val id = Integer.parseInt(cursor.getString(0))
            val name = cursor.getString(1)
            val partner = cursor.getString(2)
            val lvl = Integer.parseInt(cursor.getString(3))
            val scan = Integer.parseInt(cursor.getString(4))
            monster = Monster(id, name, partner, lvl)
            monster.scan = scan
            cursor.close()
        }

        db.close()
        return monster
    }


    fun getToScan(mon: String) : Monster? {
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_NAME = \"$mon\" AND $COLUMN_SCAN < 100"

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        var monster: Monster? = null

        if (cursor.moveToFirst()) {
            cursor.moveToFirst()

            val id = Integer.parseInt(cursor.getString(0))
            val name = cursor.getString(1)
            val partner = cursor.getString(2)
            val lvl = Integer.parseInt(cursor.getString(3))
            val scan = Integer.parseInt(cursor.getString(4))
            monster = Monster(id, name, partner, lvl)
            monster.scan = scan
            cursor.close()
        }

        db.close()
        return monster
    }

    fun getMonster(pid: Int) : Monster? {
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = \"$pid\""

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        var monster: Monster? = null

        if (cursor.moveToFirst()) {
            cursor.moveToFirst()

            val id = Integer.parseInt(cursor.getString(0))
            val name = cursor.getString(1)
            val partner = cursor.getString(2)
            val lvl = Integer.parseInt(cursor.getString(3))
            val scan = Integer.parseInt(cursor.getString(4))
            monster = Monster(id, name, partner, lvl)
            monster.scan = scan
            cursor.close()
        }

        db.close()
        return monster
    }

    fun select() : List<Monster> {
        val query = "SELECT * FROM $TABLE_NAME"

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        var monsters: MutableList<Monster?> = mutableListOf()

        var monster: Monster? = null

        if (cursor.moveToFirst()) {
            do {
                val id = Integer.parseInt(cursor.getString(0))
                val name = cursor.getString(1)
                val partner = cursor.getString(2)
                val lvl = Integer.parseInt(cursor.getString(3))
                val scan = Integer.parseInt(cursor.getString(4))
                monster = Monster(id, name, partner, lvl)
                monster.scan = scan
                monsters.add(monster)
            } while (cursor.moveToNext())
        }

        db.close()
        return monsters as List<Monster>
    }

    fun getScanning() : List<Monster> {
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_SCAN < 100 ORDER BY $COLUMN_SCAN DESC"

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        var monsters: MutableList<Monster?> = mutableListOf()

        var monster: Monster? = null

        if (cursor.moveToFirst()) {
            do {
                val id = Integer.parseInt(cursor.getString(0))
                val name = cursor.getString(1)
                val partner = cursor.getString(2)
                val lvl = Integer.parseInt(cursor.getString(3))
                val scan = Integer.parseInt(cursor.getString(4))
                monster = Monster(id, name, partner, lvl)
                monster.scan = scan
                monsters.add(monster)
            } while (cursor.moveToNext())
        }

        db.close()
        return monsters as List<Monster>
    }

    fun getMonsters() : List<Monster> {
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_SCAN >= 100 AND $COLUMN_PARTNER != \"true\" ORDER BY $COLUMN_NAME"

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        var monsters: MutableList<Monster?> = mutableListOf()

        var monster: Monster? = null

        if (cursor.moveToFirst()) {
            do {
                val id = Integer.parseInt(cursor.getString(0))
                val name = cursor.getString(1)
                val partner = cursor.getString(2)
                val lvl = Integer.parseInt(cursor.getString(3))
                val scan = Integer.parseInt(cursor.getString(4))
                monster = Monster(id, name, partner, lvl)
                monster.scan = scan
                monsters.add(monster)
            } while (cursor.moveToNext())
        }

        db.close()
        return monsters as List<Monster>
    }

    fun getMonstersToMyCodes() : List<Monster> {
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_SCAN >= 100 GROUP BY $COLUMN_NAME ORDER BY $COLUMN_NAME"

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        var monsters: MutableList<Monster?> = mutableListOf()

        var monster: Monster? = null

        if (cursor.moveToFirst()) {
            do {
                val id = Integer.parseInt(cursor.getString(0))
                val name = cursor.getString(1)
                val partner = cursor.getString(2)
                val lvl = Integer.parseInt(cursor.getString(3))
                val scan = Integer.parseInt(cursor.getString(4))
                monster = Monster(id, name, partner, lvl)
                monster.scan = scan
                monsters.add(monster)
            } while (cursor.moveToNext())
        }

        db.close()
        return monsters as List<Monster>
    }

    fun delete(name: String): Boolean {
        var result = false

        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_NAME = \"$name\""

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

    fun delete(id: Int): Boolean {
        var result = false

        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = \"$id\""

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

    fun updateScan(line: Monster): Boolean {

        val values = ContentValues()
        values.put(COLUMN_ID, line.id)
        values.put(COLUMN_NAME, line.name)
        values.put(COLUMN_PARTNER, line.partner)
        values.put(COLUMN_LVL, line.lvl)
        values.put(COLUMN_SCAN, line.scan)

        var selectionArs = arrayOf(line.id.toString())

        val db = this.writableDatabase

        db.update(TABLE_NAME,values,"$COLUMN_ID=?",selectionArs)
        db.close()

        return true
    }

    fun setPartner(id: Int): Boolean {

        var part = partner("true")

        val pvalues = ContentValues()
        pvalues.put(COLUMN_ID, part!!.id)
        pvalues.put(COLUMN_NAME, part.name)
        pvalues.put(COLUMN_PARTNER, "false")
        pvalues.put(COLUMN_LVL, part.lvl)
        pvalues.put(COLUMN_SCAN, part.scan)

        var selectionArs = arrayOf(part.id.toString())

        val db = this.writableDatabase

        db.update(TABLE_NAME,pvalues,"$COLUMN_ID=?",selectionArs)
        db.close()

        var line = getMonster(id)

        val values = ContentValues()
        values.put(COLUMN_ID, line!!.id)
        values.put(COLUMN_NAME, line.name)
        values.put(COLUMN_PARTNER, "true")
        values.put(COLUMN_LVL, line.lvl)
        values.put(COLUMN_SCAN, line.scan)

        var selectionArs2 = arrayOf(line.id.toString())

        val db2 = this.writableDatabase

        db2.update(TABLE_NAME,values,"$COLUMN_ID=?",selectionArs2)
        db2.close()

        return true
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "digicolledb_monsters.db"
        val TABLE_NAME = "digimons"

        val COLUMN_ID = "_id"
        val COLUMN_NAME = "name"
        val COLUMN_PARTNER = "partner"
        val COLUMN_LVL = "lvl"
        val COLUMN_SCAN = "scan"
    }
}