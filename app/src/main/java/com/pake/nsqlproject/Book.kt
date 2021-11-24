package com.pake.nsqlproject
import kotlinx.serialization.Serializable

@Serializable
data class Book (
    var name: String,
    var status: String,
    var readCh: String,
    var totalCh: String,
    var score: String) {
}
