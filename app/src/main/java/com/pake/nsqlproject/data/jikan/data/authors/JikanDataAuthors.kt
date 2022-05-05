package com.pake.nsqlproject.data.jikan.data.authors
import kotlinx.serialization.Serializable

@Serializable
data class JikanDataAuthors(
    val mal_id: Int?,
    val type: String?,
    val name: String?,
    val url: String?
)