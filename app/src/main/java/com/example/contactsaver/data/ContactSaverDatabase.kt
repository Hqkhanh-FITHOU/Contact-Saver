package com.example.contactsaver.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contactsaver.model.Contact

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactSaverDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDAO

    companion object {
        private var INSTANCE : ContactSaverDatabase? = null

        fun getInstance(context: Context): ContactSaverDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactSaverDatabase::class.java,
                    "contact-database").build()
                INSTANCE = instance
                return instance
            }
        }
    }

}