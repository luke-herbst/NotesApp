package iu.c323.fall2024.project7

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import iu.c323.fall2024.project7.databinding.ListItemNoteBinding
import iu.c323.fall2024.project7.model.Note
import java.util.UUID

class NoteListAdapter(
    private val notes: List<Note>,
    private val onNoteClicked: (noteId: String) -> Unit,  // noteId is now a String in Firebase
    private val onDeleteClick: (noteId: String) -> Unit
) : RecyclerView.Adapter<NoteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemNoteBinding.inflate(inflater, parent, false)
        return NoteHolder(binding)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val note = notes[position]
        holder.bind(note, onNoteClicked, onDeleteClick)
    }
}

class NoteHolder(private val binding: ListItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(note: Note, onNoteClicked: (noteId: String) -> Unit, onDeleteClick: (noteId: String) -> Unit) {
        binding.noteTitle.text = note.title
        binding.noteDescription.text = note.description

        binding.root.setOnClickListener {
            onNoteClicked(note.id)
        }
    }
}
