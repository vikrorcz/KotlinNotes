package com.bura.kotlinnotes

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class NoteActivityEdit: AppCompatActivity() {
    private var list = mutableListOf<Note>()
    private var adapter: CustomAdapter?=null
    private var db: NoteDatabase?=null
    private var notedao:NoteDao?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        val textView1: TextView = findViewById(R.id.titleView)
        val textView2: TextView = findViewById(R.id.descView)
        var saveButton: Button = findViewById(R.id.save_button)

        saveButton.setText("EDIT")

        val toolbar: Toolbar = findViewById(R.id.note_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Edit note"

            // show back button on toolbar
            // on back button press, it will navigate to parent activity
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        db = Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java, "note_database"
        ).allowMainThreadQueries().build()//put in coroutines

        notedao=db?.noteDao()
        list = db?.noteDao()?.getNoteList()!!

        val titleText:String = intent.getStringExtra("title").toString()
        val titleDescText:String = intent.getStringExtra("desc").toString()
        val position: Int = intent.getIntExtra("position",0) + 1// TODO: 23.06.2021 +1?
        textView1.text = titleText
        textView2.text = titleDescText



        saveButton.setOnClickListener{


            var dbpos = 0;
            for (i in list + 1){// TODO: 22.06.2021 +1??? index()??
                dbpos++;

                if (dbpos != position){
                    if (list.contains(Note(dbpos,textView1.text.toString(),""))){
                        val toast: Toast = Toast.makeText(this,"A NOTE WITH THIS NAME ALREADY EXISTS", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.duration = Toast.LENGTH_SHORT
                        toast.show()
                        return@setOnClickListener
                    }
                }
            }

            if (textView1.text.toString().trim().isEmpty()) {
                val toast: Toast = Toast.makeText(this, "PLEASE ADD A TITLE", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.duration = Toast.LENGTH_SHORT
                toast.show()
                return@setOnClickListener
            }

            val toast: Toast = Toast.makeText(this,"NOTE EDITED", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.duration = Toast.LENGTH_SHORT
            toast.show()

            with(notedao) {
                this?.updateItem(position,textView1.text.toString(), textView2.text.toString())
            }
        }
    }



}