package com.bura.kotlinnotes

import androidx.room.*

//data access
@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)//also replace
    //@Query("select * from note_table ORDER BY name ASC")
    @Query("SELECT * FROM note_table")
    fun getNoteList():MutableList<Note>

    @Delete
    fun delete(note: Note)

    @Query("UPDATE note_table SET title = :title, `desc`= :desc WHERE id =:id")
    fun updateItem(id: Int, title: String?, desc: String?)

    @Update
    fun update(note: Note)

    @Query("DELETE FROM note_table WHERE id =:id")
    fun deleteAtPosition(id: Int)


}