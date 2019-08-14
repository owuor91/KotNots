package io.github.owuor91.kotnots.activities

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.owuor91.kotnots.R.layout
import io.github.owuor91.kotnots.adapters.NotesAdapter
import io.github.owuor91.kotnots.sql.DatabaseHelper
import kotlinx.android.synthetic.main.activity_main.mainActivityFab
import kotlinx.android.synthetic.main.activity_main.rvNotes

class MainActivity : BaseActivity() {
  private lateinit var notesAdapter: NotesAdapter
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)
  }
  
  override fun onStart() {
    super.onStart()
    mainActivityFab.setOnClickListener {
      startActivity(Intent(this, AddNoteActivity::class.java))
    }
    rvNotes.layoutManager = LinearLayoutManager(this)
  }
  
  override fun onResume() {
    super.onResume()
    var databaseHelper = DatabaseHelper(baseContext, "Notes", null, 1)
    var notesList = databaseHelper.getNotes()
    notesAdapter = NotesAdapter(notesList)
    rvNotes.adapter = notesAdapter
  }
}
