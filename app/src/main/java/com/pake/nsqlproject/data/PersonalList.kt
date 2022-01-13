package com.pake.nsqlproject.data
import com.pake.nsqlproject.data.Book
import kotlinx.serialization.Serializable

@Serializable
data class PersonalList(var id: String,
                        var user: String,
                        var name: String,
                        var books: MutableList<Book>)