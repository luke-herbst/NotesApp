package iu.c323.fall2024.project7.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import iu.c323.fall2024.project7.model.Note
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id=:id")
    fun getNote(id: UUID): Flow<Note>

    @Update
    fun updateNote(note: Note)

    @Insert
    fun addNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}