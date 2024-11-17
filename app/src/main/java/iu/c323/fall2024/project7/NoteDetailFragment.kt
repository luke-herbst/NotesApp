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
import com.google.firebase.firestore.FirebaseFirestore
import iu.c323.fall2024.project7.databinding.FragmentNoteDetailBinding
import iu.c323.fall2024.project7.model.Note
import iu.c323.fall2024.project7.model.User
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
    private var signedInUser: User? = null
    private lateinit var firestoreDb: FirebaseFirestore


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


//package iu.c323.fall2024.project7
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.lifecycleScope
//import androidx.lifecycle.repeatOnLifecycle
//import androidx.navigation.fragment.findNavController
//import androidx.navigation.fragment.navArgs
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//import iu.c323.fall2024.project7.databinding.FragmentNoteDetailBinding
//import iu.c323.fall2024.project7.model.Note
//import iu.c323.fall2024.project7.model.User
//import kotlinx.coroutines.launch
//import java.util.UUID
//
//private const val TAG ="NoteDetailFragment"
//class NoteDetailFragment: Fragment() {
//    private val args: NoteDetailFragmentArgs by navArgs()
//    private val noteDetailViewModel: NoteDetailViewModel by viewModels{
//        NoteDetailViewModelFactory(args.noteId)
//    }
//    private var _binding: FragmentNoteDetailBinding? = null
//    private val binding
//        get() = checkNotNull(_binding){
//            "Cannot access the view because it is null"
//        }
//    private var signedInUser: User? = null
//    private lateinit var firestoreDb: FirebaseFirestore
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
//        binding.apply {
//            saveButton.setOnClickListener {
//                saveTheNote()
//
//            }
//        }
//        getTheCurrentUser()
//        return binding.root
//
//    }
//
//
//
//
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    private fun getTheCurrentUser(){
//        firestoreDb.collection("users")
//            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
//            .get()
//            .addOnSuccessListener { userSnapshot ->
//                signedInUser = userSnapshot.toObject(User::class.java)
//                Log.i(TAG, "signed in user: $signedInUser")
//            }
//            .addOnFailureListener{ exception ->
//                Log.i(TAG, "Failure fetching signed in user", exception)
//            }
//    }
//
//    private fun saveTheNote(){
//        val note = Note(
//            UUID.randomUUID(),
//            binding.noteTitle.text.toString(),
//            binding.noteContentEt.text.toString()
//        )
//        firestoreDb.collection("notes").add(note).addOnCompleteListener {
//            this.findNavController().navigate(R.id.noteListFragment)
//        }
//
//    }
//
//    private fun updateUi(note: Note){
//        binding.apply {
//            if(noteTitle.text.toString() != note.title){
//                noteTitle.setText(note.title)
//            }
//            if(noteContentEt.text.toString() != note.description){
//                noteContentEt.setText(note.description)
//            }
//        }
//    }
//
//}