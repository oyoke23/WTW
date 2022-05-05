package com.pake.nsqlproject.data.jikan.data.genres
import kotlinx.serialization.Serializable

@Serializable
data class JikanDataGenres (
    val mal_id: Int?,
    val type: String?,
    val name: String?,
    val url: String?
        )