package com.info.noteapp.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.info.noteapp.Note
import com.info.noteapp.NoteDao

@Database(entities = [Note::class], version = 1)
abstract class NoteDataBase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

        companion object {
        @Volatile
        private var INSTANCE: NoteDataBase? = null

        fun getInstance(context: Context): NoteDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NoteDataBase::class.java, "database"
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

    }

}