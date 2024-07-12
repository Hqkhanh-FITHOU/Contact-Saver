package com.example.contactsaver.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsaver.data.ContactRepository
import com.example.contactsaver.model.Contact
import kotlinx.coroutines.launch

class ContactViewModel(
    val contactRepository: ContactRepository
) : ViewModel() {

    val contacts = contactRepository.getAllContactFromRoom

    fun addContact(contact: Contact){
        viewModelScope.launch {
            contactRepository.addContactToRoom(contact)
        }
    }

    fun updateContact(contact: Contact){
        viewModelScope.launch {
            contactRepository.updateContactInRoom(contact)
        }
    }

    fun deleteContact(contact: Contact){
        viewModelScope.launch {
            contactRepository.deleteContactFromRoom(contact)
        }
    }

}