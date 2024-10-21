package iu.c323.fall2024.project7

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Note(
    @PrimaryKey val id: UUID,
    val title: String,
    val description: String
)
