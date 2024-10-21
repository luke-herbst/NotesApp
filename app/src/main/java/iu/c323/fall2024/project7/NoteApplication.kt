package iu.c323.fall2024.project7

import android.app.Application

class NoteApplication : Application() {
    override fun onCreate(){
        super.onCreate()
        NoteRepository.initialize(this)
    }
}