package com.mrkv.diary.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "text") var textNote: String?,
    @ColumnInfo(name = "audio") val audioNote: String?,
    @ColumnInfo(name = "date") val date: String
)
