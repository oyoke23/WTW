package com.pake.nsqlproject
import kotlinx.serialization.Serializable

@Serializable
data class PersonalList(var id: String, var user: String, var name: String, var books: List<Book>) {
}