package com.joker.keppoya.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.joker.keppoya.KeppoyaModel
import java.lang.Exception

class SQLiteHelper(context:Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "keppoya_db"
        private const val DATABASE_VERSION = 1
        private const val TABEL_KEPPOYA = "tbl_keppoya"
        private const val ID = "id"
        private const val TITLE = "title"
        private const val TEXT = "text"
        private const val STAR = "star"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableKeppoya = ("CREATE TABLE $TABEL_KEPPOYA ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $TITLE TEXT," +
                "$TEXT TEXT, $STAR TEXT)" )
        db?.execSQL(createTableKeppoya)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABEL_KEPPOYA")
        onCreate(db)
    }

    fun insertKeppoya(keppoya : KeppoyaModel):Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
//        contentValues.put(ID,keppoya.id)
        contentValues.put(TITLE,keppoya.title)
        contentValues.put(TEXT,keppoya.text)
        contentValues.put(STAR,keppoya.star)

        val success = db.insert(TABEL_KEPPOYA, null, contentValues)
        db.close()
        return success

    }

    fun getAllKeppoya() : ArrayList<KeppoyaModel> {
        val keppoyaList : ArrayList<KeppoyaModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABEL_KEPPOYA ORDER BY STAR DESC"
        val db = this.readableDatabase

        val cursor : Cursor?

        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e : Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id : Int
        var title : String
        var text : String
        var star : String

        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                text = cursor.getString(cursor.getColumnIndexOrThrow("text"))
                star = cursor.getString(cursor.getColumnIndexOrThrow("star"))

                val keppoya = KeppoyaModel(id = id, title = title, text = text, star = star)
                keppoyaList.add(keppoya)

            }while (cursor.moveToNext())
        }
        return  keppoyaList
    }
    fun updateKeppoya(keppoya : KeppoyaModel) : Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ID,keppoya.id)
        contentValues.put(TITLE,keppoya.title)
        contentValues.put(TEXT,keppoya.text)
        contentValues.put(STAR,keppoya.star)

        val success = db.update(TABEL_KEPPOYA, contentValues, "id=${keppoya.id}", null)
        db.close()
        return success
    }

    fun deleteKeppoyaById(id : Int):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(ID, id)

        val success = db.delete(TABEL_KEPPOYA, "id=$id", null)
        db.close()
        return success
    }
}