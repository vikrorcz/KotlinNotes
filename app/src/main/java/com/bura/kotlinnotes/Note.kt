package com.bura.kotlinnotes

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * https://developer.android.com/training/data-storage/room
 */

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    //@ColumnInfo(name = "note")
    @ColumnInfo(name = "title")
    var title:String,
    @ColumnInfo(name="desc")
    var desc: String

)
