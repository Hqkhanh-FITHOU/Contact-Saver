package com.example.contactsaver.data

import androidx.lifecycle.LiveData
import com.example.contactsaver.model.Contact
import kotlinx.coroutines.flow.Flow

class ContactRepository(
    private val contactBD: ContactSaverDatabase
) {
    val getAllContactFromRoom : Flow<List<Contact>> = contactBD.contactDao().getAllContact()

    suspend fun addContactToRoom(contact: Contact){
        contactBD.contactDao().insertContact(contact)
    }

    suspend fun updateContactInRoom(contact: Contact){
        contactBD.contactDao().updateContact(contact)
    }

    suspend fun deleteContactFromRoom(contact: Contact){
        contactBD.contactDao().deleteContact(contact)
    }
}