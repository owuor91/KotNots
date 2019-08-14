package io.github.owuor91.kotnots.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import io.github.owuor91.kotnots.R.layout
import io.github.owuor91.kotnots.R.string
import io.github.owuor91.kotnots.sql.DatabaseHelper
import io.github.owuor91.kotnots.sql.Note
import kotlinx.android.synthetic.main.activity_add_note.addNoteActivityToolbar
import kotlinx.android.synthetic.main.activity_add_note.btnSaveNote
import kotlinx.android.synthetic.main.activity_add_note.etNoteText
import kotlinx.android.synthetic.main.activity_add_note.etTitle
import kotlinx.android.synthetic.main.activity_add_note.imgBitmap
import kotlinx.android.synthetic.main.activity_add_note.tvAddPhoto

class AddNoteActivity : BaseActivity() {
  val TAKE_PIC_INTENT: Int = 234

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_add_note)

    btnSaveNote.setOnClickListener {
      clickSaveNote()
    }

    tvAddPhoto.setOnClickListener {
      startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), TAKE_PIC_INTENT)
    }
  }

  override fun onStart() {
    super.onStart()
    setupToolbar(addNoteActivityToolbar)
  }

  private fun clickSaveNote() {
    var title: String = etTitle.text.toString()
    var noteText: String = etNoteText.text.toString()
    var error: Boolean = false

    if (title.isEmpty()) {
      error = true
      etTitle.error = getString(string.please_enter_title)
    }

    if (noteText.isEmpty()) {
      error = true
      etNoteText.error = getString(string.please_enter_note_text)
    }

    if (!error) {
      var databaseHelper = DatabaseHelper(baseContext, "Notes", null, 1);
      var note = Note(title = title, noteText = noteText)
      databaseHelper.insertNote(note)
  
      Toast.makeText(baseContext, "Note saved successfully", Toast.LENGTH_SHORT)
          .show()
      finish()
    }
  }

  override fun onActivityResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
  ) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == TAKE_PIC_INTENT && resultCode == Activity.RESULT_OK) {
      var extras = data?.extras
      var bitmap = extras?.get("data") as? Bitmap
      imgBitmap.setImageBitmap(bitmap)
    }
  }
}