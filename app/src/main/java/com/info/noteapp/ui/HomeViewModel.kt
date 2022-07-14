package com.info.noteapp.ui

import android.app.Application
import androidx.lifecycle.*
import com.info.noteapp.Note
import com.info.noteapp.local.NoteDataBase
import com.info.noteapp.repository.NoteRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private lateinit var repository: NoteRepository
    lateinit var notes: LiveData<List<Note>>

    init {
        val noteDao =  NoteDataBase.getInstance(application).noteDao()
        repository = NoteRepository(noteDao)
        notes = repository.observeAll()

    }



    fun add(note: Note){
        viewModelScope.launch {
            repository.add(note)
        }
    }

    fun delete(note: Note){
        viewModelScope.launch {
            repository.delete(note)
        }
    }

    fun update(note: Note) {
        viewModelScope.launch {
            repository.update(note)
        }
    }


}