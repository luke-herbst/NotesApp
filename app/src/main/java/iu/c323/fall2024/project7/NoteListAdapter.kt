package iu.c323.fall2024.project7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import iu.c323.fall2024.project7.databinding.ListItemNoteBinding
import java.util.UUID

class NoteListAdapter(
    private val notes: List<Note>,
    private val onNoteClicked: (noteId: UUID) -> Unit,
    private val onDeleteClick: (noteId: UUID) -> Unit
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

class NoteHolder(
    private val binding: ListItemNoteBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(note: Note, onNoteClicked: (noteId: UUID) -> Unit, onDeleteClick: (noteId: UUID) -> Unit) {
        binding.noteTitle.text = note.title

        binding.root.setOnClickListener {
            onNoteClicked(note.id)
        }

        binding.deleteButton.setOnClickListener {
            onDeleteClick(note.id)
        }
    }
}

