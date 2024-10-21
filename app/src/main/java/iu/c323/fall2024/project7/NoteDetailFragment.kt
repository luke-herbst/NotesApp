package iu.c323.fall2024.project7

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import iu.c323.fall2024.project7.databinding.FragmentNoteDetailBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


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
                        description = noteDescription.text.toString()
                    )
                }
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
            if(noteDescription.text.toString() != note.description){
                noteDescription.setText(note.description)
            }
        }
    }

}