package com.example.contactsaver.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tblContact")
class Contact(
    @PrimaryKey(autoGenerate = true)
    private val id: Int,

    @ColumnInfo(name = "name")
    private  var name: String,

    @ColumnInfo(name = "phone")
    private  var phone: String
) : Parcelable {


    fun getId(): Int {
        return id
    }


    fun getName(): String {
        return name
    }

    fun setName(name: String):Unit {
        this.name = name
    }

    fun getPhone(): String {
        return phone
    }

    fun setPhone(phone: String):Unit {
        this.phone = phone
    }
}