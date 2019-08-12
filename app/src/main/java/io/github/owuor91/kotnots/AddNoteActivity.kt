package io.github.owuor91.kotnots

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import io.github.owuor91.kotnots.R.string
import kotlinx.android.synthetic.main.activity_add_note.addNoteActivityToolbar
import kotlinx.android.synthetic.main.activity_add_note.btnSaveNote
import kotlinx.android.synthetic.main.activity_add_note.etNoteText
import kotlinx.android.synthetic.main.activity_add_note.etTitle
import kotlinx.android.synthetic.main.activity_add_note.tvAddPhoto

class AddNoteActivity : BaseActivity() {
  val TAKE_PIC_INTENT: Int = 234

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_note)

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

    }
  }

  override fun onActivityResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
  ) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == TAKE_PIC_INTENT && resultCode == Activity.RESULT_OK) {
      var extras = data!!.extras!!
      //var bitmap:Bitmap = extras!!.get("data")!!

      //imgBitmap.setImageBitmap(bitmap)

    }
  }
}
