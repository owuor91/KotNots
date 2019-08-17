package io.github.owuor91.kotnots.sql

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper : SQLiteOpenHelper {
  
  constructor(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
  ) : super(context, name, factory, version)
  
  override fun onCreate(db: SQLiteDatabase?) {
    db?.execSQL("CREATE TABLE `Notes`(`id` INTEGER PRIMARY KEY, `title` TEXT, `noteText` TEXT)")
  }
  
  override fun onUpgrade(
    db: SQLiteDatabase?,
    oldVersion: Int,
    newVersion: Int
  ) {
    db?.execSQL("DROP TABLE IF EXISTS `Notes`")
    onCreate(db)
  }
  
  fun insertNote(note: Note): Long {
    var sqLiteDatabase = this.writableDatabase
    
    var contentValues = ContentValues()
    contentValues.put("id", note.id)
    contentValues.put("title", note.title)
    contentValues.put("noteText", note.noteText)
    
    var insert = sqLiteDatabase.insert("Notes", null, contentValues)
    sqLiteDatabase.close()
    return insert
  }
  
  fun getNotes(): MutableList<Note> {
    var noteList = ArrayList<Note>()
    var query = "SELECT * FROM Notes"
    
    var sqLiteDatabase = this.readableDatabase
    var cursor = sqLiteDatabase.rawQuery(query, null)
    
    if (cursor.moveToFirst()) {
      do {
        var note = Note()
        note.id = cursor.getInt(cursor.getColumnIndex("id"))
        note.title = cursor.getString(cursor.getColumnIndex("title"))
        note.noteText = cursor.getString(cursor.getColumnIndex("noteText"))
  
        noteList.add(note)
      } while (cursor.moveToNext())
    }
    sqLiteDatabase.close()
    return noteList
  }
  
  fun updateNote(note: Note): Int {
    var sqLiteDatabase = this.writableDatabase
    
    var contentValues = ContentValues()
    contentValues.put("title", note.title)
    contentValues.put("noteText", note.noteText)
    
    var tableName = "Notes"
    var whereClause = "id = ?"
    
    var update = sqLiteDatabase.update(tableName, contentValues, whereClause, arrayOf<String>(note.id.toString()))
    sqLiteDatabase.close()
    return update
  }
  
  fun deleteNote(noteId: Int) {
    var sqLiteDatabase = this.readableDatabase
    var tableName = "Notes"
    var whereClause = "id=?"
    var whereArgs = arrayOf(noteId.toString())
    sqLiteDatabase.delete(tableName, whereClause, whereArgs)
    sqLiteDatabase.close()
  }
  
  fun getNoteById(id: Int): Note {
    var note = Note()
    var query = "SELECT * FROM Notes WHERE id = ?"
    var whereArgs = arrayOf(id.toString())
    var sqLiteDatabase = this.readableDatabase
    var cursor = sqLiteDatabase.rawQuery(query, whereArgs)
    
    if (cursor.moveToFirst()) {
      note.id = cursor.getInt(cursor.getColumnIndex("id"))
      note.title = cursor.getString(cursor.getColumnIndex("title"))
      note.noteText = cursor.getString(cursor.getColumnIndex("noteText"))
    }
    sqLiteDatabase.close()
    return note
  }
}