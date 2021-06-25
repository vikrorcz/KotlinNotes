package com.bura.kotlinnotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room


//class CustomAdapter(private val customList: List<Note>, private val listener: customOnItemClickListener) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
class CustomAdapter(private val customList: MutableList<Note>, private val listener: customOnItemClickListener) : ListAdapter<Note, CustomAdapter.CustomViewHolder>(DiffCallback) {

    //private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
    //
    //}



    //single row in list
    inner class CustomViewHolder (view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val textView1: TextView = view.findViewById(R.id.text_view1)
        val textView2: TextView = view.findViewById(R.id.text_view2)

        private val button: Button = view.findViewById(R.id.imageView)


        private var db: NoteDatabase?=null
        private var notedao:NoteDao?=null

        val recyclerView: RecyclerView = (itemView.context as MainActivity).findViewById(R.id.recyclerView)
        //constructor
        // Define click listener for the ViewHolder's View.
        init {
            itemView.setOnClickListener(this)
            button.setOnClickListener(this)

            db = Room.databaseBuilder(
                (itemView.context as MainActivity),
                NoteDatabase::class.java, "note_database"
            ).allowMainThreadQueries().build()//put in coroutines

            notedao=db?.noteDao()

            val popup = PopupMenu(itemView.context, button)
            popup.inflate(R.menu.menu_note)

            button.setOnClickListener{
                val position:Int = absoluteAdapterPosition
                popup.setOnMenuItemClickListener { item ->
                    when(item.itemId){
                        R.id.delete->{

                            with(notedao){

                                this?.delete(customList[position])
                                customList.removeAt(position)

                                recyclerView.adapter!!.notifyItemRemoved(position)
                            }

                        }
                        //more options

                    }
                    true

                }
                popup.show()
            }


        }
/*
        fun onButtonClick(){
            button.setOnClickListener{
                val position:Int = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    listener.onItemClick(position)
                }
            }
        }*/



/*
        fun onButtonClick() {
            val position:Int = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION){
                button.setOnClickListener{
                    println("working")

                }

            }
        }*/
/*
        override fun onClick(view: View?) {
            val position:Int = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)

            }
        }
*/
        override fun onClick(view: View?) {

            val position:Int = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)

            }
        }


/*
        init {

            textView1 = view.findViewById(R.id.text_view1)
        }*/

    }

    interface customOnItemClickListener{
        fun onItemClick(position: Int)
        fun onButtonClick(position: Int)

    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CustomViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recyclerview_item, viewGroup, false)

        return CustomViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: CustomViewHolder, position: Int) {
        val currentItem = customList[position]
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView1.text = currentItem.title
        viewHolder.textView2.text = currentItem.desc


        //if (position == 0){
        //    viewHolder.textView1.setBackgroundColor(Color.YELLOW)
        //}

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = customList.size


    object DiffCallback : DiffUtil.ItemCallback<Note>() {//companion object????, class????

        // The ID property identifies when items are the same.
        override fun areItemsTheSame(oldItem: Note, newItem: Note) =
            oldItem.id == newItem.id

        // Check the properties that can change, or implements the equals method in Item class
        override fun areContentsTheSame(
            oldItem: Note, newItem: Note) = oldItem.title == newItem.title
    }



}