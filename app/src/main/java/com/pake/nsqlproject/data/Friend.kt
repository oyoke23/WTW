package com.pake.nsqlproject.data
import kotlinx.serialization.Serializable

@Serializable
data class Friend(var id: String, var name: String, var hash: String)