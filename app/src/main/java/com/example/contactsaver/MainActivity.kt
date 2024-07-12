

package com.example.contactsaver

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.contactsaver.data.ContactRepository
import com.example.contactsaver.data.ContactSaverDatabase
import com.example.contactsaver.model.Contact
import com.example.contactsaver.ui.theme.ContactSaverTheme
import com.example.contactsaver.viewmodel.ContactViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactSaverTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val context = LocalContext.current
                    val db = ContactSaverDatabase.getInstance(context)
                    val repository = ContactRepository(db)
                    val viewModel = ContactViewModel(repository)
                    MainApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MainApp(viewModel: ContactViewModel) {

    val contactList by viewModel.contacts.collectAsState(initial = emptyList())

    val navigationController = rememberNavController()
    ContactSaverTheme {
        NavHost(navController = navigationController, startDestination = "home") {
            composable("home") {
                MainScreen(
                    navController = navigationController,
                    items = contactList,
                    openAddContactScreen = {
                        navigationController.navigate("add")
                    }
                )
            }
            composable("add") {
                AddConTactScreen(
                    backToHomeSreen = {
                        navigationController.popBackStack()
                    },
                    addNewContact = { name, phone ->
                        if(name.isNotEmpty() && phone.isNotEmpty()){
                            val contact = Contact(0,name, phone)
                            viewModel.addContact(contact)
                        }
                        navigationController.popBackStack()
                    },
                )
            }


            navigation(startDestination = "detail", route = "information"){
                composable("detail") {
                    val contact = navigationController.previousBackStackEntry?.savedStateHandle?.get<Contact>("currentContact")
                    if (contact != null) {
                        DetailContactScreen(
                            contact = contact,
                            backToHomeScreen = {
                                navigationController.popBackStack()
                            },
                            openEditContactScreen = {
                                navigationController.currentBackStackEntry?.savedStateHandle?.set(
                                    key = "editContact",
                                    value = contact
                                )
                                navigationController.navigate("edit")
                            },
                            deleteContact = {
                                viewModel.deleteContact(contact)
                                navigationController.popBackStack()
                            }
                        )
                    }
                }
                composable("edit") {
                    val contact = navigationController.previousBackStackEntry?.savedStateHandle?.get<Contact>("editContact")
                    if (contact != null) {
                        EditContactScreen(
                            contact = contact,
                            confirmEdit = {contactUpdated ->
                                viewModel.updateContact(contactUpdated)
                                navigationController.popBackStack()
                            },
                            cancelEdit = {
                                navigationController.popBackStack()
                            })
                    }
                }
            }


        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchField(
    state : MutableState<String>,
    onSortings: () -> Unit = {},
    onCancelSorting: () -> Unit = {},
) {

    var active by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }
    val recentQueries = remember { mutableStateListOf("Khanh", "123", "trinh") }
    val filterQueries = recentQueries.filter { searhString -> searhString.contains(state.value, ignoreCase = true) }
    SearchBar(
        query = state.value,
        onQueryChange = {
            state.value = it
        },
        onSearch = { inputString -> //event when click search button on keyboard
            recentQueries.add(inputString)
            active = false
            state.value = inputString
        },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text("Search") },
        leadingIcon = {Icon(Icons.Default.Search, contentDescription = null)},
        trailingIcon = {
            if (active) {
                IconButton(onClick = {
                    state.value = ""
                    active = false
                }) {
                    Icon(Icons.Default.Close, contentDescription = null)
                }
            }else {
                IconButton(onClick = {
                    showMenu = true
                }) {
                    Icon(Icons.Default.Menu, contentDescription = null)
                }

                DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                    DropdownMenuItem(
                        text = { Text("Sort by name") },
                        onClick = {

                            onSortings()
                            showMenu = false
                        }
                       ,
                        leadingIcon = {
                            Icon(Icons.Default.Sort, contentDescription = null)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Cancel sorting") },
                        onClick = {
                            onCancelSorting()
                            showMenu = false
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Cancel, contentDescription = null)
                        }
                    )
                }
            }
        },
    ) {
        filterQueries.forEach {
            Row(
                modifier = Modifier
                    .padding(14.dp)
                    .clickable {
                        state.value = it
                        active = false
                    },
            ) {
                Icon(
                    Icons.Default.History,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(text = it)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactItem(contact: Contact, modifier: Modifier = Modifier, navController: NavHostController) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        onClick = {
            navController.currentBackStackEntry?.savedStateHandle?.set(
                key = "currentContact",
                value = contact
            )
            navController.navigate("information")
        }
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
        ){
            Text(
                text = contact.getName(),
                fontWeight = FontWeight.Bold,

                )
            Text(
                text = contact.getPhone(),
            )
        }
    }
}

@Composable
fun AddFloatingButton(
    openAddContactScreen:() -> Unit
) {
    val context = LocalContext.current
    FloatingActionButton(
        onClick = openAddContactScreen
    ){
        Icon(Icons.Default.Add, contentDescription = null)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactList(
    items: List<Contact>,
    searchString: String,
    openAddContactScreen: () -> Unit,
    navController: NavHostController,
) {
    Scaffold(
        floatingActionButton = { AddFloatingButton(openAddContactScreen) }
    ) { padding ->
        if(items.isEmpty()){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "No contacts available")
            }
        }else{
            LazyColumn(
                modifier = Modifier.padding(top = 18.dp, bottom = 13.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items.filter { it.getName().contains(searchString, ignoreCase = true) }, key = {it.hashCode()}
                ){ contact ->
                    ContactItem(
                        contact,
                        modifier = Modifier.animateItemPlacement(
                            animationSpec = tween(
                                durationMillis = 500
                            )
                        ),
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    items: List<Contact>,
    openAddContactScreen: () -> Unit = {},
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    val searchState = remember { mutableStateOf("") }
    val isSorting = remember { mutableStateOf(false) }
    val sortedList = when(isSorting.value){
        true -> items.sortedBy { it.getName().lowercase() }
        false -> items
    }
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ){
        MySearchField(
            state = searchState,
            onSortings = {
                isSorting.value = true
            },
            onCancelSorting = {
                isSorting.value = false
            }
        )
        ContactList(
            items = sortedList,
            searchString = searchState.value,
            openAddContactScreen = openAddContactScreen,
            navController = navController,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ContactSaverTheme {
        MainScreen(items = listOf(),openAddContactScreen = {},modifier = Modifier.fillMaxSize(), navController = rememberNavController())
    }
}
