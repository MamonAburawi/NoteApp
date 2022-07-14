package com.info.noteapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.info.noteapp.Note
import com.info.noteapp.R
import com.info.noteapp.databinding.ActivityMainBinding
import com.info.noteapp.databinding.AddEditNoteBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: HomeViewModel
    private val noteController = NoteController()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]


        setObserves()
        setViews()
    }

    private fun setObserves() {

        /** notes live data **/
        viewModel.notes.observe(this){ notes ->
            if (!notes.isNullOrEmpty()){
                noteController.setData(notes)
            }

        }
    }



    private fun setViews() {
        binding.apply {

            setAdapter()

            /** add note **/
            btnAdd.setOnClickListener {
                addEditDialog(null, false, null)
            }

        }
    }


    private fun setAdapter() {

        /** listener **/
        noteController.clickListener = object : NoteController.ClickListener {


            override fun onEdit(index: Int?) {
                val item = index?.let { viewModel.notes.value?.get(it) } // new updated item
                addEditDialog(item,true, index)
            }

        }
        binding.rvNotes.adapter = noteController.adapter

    }



    fun addEditDialog(note: Note?, isEdit: Boolean, index: Int?) {
        val alertDialog = AlertDialog.Builder(this)
        var create: AlertDialog? = null
        val editNoteBinding = AddEditNoteBinding.inflate(layoutInflater,null,false)


        editNoteBinding.apply {

            if (note != null){
                editNoteBinding.note = note
            }


            /** delete note **/
            btnDelete.setOnClickListener {
                if (note != null) {
                    viewModel.delete(note)
                }
                create!!.dismiss()

            }

            /** save note **/
            btnSave.setOnClickListener {
                val newTitle = editNoteBinding.title.text.trim().toString()
                val newDescription = editNoteBinding.description.text.trim().toString()
                val data = Note(null,newTitle,newDescription)
                if (isEdit) { // update note
                    data.uid = note?.uid // we don't need change the not id
                    viewModel.update(data)
                }else{ // add new note
                    viewModel.add(data)
                }
                create!!.dismiss()
            }

            if (isEdit){
                btnDelete.visibility = View.VISIBLE
                noteId.visibility = View.VISIBLE

            }

        }

        alertDialog.setView(editNoteBinding.root)
        create = alertDialog.create()
        create.show()

    }

}