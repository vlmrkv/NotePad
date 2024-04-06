package com.mrkv.diary

import android.app.Application
import com.mrkv.diary.data.AppContainer
import com.mrkv.diary.data.AppDataContainer

class NotesApplication : Application() {

    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}