package com.pake.nsqlproject.data.jikan.links
import kotlinx.serialization.Serializable

@Serializable
data class JikanLinks (
    val first: String?,
    val last: String?,
    val prev: String?,
    val next: String?,
)