package iu.c323.fall2024.project7

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
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








