package com.bura.kotlinnotes

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.room.Room


class NoteActivity : AppCompatActivity() {

    private var list = mutableListOf<Note>()
    private var adapter: CustomAdapter?=null
    private var db: NoteDatabase?=null
    private var notedao:NoteDao?=null

    var created: Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        val textView1: TextView = findViewById(R.id.titleView)
        val textView2: TextView = findViewById(R.id.descView)
        val saveButton: Button = findViewById(R.id.save_button)

        var savedTitle: String = ""
        var savedPosition: Int ?= null
        //val recyclerView: RecyclerView = findViewById(R.id.recyclerView)


        val toolbar: Toolbar = findViewById(R.id.note_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Add a new note"

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

        //val titleText:String = intent.getStringExtra("title").toString()
        //textView1.text = titleText



        saveButton.setOnClickListener{
            
            var dbpos = 0;
            for (i in list + 1){// TODO: 22.06.2021 +1???
                dbpos++;
                if (!created){
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

            val toast: Toast = Toast.makeText(this,"NOTE SAVED", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.duration = Toast.LENGTH_SHORT
            toast.show()

            if (!created){
                val position = list.size + 1 // TODO: 22.06.2021 +1???
                val note = Note(position, textView1.text.toString(), textView2.text.toString())
                with(notedao) {
                    this?.insert(note)
                    list.add(note)
                    savedTitle = textView1.text.toString()
                    savedPosition = position
                    created=true
                }
            }else{
                //val position = list.size - 1 // TODO: 22.06.2021 -1???
                //val note = Note(position, textView1.text.toString(), textView2.text.toString())
                with(notedao) {
                    this?.updateItem(savedPosition!! ,textView1.text.toString(), textView2.text.toString())
                }
            }
        }
    }
}