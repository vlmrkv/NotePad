package com.mrkv.diary

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mrkv.diary.model.Note
import com.mrkv.diary.model.NotesDao

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun notes(): NotesDao

    companion object {
        fun createDatabase(context: Context): NotesDatabase {
            return Room.databaseBuilder(
                context,
                NotesDatabase::class.java,
                "notes.db"
            ).build()
        }
    }

}