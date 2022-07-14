package com.info.noteapp.ui

import com.airbnb.epoxy.TypedEpoxyController
import com.info.noteapp.Note
import com.info.noteapp.note

class NoteController() : TypedEpoxyController<List<Note>>() {

    lateinit var clickListener: ClickListener



    override fun buildModels(data: List<Note>?) {
        data?.forEachIndexed { index, note ->
            noteView(note,index)
        }
    }


    private fun noteView(note: Note,index: Int) {

        note {
            id(note.uid.toString())
            note(note)
            onClick{ v-> clickListener.onEdit(index) }
        }

    }



    companion object{
        private const val TAG = "chatFragment"
    }


    interface ClickListener{
        fun onEdit(index: Int?)
    }



}