package iu.c323.fall2024.project7.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Note(
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    var title: String = "",
    var description: String= ""
)
