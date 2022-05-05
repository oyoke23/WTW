package com.pake.nsqlproject.data.jikan.data.serializations
import kotlinx.serialization.Serializable

@Serializable
data class JikanDataSerializations (
    val mal_id: Int?,
    val type: String?,
    val name: String?,
    val url: String?
        )