package com.example.bookmanagement
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.LiveData
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(books: LiveData<List<Book>>, deleteBook: (Book)-> Unit, navController: NavController) {
    // Extract the value from LiveData
    val books = books.value ?: emptyList()
    var selectedBook by remember { mutableStateOf<Book?>(null) }
    var bookToDelete by remember { mutableStateOf<Book?>(null) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book List") },
                actions = {
                    // Plus icon to add a book
                    IconButton(
                        onClick = { navController.navigate("addBook") }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                },
                navigationIcon = {
                    // Home icon to navigate back to the home page
                    IconButton(
                        onClick = { navController.navigate("landing") }
                    ) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = null)
                    }
                }



            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding).padding(16.dp)
            //modifier=Modifier.padding(16.dp)
        ) {
            items(items = books) { book ->
                var expanded by remember { mutableStateOf(false) }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            // Set the selected book in the view model
                            selectedBook = book
                        }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = book.title, fontWeight = FontWeight.Bold)
                        Text(text = "Author: ${book.author}")
                        if (expanded) {
                            Text(text = "Genre: ${book.genre}")
                            Text(text = "Pages: ${book.pageCount}")
                            Text(text = "Status: ${book.status}")
                        }
                        Row(modifier = Modifier.padding(16.dp))
                        {
                            Button(
                                onClick = { expanded = !expanded },

                                ) {
                                Text(if (expanded) "Show less" else "Show more")
                            }

                            //Add edit button
                            Button(
                                onClick = {
                                    // Set the selected book in the view model
                                    selectedBook = book
                                    //navController.navigate("editBook")
                                    navController.navigate("editBook/${selectedBook!!.title}")
                                },

                                ) {
                                Text("Edit")
                            }

                            // Add delete button
                            Button(
                                onClick = { bookToDelete = book },
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }


    }
    // Confirmation Dialog for Delete
    if (bookToDelete != null) {
        AlertDialog(
            onDismissRequest = { bookToDelete = null },
            title = { Text("Confirm Deletion") },
            text = { Text("Are you sure you want to delete ${bookToDelete?.title}?") },
            confirmButton = {
                Button(
                    onClick = {
                        deleteBook(bookToDelete!!)
                        bookToDelete = null
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(
                    onClick = { bookToDelete = null }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

