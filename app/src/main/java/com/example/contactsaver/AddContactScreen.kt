

package com.example.contactsaver

import android.R
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.contactsaver.model.Contact

@Composable
fun AddConTactScreen(
    backToHomeSreen: () -> Unit = {},
    addNewContact: (name: String, phone: String) -> Unit
){
    val textNameState = remember { mutableStateOf("") }
    val textPhoneState = remember { mutableStateOf("") }
    Scaffold(
        topBar = { ToolBarAddContact(backToHomeSreen) }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            OutlinedTextField(
                modifier = Modifier.width(280.dp),
                label = { Text(text = "Name") },
                value = textNameState.value,
                singleLine = true,
                onValueChange = {
                    textNameState.value = it
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .width(280.dp)
                    .padding(top = 10.dp),
                label = { Text(text = "Phone") },
                value = textPhoneState.value,
                singleLine = true,
                onValueChange = {
                    textPhoneState.value = it
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                )
            )
            Button(
                modifier = Modifier
                    .width(280.dp)
                    .padding(top = 10.dp),
                onClick = {
                    addNewContact(textNameState.value, textPhoneState.value)
                    Log.d("AddContactScreen", "Name: ${textNameState.value}, Phone: ${textPhoneState.value}")
                }
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                }
                Text(text = "Add")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBarAddContact(
    backToHomeSreen: () -> Unit
){
    CenterAlignedTopAppBar(
        title = { Text(text = "Add Contact") } ,
        navigationIcon = {
            IconButton(onClick = backToHomeSreen){
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactScreenPreview(){
    AddConTactScreen(backToHomeSreen = {}, addNewContact = {_,_->})
}