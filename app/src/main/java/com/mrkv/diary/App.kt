package com.mrkv.diary

import android.app.Application

class App : Application() {
    val database by lazy { NotesDatabase.createDatabase(this)}
}