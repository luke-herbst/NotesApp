package iu.c323.fall2024.project7.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import iu.c323.fall2024.project7.model.Note

@Database(entities = [Note::class], version = 1)
@TypeConverters(NoteTypeConverter::class)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun noteDao(): NoteDao

}