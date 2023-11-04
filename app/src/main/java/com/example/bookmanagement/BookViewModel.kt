package com.example.bookmanagement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookViewModel : ViewModel() {
    private val _books: MutableLiveData<List<Book>> by lazy {
        MutableLiveData<List<Book>>().apply {
            value = mutableListOf( Book("Harry Potter and the Goblet of Fire", "J. K. Rowling", "Fantasy", 300, "To Read"),
                Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 200, "Read"),
                Book("To Kill a Mockingbird", "Harper Lee", "Action", 336, "In Progress"),
                Book("Pride and Prejudice", "Jane Austen", "Romance", 432, "To Read"),
                Book("1984", "George Orwell", "Action", 328, "Read"),
                Book("Ion", "Liviu Rebreanu", "Drama", 450, "In Progress"),
                Book("The Alchemist", "Paulo Coelho", "Fiction", 208, "To Read"),
                Book("The Kite Runner", "Khaled Hosseini", "Fantasy", 371, "Read"),
            )
        }
    }

    val books: LiveData<List<Book>> get() = _books




    fun addBook(book: Book) {
        val updatedList = books.value?.toMutableList() ?: mutableListOf()
        updatedList.add(book)
        _books.value = updatedList
    }

    fun updateBook(book: Book) {
        val updatedList = books.value?.toMutableList() ?: mutableListOf()
        val index = updatedList.indexOfFirst { it.status == book.status }
        updatedList[index] = book
        _books.value = updatedList
    }

    fun deleteBook(book: Book) {
        val updatedList = books.value?.toMutableList() ?: mutableListOf()
        updatedList.remove(book)
        _books.value = updatedList
    }

}