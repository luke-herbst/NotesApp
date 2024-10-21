package iu.c323.fall2024.project7

import android.content.Context
import androidx.room.Room
import iu.c323.fall2024.project7.database.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.UUID

private const val DATABASE_NAME = "note-database"

class NoteRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope) {

    private val database: NoteDatabase = Room.databaseBuilder(
        context.applicationContext,
        NoteDatabase::class.java,
        DATABASE_NAME
    )
        .build()

    fun getNotes(): Flow<List<Note>> = database.noteDao().getNotes()
    fun getNote(id: UUID): Flow<Note> = database.noteDao().getNote(id)
    fun updateNote(note: Note) {
        coroutineScope.launch {
            database.noteDao().updateNote(note)
        }

    }

    fun addNote(note: Note){
        coroutineScope.launch {
            database.noteDao().addNote(note)
        }
    }
    fun deleteNote(noteId: UUID) {
        coroutineScope.launch {
            val noteToDelete = getNote(noteId).first()
            database.noteDao().deleteNote(noteToDelete)
        }
    }

    companion object{
        private var INSTANCE: NoteRepository? = null

        fun initialize(context: Context){
            if(INSTANCE == null){
                INSTANCE = NoteRepository(context)
            }
        }
        fun get(): NoteRepository{
            return INSTANCE ?:
            throw IllegalStateException(
                "NoteRepository must be initialized"
            )
        }
    }
}