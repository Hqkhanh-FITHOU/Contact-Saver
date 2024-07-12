package com.example.contactsaver

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.contactsaver.model.Contact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContactScreen(
    contact: Contact,
    openEditContactScreen: () -> Unit = {},
    backToHomeScreen: () -> Unit = {},
    deleteContact: () -> Unit = {}
){
    val openAlertDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar (
                title = {Text("Detail")},
                navigationIcon = {
                    IconButton(onClick = backToHomeScreen) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                actions = {
                    IconButton(onClick = openEditContactScreen) {
                        Icon(Icons.Filled.Edit, "editIcon")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    actionIconContentColor = Color.DarkGray,
                )
            )
        }
    ) {innerPadding -> 
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(start = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = "ID : ${contact.getId()}", modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(), fontSize = 18.sp)
            Text(text = "Name : ${contact.getName()}" , modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth(), fontSize = 18.sp)
            Text(text = "Phone : ${contact.getPhone()}" , modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth(), fontSize = 18.sp)
            Button(
                onClick = {
                    openAlertDialog.value = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .padding(top = 18.dp)
            ) {
                Icon(Icons.Filled.Delete, "editIcon", modifier = Modifier.padding(end = 4.dp))
                Text(text = "Delete contact", fontSize = 16.sp)
            }
        }
    }
    when {
        openAlertDialog.value -> {
            AlertDialogToDelete(
                onDismissRequest = { openAlertDialog.value = false},
                onConfirmation = { openAlertDialog.value = false; deleteContact() },
                dialogTitle = "Delete contact",
                dialogText = "Do yo really want to delete this contact?",
                icon = Icons.Filled.Warning
            )
        }
    }

}

@Composable
fun AlertDialogToDelete(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector
){
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Warning Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailContactScreenPreview(){
    DetailContactScreen(Contact(1, "Khanh", "029284345"))
}