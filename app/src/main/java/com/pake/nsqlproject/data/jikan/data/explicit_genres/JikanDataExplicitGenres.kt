package com.pake.nsqlproject.data.jikan.data.explicit_genres
import kotlinx.serialization.Serializable

@Serializable
data class  JikanDataExplicitGenres (
    val mal_id: Int?,
    val type: String?,
    val name: String?,
    val url: String?
)