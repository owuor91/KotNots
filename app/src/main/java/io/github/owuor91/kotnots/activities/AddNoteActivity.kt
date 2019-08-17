package io.github.owuor91.kotnots.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
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
import org.json.JSONObject

class AddNoteActivity : BaseActivity() {
  val TAKE_PIC_INTENT: Int = 234
  val API_URL: String = "https://akirachixnotesapi.herokuapp.com/api/v1/notes"
  
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
      postNote(Note(title = title, noteText = noteText))
      //saveNoteToDatabase(Note(title=title, noteText = noteText))
    }
  }
  
  private fun postNote(note: Note) {
    val TAG = "POST_NOTE"
    
    val params = HashMap<String, String>()
    params.put("title", note.title)
    params.put("noteText", note.noteText)
    
    var jsonObjectRequest =
      JsonObjectRequest(Request.Method.POST, API_URL, JSONObject(params), Response.Listener<JSONObject> { response ->
        try {
          var id = response.get("id") as Int
          var title = response.get("title") as String
          var noteText = response.get("noteText") as String
          saveNoteToDatabase(Note(id = id, title = title, noteText = noteText))
          Log.d(TAG, response.toString())
        } catch (e: Exception) {
          Log.d(TAG, e.message)
        }
      }, Response.ErrorListener {
        Log.d(TAG, it.message)
      })
    
    var requestQueue = Volley.newRequestQueue(baseContext)
    requestQueue.add(jsonObjectRequest)
  }
  
  private fun saveNoteToDatabase(note: Note) {
    var databaseHelper = DatabaseHelper(baseContext, "Notes", null, 1);
    var note = Note(note.id, note.title, note.noteText)
    databaseHelper.insertNote(note)
    
    Toast.makeText(baseContext, "Note saved successfully", Toast.LENGTH_SHORT)
      .show()
    finish()
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
