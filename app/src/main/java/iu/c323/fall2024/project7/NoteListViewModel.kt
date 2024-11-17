package iu.c323.fall2024.project7

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iu.c323.fall2024.project7.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

private const val TAG = "NoteListViewModel"

class NoteListViewModel: ViewModel() {
    val noteRepository = NoteRepository.get()

    private val _notes: MutableStateFlow<List<Note>> = MutableStateFlow(emptyList())
    val notes: StateFlow<List<Note>>
        get() = _notes.asStateFlow()

    init {
        viewModelScope.launch {
            noteRepository.getNotes().collect(){
                _notes.value = it
            }
        }
    }

    fun deleteNote(noteId: UUID) {
        viewModelScope.launch {
            noteRepository.deleteNote(noteId)
        }
    }


}


//package iu.c323.fall2024.project7
//
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.toObjects
//import iu.c323.fall2024.project7.model.Note
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import java.util.UUID
//
//private const val TAG = "NoteListViewModel"
//
//class NoteListViewModel: ViewModel() {
//    val noteRepository = NoteRepository.get()
//
//    private val _notes: MutableStateFlow<List<Note>> = MutableStateFlow(emptyList())
//    val notes: StateFlow<List<Note>>
//        get() = _notes.asStateFlow()
//
//    init {
//        val firestoreDb = FirebaseFirestore.getInstance()
//        var notesReference = firestoreDb
//            .collection("posts")
//            .limit(30)
//        viewModelScope.launch {
//            notesReference.addSnapshotListener{ snapshot, exception ->
//                if (exception != null || snapshot == null){
//                    Log.e(TAG, "Exception when querying notes", exception)
//                    return@addSnapshotListener
//                }
//                val noteList = snapshot.toObjects<Note>()
//                _notes.value = noteList as MutableList<Note>
//                for(note in noteList){
//                    Log.i(TAG, "Note: {$note")
//                }
//            }
//        }
//    }
//
//    fun deleteNote(noteId: UUID) {
//        viewModelScope.launch {
//            noteRepository.deleteNote(noteId)
//        }
//    }
//
//
//}







