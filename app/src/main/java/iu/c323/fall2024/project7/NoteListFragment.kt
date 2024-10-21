package iu.c323.fall2024.project7


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import iu.c323.fall2024.project7.databinding.FragmentNoteListBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.UUID


class NoteListFragment: Fragment() {

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = checkNotNull(_binding){
        "Cannot access binding because it is not null."
    }

    private val noteListViewModel: NoteListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding  = FragmentNoteListBinding.inflate(inflater,container,false)
        binding.noteRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteListViewModel.notes.collect { notes ->
                    binding.noteRecyclerView.adapter = NoteListAdapter(notes,
                        onNoteClicked = { noteId ->
                            findNavController().navigate(NoteListFragmentDirections.showNoteDetail(noteId))
//                            findNavController().navigate(R.id.show_note_detail)

//                            findNavController().navigate(NoteListFragmentDirections.showNoteDetail())

                        },
                        onDeleteClick = { noteId ->
                            noteListViewModel.deleteNote(noteId)
                        }
                    )
                }
            }
        }
        binding.addNoteButton.setOnClickListener {
            findNavController().navigate(NoteListFragmentDirections.showNoteDetail(null))
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}