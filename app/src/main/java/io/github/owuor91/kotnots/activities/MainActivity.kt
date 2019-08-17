package io.github.owuor91.kotnots.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import io.github.owuor91.kotnots.R.layout
import io.github.owuor91.kotnots.adapters.NotesAdapter
import io.github.owuor91.kotnots.sql.DatabaseHelper
import io.github.owuor91.kotnots.sql.Note
import kotlinx.android.synthetic.main.activity_main.mainActivityFab
import kotlinx.android.synthetic.main.activity_main.rvNotes
import org.json.JSONArray

class MainActivity : BaseActivity() {
  private lateinit var notesAdapter: NotesAdapter
  private val API_URL: String = "https://akirachixnotesapi.herokuapp.com/api/v1/notes"
  private val TAG = "NOTES_API"
  
  
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
    //setupRecyclerView(notesList)
    getApiNotes()
  }
  
  fun setupRecyclerView(noteList: List<Note>) {
    notesAdapter = NotesAdapter(noteList)
    rvNotes.adapter = notesAdapter
  }
  
  fun getApiNotes() {
    var notesRequest = StringRequest(Request.Method.GET, API_URL, Response.Listener { response ->
      var noteList = ArrayList<Note>()
      try {
        var jsonArray = JSONArray(response)
        for (i in 0 until jsonArray.length()) {
          var note = Note()
          var jsonObject = jsonArray.getJSONObject(i)
          note.id = jsonObject.getInt("id")
          note.title = jsonObject.getString("title")
          note.noteText = jsonObject.getString("noteText")
          noteList.add(note)
        }
        
        setupRecyclerView(noteList)
      } catch (e: Exception) {
        Log.d(TAG, e.message)
      }
    },
      Response.ErrorListener {
        Log.d(TAG, it.message)
      })
    
    var requestQueue = Volley.newRequestQueue(baseContext)
    requestQueue.add(notesRequest)
  }
}
