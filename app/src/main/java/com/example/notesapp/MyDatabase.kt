package com.example.notesapp

import android.annotation.SuppressLint
import android.content.ClipDescription
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import androidx.core.content.contentValuesOf
import kotlin.contracts.contract

class MyDatabase( context: Context)
    : SQLiteOpenHelper(context, name, null,version) {


    override fun onCreate(dp: SQLiteDatabase?) {
        dp?.execSQL("CREATE TABLE ${DB.tab_name} ( ${DB.id} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${DB.title} TEXT NOT NULL, ${DB.description} TEXT)")
    }

    override fun onUpgrade(dp: SQLiteDatabase?, p1: Int, p2: Int) {
        dp?.execSQL("DROP TABLE ${DB.tab_name}")
        onCreate(dp)

    }
    fun insertData(context:Context,note: Note){
        val dp= writableDatabase
        val newInput=ContentValues().apply {
            put(DB.title,note.title)
            put(DB.description,note.description)
        }
        try {
            dp.insert(DB.tab_name, null, newInput)
        }catch (e:Exception){
            Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
        }
        dp.close()
    }
    @SuppressLint("Range")
    fun readAllData(context:Context):ArrayList<Note>{
        val notes=ArrayList<Note>()
        val cursor= readableDatabase.rawQuery("SELECT * FROM ${DB.tab_name}",null)
       // if(cursor.count==0) Toast.makeText(context,"Not Found Records!", Toast.LENGTH_SHORT).show()
      //  else{
            while (cursor.moveToNext()){
                val id=cursor.getInt(cursor.getColumnIndex(DB.id))
                val title=cursor.getString(cursor.getColumnIndex(DB.title))
                val desc=cursor.getString(cursor.getColumnIndex(DB.description))
                val note=Note(id,title,desc)
                notes.add(note)

            }
       // }
        cursor.close()
        return notes
    }
    @SuppressLint("SuspiciousIndentation")
    fun updateData(note:Note){
        val dp= writableDatabase
        val updatedData=ContentValues().apply {
            put(DB.title,note.title)
            put(DB.description,note.description)
        }
          dp.update(DB.tab_name,updatedData,"${DB.id}=?", arrayOf(note.id.toString()))
         dp.close()
    }
    @SuppressLint("Range")
    fun getNoteById(noteId:Int):Note{
        val dp=readableDatabase
        val cursor=dp.rawQuery("SELECT * FROM ${DB.tab_name} WHERE ${DB.id}=${noteId} ",null)
        cursor.moveToFirst()
        val id=cursor.getInt(cursor.getColumnIndexOrThrow(DB.id))
        val title=cursor.getString(cursor.getColumnIndexOrThrow(DB.title))
        val desc=cursor.getString(cursor.getColumnIndexOrThrow(DB.description))
        dp.close()
        cursor.close()
        return Note(id,title,desc)
    }
    fun deleteData(id:Int){
        writableDatabase.delete(DB.tab_name,"${DB.id}=?", arrayOf(id.toString()))

    }
    fun searchData(title: String):Cursor{
        return  readableDatabase.rawQuery("SELECT * FROM ${DB.tab_name} WHERE ${DB.title} LIKE '%$title%'",null)
    }
    companion object{
        private const val name="MyBooksDB"
        private const val version=1

    }
}