package com.pake.nsqlproject.data

import kotlinx.serialization.Serializable

@Serializable
data class ApiItem (
    val mal_id: Int,
    val title: String,
    val image: String,
    val chapters: Int,
    val synopsis: String,
    val members: Int?
)