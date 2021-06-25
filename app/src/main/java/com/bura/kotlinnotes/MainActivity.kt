package com.bura.kotlinnotes

import android.content.Intent
import android.database.Observable
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class MainActivity : AppCompatActivity(), CustomAdapter.customOnItemClickListener {

    private var list = mutableListOf<Note>()
    private var adapter: CustomAdapter?=null
    private var db: NoteDatabase?=null
    private var notedao:NoteDao?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView);
        val fab: FloatingActionButton = findViewById(R.id.fab)
        val textView: TextView = findViewById(R.id.no_notes)


        db = Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java, "note_database"
        ).allowMainThreadQueries().build()//put in coroutines

        notedao=db?.noteDao()
        list = db?.noteDao()?.getNoteList()!!



        recyclerView.layoutManager = LinearLayoutManager(this)//vertical
        adapter = CustomAdapter(list, this)
        recyclerView.adapter = adapter

        if (list.size > 0){
            textView.isVisible = false
        }


        setSupportActionBar(findViewById(R.id.my_toolbar))

        fab.setOnClickListener { view ->

            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)

        }


    }

    fun update(recyclerView: RecyclerView){
        adapter = CustomAdapter(list, this)
        recyclerView.adapter = adapter
    }




    override fun onButtonClick(position: Int){
        val clickedItem: Note = list[position]

    }

    override fun onItemClick(position: Int) {

        val clickedItem: Note = list[position]
        val intent = Intent(this, NoteActivityEdit::class.java)
        intent.putExtra("position", position)
        intent.putExtra("title", clickedItem.title)
        intent.putExtra("desc", clickedItem.desc)
        startActivity(intent)

    }

/*
    private fun <R> CoroutineScope.executeAsyncTask(
        //onPreExecute: () -> Unit,
        doInBackground: () -> R,
       // onPostExecute: (R) -> Unit
    ) = launch {
       // onPreExecute()
        val result = withContext(Dispatchers.IO) { // runs in background thread without blocking the Main Thread
            doInBackground()
        }
       // onPostExecute(result)
    }
*/

    override fun onResume() {
        super.onResume()

        //refresh newly added notes
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val textView: TextView = findViewById(R.id.no_notes)

        list = db?.noteDao()?.getNoteList()!!
        adapter = CustomAdapter(list, this)
        recyclerView.adapter = adapter
        adapter!!.notifyDataSetChanged()
        recyclerView.scrollToPosition(list.size)


        if (list.size > 0){
            textView.isVisible = false
        }
    }
}