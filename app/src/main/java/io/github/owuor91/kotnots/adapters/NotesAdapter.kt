package io.github.owuor91.kotnots.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import io.github.owuor91.kotnots.R
import io.github.owuor91.kotnots.activities.ViewNoteActivity
import io.github.owuor91.kotnots.sql.Note

class NotesAdapter(val noteList: List<Note>) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
  
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): NotesViewHolder {
    return NotesViewHolder(LayoutInflater.from(parent.context), parent)
  }
  
  override fun getItemCount(): Int {
    return noteList.size
  }
  
  override fun onBindViewHolder(
    holder: NotesViewHolder,
    position: Int
  ) {
    holder.bind(noteList[position])
  }
  
  class NotesViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup
  ) : RecyclerView.ViewHolder(
      inflater.inflate(
          R.layout.note_list_item, parent, false
      )
  ) {
    private var tvNoteItemTitle: TextView
    private var imgNoteItem: ImageView
    private var tvNoteItemText: TextView
    private var tvNoteItemEdit: TextView
    private var tvNoteItemDelete: TextView
    private var cvNoteRoot: CardView
    
    init {
      tvNoteItemTitle = itemView.findViewById(R.id.tvNoteItemTitle)
      imgNoteItem = itemView.findViewById(R.id.imgNoteItem)
      tvNoteItemText = itemView.findViewById(R.id.tvNoteItemText)
      tvNoteItemEdit = itemView.findViewById(R.id.tvNoteItemEdit)
      tvNoteItemDelete = itemView.findViewById(R.id.tvNoteItemDelete)
      cvNoteRoot = itemView.findViewById(R.id.cardViewRoot)
    }
    
    fun bind(note: Note) {
      tvNoteItemTitle.text = note.title
      tvNoteItemText.text = note.noteText
      
      cvNoteRoot.setOnClickListener {
          it.context.startActivity(Intent(it.context, ViewNoteActivity::class.java).putExtra("NOTE_ID", note.id))
      }
      
    }
  }
}