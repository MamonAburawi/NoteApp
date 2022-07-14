package com.info.noteapp.repository

import android.util.Log
import com.info.noteapp.Note
import com.info.noteapp.NoteDao

class NoteRepository(private val noteDao: NoteDao) {

    companion object{
        const val TAG = "NoteRepository"
    }


    fun observeAll() = noteDao.getAll()

    suspend fun add(note: Note){
        noteDao.insertAll(note)
        Log.d(TAG,"adding")
    }

    suspend fun delete(note: Note){
        noteDao.delete(note)
        Log.d(TAG,"deleting")
    }

    suspend fun update(note: Note){
        noteDao.update(note)
        Log.d(TAG,"updating")
    }

}