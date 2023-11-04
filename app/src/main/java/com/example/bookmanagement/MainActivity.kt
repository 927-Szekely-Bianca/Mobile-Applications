package com.example.bookmanagement

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bookmanagement.ui.theme.BookManagementTheme
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.composable



class MainActivity : ComponentActivity() {
    // Initialize your BookViewModel using viewModels delegate
    private val viewModel: BookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookManagementTheme {
                MainNavHost(viewModel,navController = rememberNavController())
            }
        }
    }

}


@Composable
fun LandingScreen(navController: NavController,userName:String) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(
            factory = { context ->
                LayoutInflater.from(context).inflate(R.layout.landing_page, null)
            },
            update = { view ->
                val helloUserText = view.findViewById<TextView>(R.id.hello_user)
                val welcomeText = view.findViewById<TextView>(R.id.welcome)
                val viewBooksButton = view.findViewById<Button>(R.id.view_books_button)

                helloUserText.text = "Hello, $userName!"
                welcomeText.text = "Welcome to your personal bookshelf!"

                viewBooksButton.setOnClickListener {
                    navController.navigate("bookList")
                }
            }
        )
    }
}

@Composable
fun MainNavHost(viewModel: BookViewModel,navController: NavController,) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "landing") {
        composable("landing") {
            LandingScreen(navController,"Bianca")
        }
        composable("bookList") {
            BookListScreen(books =viewModel.books,viewModel::deleteBook, navController = navController)
        }
        composable("addBook"){
            AddBookScreen(viewModel::addBook,navController=navController)
        }
        composable("editBook/{bookTitle}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("bookTitle")
            val selectedBook = viewModel.books.value?.find { it.title == title }
            EditBookScreen(viewModel::updateBook, selectedBook, navController)
        }
            //EditBookScreen(viewModel::updateBook,book=viewModel.books.value?,navController=navController)
        }
    }


