package iu.c323.fall2024.project7.database


import androidx.room.TypeConverter
import java.util.UUID

class NoteTypeConverter {
    @TypeConverter
    fun fromUUID(id: UUID): String{
        return id.toString()
    }

    @TypeConverter
    fun toUUID(idString: String): UUID{
        return UUID.fromString(idString)
    }
}