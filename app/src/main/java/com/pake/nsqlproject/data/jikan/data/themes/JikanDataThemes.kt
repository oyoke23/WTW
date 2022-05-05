package com.pake.nsqlproject.data.jikan.data.themes
import kotlinx.serialization.Serializable

@Serializable
data class JikanDataThemes (
    val mal_id: Int?,
    val type: String?,
    val name: String?,
    val url: String?
)