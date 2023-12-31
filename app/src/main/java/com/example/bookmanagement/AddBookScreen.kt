package com.example.bookmanagement

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.activity.ComponentActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(addBook: (Book) -> Unit, navController: NavController) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var pageCount by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf(false) }
    var authorError by remember { mutableStateOf(false) }
    var genreError by remember { mutableStateOf(false) }
    var pageCountError by remember { mutableStateOf(false) }
    var statusError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Add a new Book")
                },
                navigationIcon = {
                    //Back arrow icon to navigate to the book list
                    IconButton(
                        onClick = { navController.navigate("bookList") }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {// Home icon to navigate back to the home page
                    IconButton(
                        onClick = { navController.navigate("landing") }
                    ) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = null)
                    }

                }
            )
        })

    { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .fillMaxWidth()


            )
            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Author") },
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = genre,
                onValueChange = { genre = it },
                label = { Text("Genre") },
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = pageCount,
                onValueChange = { pageCount = it },
                label = { Text("Page Count") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = status,
                onValueChange = { status = it },
                label = { Text("Status") },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        if (isNotBlank(title) && isNotBlank(author) && isNotBlank(genre) &&
                            isValidPageCount(pageCount) && isValidStatus(status)
                        ) {
                            addBook(
                                Book(title, author, genre, pageCount.toInt(), status)
                            )
                            Toast.makeText(
                                context,
                                "Genre is required",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate("bookList")
                        } else {

                            // Set error flags accordingly
                            titleError = !isNotBlank(title)
                            authorError = !isNotBlank(author)
                            genreError = !isNotBlank(genre)
                            pageCountError = !isValidPageCount(pageCount)
                            statusError = !isValidStatus(status)
                            if (titleError) Toast.makeText(
                                context,
                                "Title is required",
                                Toast.LENGTH_SHORT
                            ).show()
                            if (authorError) Toast.makeText(
                                context,
                                "Author is required",
                                Toast.LENGTH_SHORT
                            ).show()
                            if (genreError) Toast.makeText(
                                context,
                                "Genre is required",
                                Toast.LENGTH_SHORT
                            ).show()
                            if (pageCountError) Toast.makeText(
                                context,
                                "Page Count must be a positive number",
                                Toast.LENGTH_SHORT
                            ).show()
                            if (statusError) Toast.makeText(
                                context,
                                "Status is required",
                                Toast.LENGTH_SHORT
                            ).show()
                            Toast.makeText(
                                context,
                                "Please fill in all fields correctly.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    },

                ) {
                    Text("Add Book")
                }
                Button(
                    onClick = { navController.navigate("bookList") },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Cancel")
                }
            }
        }

    }

    }

fun isNotBlank(value: String): Boolean {
    return value.isNotBlank()
}

fun isValidPageCount(value: String): Boolean {
    return value.toIntOrNull() != null && value.toInt() > 0
}

fun isValidStatus(value: String): Boolean {
    return value == "Read" || value == "In Progress" || value == "Not Read" || value.isNotBlank()
}