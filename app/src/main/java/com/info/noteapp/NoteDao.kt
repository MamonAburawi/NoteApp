package com.info.noteapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAll(): LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE uid IN (:noteIds)")
    fun loadAllByIds(noteIds: IntArray): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg notes: Note)

    @Delete
    fun delete(note: Note)

    @Update(entity = Note::class)
    fun update(note: Note)
}