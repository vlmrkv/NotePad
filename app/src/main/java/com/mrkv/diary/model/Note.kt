package com.mrkv.diary.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey val id: Int?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "text") val textNote: String?,
    @ColumnInfo(name = "audio") val audioNote: String?,
    @ColumnInfo(name = "date") val date: String?
)
