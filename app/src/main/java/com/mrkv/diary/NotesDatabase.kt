package com.mrkv.diary

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mrkv.diary.model.Note
import com.mrkv.diary.model.NotesDao

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun notes(): NotesDao

}