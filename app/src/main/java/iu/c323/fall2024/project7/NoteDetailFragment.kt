package iu.c323.fall2024.project7

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import iu.c323.fall2024.project7.databinding.FragmentNoteDetailBinding
import iu.c323.fall2024.project7.model.Note
import iu.c323.fall2024.project7.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

private const val TAG ="NoteDetailFragment"
class NoteDetailFragment: Fragment() {
    private val args: NoteDetailFragmentArgs by navArgs()
    private val noteDetailViewModel: NoteDetailViewModel by viewModels{
        NoteDetailViewModelFactory(args.noteId)
    }
    private var _binding: FragmentNoteDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding){
            "Cannot access the view because it is null"
        }
    private var signedInUser: User? = null
    private lateinit var firestoreDb: FirebaseFirestore

    private val _notes: MutableStateFlow<List<Note>> = MutableStateFlow(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            saveButton.setOnClickListener {
                noteDetailViewModel.updateNote { oldNote ->
                    oldNote.copy(
                        title = noteTitle.text.toString(),
                        description = noteContentEt.text.toString()
                    )
                }
                findNavController().navigateUp()
            }
            deleteButton.setOnClickListener {
                val noteId = UUID.fromString(args.noteId)
                noteDetailViewModel.deleteNote(noteId)
                findNavController().navigateUp()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteDetailViewModel.note.collect { note ->
                    note?.let { updateUi(it) }
                }
            }
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun updateUi(note: Note){
        binding.apply {
            if(noteTitle.text.toString() != note.title){
                noteTitle.setText(note.title)
            }
            if(noteContentEt.text.toString() != note.description){
                noteContentEt.setText(note.description)
            }
        }
    }

}
