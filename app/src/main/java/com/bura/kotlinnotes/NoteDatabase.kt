package com.bura.kotlinnotes

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao


/*
    companion object{
        var instance: NoteDatabase?=null
        fun getAppDatabase(context: Context):NoteDatabase?{
            if (instance == null){
                synchronized(NoteDatabase::class){

                    instance = Room.databaseBuilder(context.applicationContext,NoteDatabase::class.java, "notedb").fallbackToDestructiveMigration().allowMainThreadQueries().build()

                }
            }

            return instance
        }
    }
*/
/*
    private fun <R> CoroutineScope.executeAsyncTask(
        onPreExecute: () -> Unit,
        doInBackground: () -> R,
        onPostExecute: (R) -> Unit
    ) = launch {
        onPreExecute()
        val result = withContext(Dispatchers.IO) { // runs in background thread without blocking the Main Thread
            doInBackground()
        }
        onPostExecute(result)
    }
*/
}