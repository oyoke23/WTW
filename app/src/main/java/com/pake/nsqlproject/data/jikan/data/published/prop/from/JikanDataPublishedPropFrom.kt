package com.pake.nsqlproject.data.jikan.data.published.prop.from
import kotlinx.serialization.Serializable

@Serializable
data class JikanDataPublishedPropFrom(
    val day: Int?,
    val month: Int?,
    val year: Int?
        )