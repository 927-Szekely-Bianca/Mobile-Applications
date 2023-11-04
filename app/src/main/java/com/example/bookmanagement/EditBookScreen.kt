package com.example.bookmanagement
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBookScreen(updateBook:(Book) -> Unit,book: Book?, navController: NavController) {
    val context = LocalContext.current
    var updatedTitle by remember { mutableStateOf(book?.title ?: "") }
    var updatedAuthor by remember { mutableStateOf(book?.author ?: "") }
    var updatedGenre by remember { mutableStateOf(book?.genre ?: "") }
    var updatedPageCount by remember { mutableStateOf(book?.pageCount?.toString() ?: "") }
    var updatedStatus by remember { mutableStateOf(book?.status ?: "") }
    var titleError by remember { mutableStateOf(false) }
    var authorError by remember { mutableStateOf(false) }
    var genreError by remember { mutableStateOf(false) }
    var pageCountError by remember { mutableStateOf(false) }
    var statusError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Edit Book")
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
        Text(
            text = "Editing ${book?.title}",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp

        )
        OutlinedTextField(
            value = updatedTitle,
            onValueChange = { updatedTitle = it },
            label = { Text("Title") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = updatedAuthor,
            onValueChange = { updatedAuthor = it },
            label = { Text("Author") },
                    modifier = Modifier
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = updatedGenre,
            onValueChange = { updatedGenre = it },
            label = { Text("Genre") },
                    modifier = Modifier
                    .fillMaxWidth()
        )
        OutlinedTextField(
            value = updatedPageCount,
            onValueChange = { updatedPageCount = it },
            label = { Text("Page Count") },
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )
        OutlinedTextField(
            value = updatedStatus,
            onValueChange = { updatedStatus = it },
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
                    if (isNotBlank(updatedTitle) && isNotBlank(updatedAuthor) && isNotBlank(updatedGenre) &&
                        isValidPageCount(updatedPageCount) && isValidStatus(updatedStatus)
                    ) {
                        updateBook(Book(
                            updatedTitle,
                            updatedAuthor,
                            updatedGenre,
                            updatedPageCount.toInt(),
                            updatedStatus
                        ))
                        navController.navigate("bookList")
                    }
                    else{

                    // Set error flags accordingly
                    titleError = !isNotBlank(updatedTitle)
                    authorError = !isNotBlank(updatedAuthor)
                    genreError = !isNotBlank(updatedGenre)
                    pageCountError = !isValidPageCount(updatedPageCount)
                    statusError = !isValidStatus(updatedStatus)
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

                }
            ) {
                Text("Save Changes")
            }
            Button(
                onClick = { navController.navigate("bookList") },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Cancel")
            }
        }
    }
}}