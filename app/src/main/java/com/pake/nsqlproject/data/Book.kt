package com.pake.nsqlproject.data
import kotlinx.serialization.Serializable

@Serializable
data class Book (
    var id: Int,
    var name: String,
    var image: String,
    var synopsis: String,
    var status: String,
    var readCh: Int,
    var totalCh: Int,
    var score: String)
