package com.example.contactsaver

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.contactsaver.model.Contact


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditContactScreen(
    contact: Contact,
    confirmEdit: (contactUpdated: Contact) -> Unit = {},
    cancelEdit: () -> Unit = {},
){
    val name = remember { mutableStateOf(contact.getName()) }
    val phone = remember { mutableStateOf(contact.getPhone()) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Edit") },
                navigationIcon = { IconButton(onClick = cancelEdit) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                } }
            )
        }
    ){innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ID: ${contact.getId()}",
                modifier = Modifier
                    .width(280.dp)
                    .padding(start = 4.dp),
                fontSize = 16.sp
            )
            OutlinedTextField(
                placeholder = { Text(text = "Name") },
                value = name.value,
                onValueChange = {name.value = it},
                modifier = Modifier.padding(top = 16.dp),
                label = { Text(text = "Name") }
            )
            OutlinedTextField(
                placeholder = { Text(text = "Name") },
                value = phone.value,
                onValueChange = {phone.value = it},
                modifier = Modifier.padding(top = 16.dp),
                label = { Text(text = "Phone") }
            )

            Button(
                onClick = {
                    val contactUpdated = contact
                    contactUpdated.setName(name.value)
                    contactUpdated.setPhone(phone.value)
                    confirmEdit(contactUpdated)
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(270.dp)
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.padding(end = 6.dp))
                Text(text = "Confirm")
            }

            Button(
                onClick = cancelEdit,
                modifier = Modifier.width(270.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {
                Icon(Icons.Default.Cancel, contentDescription = null,  modifier = Modifier.padding(end = 6.dp))
                Text(text = "Cancel  ")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EditContactScreenPreview(){
    EditContactScreen(contact = Contact(1,"Khanh","102933441"))
}