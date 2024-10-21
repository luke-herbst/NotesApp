package iu.c323.fall2024.project7

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class NoteDetailViewModel(noteId: UUID?): ViewModel() {
    private val noteId = noteId
    private val noteRepository = NoteRepository.get()
    private val _note: MutableStateFlow<Note?> = MutableStateFlow(null)
    val note: StateFlow<Note?> = _note.asStateFlow()
    init{
        viewModelScope.launch {
            try {
                if (noteId != null){
                    noteRepository.getNote(noteId).collect{
                        _note.value = it
                    }
                } else {
                    _note.value = Note(UUID.randomUUID(), "", "")
                }

            }
            catch(e:Throwable){
                println(e.message)
            }
        }
    }

    fun updateNote(onUpdate: (Note) -> Note) {
        _note.update { oldNote ->
            oldNote?.let {
                val updatedNote = onUpdate(it)
                viewModelScope.launch {
                    noteRepository.updateNote(updatedNote)
                }
                updatedNote
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        _note.value?.let {
            if (noteId !=null){
                noteRepository.updateNote(it)
            } else {
                noteRepository.addNote(it)
            }
        }
    }
}
class NoteDetailViewModelFactory(
    private val noteId: UUID?
): ViewModelProvider.Factory{
    override fun <N : ViewModel> create(modelClass: Class<N>): N {
        return NoteDetailViewModel(noteId) as N
    }
}