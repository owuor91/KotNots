package io.github.owuor91.kotnots.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.owuor91.kotnots.R
import io.github.owuor91.kotnots.sql.DatabaseHelper
import kotlinx.android.synthetic.main.activity_view_note.*

class ViewNoteActivity : AppCompatActivity() {
    private var noteId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_note)
        getNoteIdBundle()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        var databaseHelper = DatabaseHelper(baseContext, "Notes", null, 1)
        var note = databaseHelper.getNoteById(noteId)
        note?.let {
            tvNoteTitle.text = note.title
            tvNoteText.text = note.noteText
        }
    }

    fun getNoteIdBundle() {
        var bundle = intent.extras
        bundle?.let {
            noteId = bundle.getInt("NOTE_ID", 0)
        }
    }
}
