package com.pake.nsqlproject.data.jikan.data.published.prop.to
import kotlinx.serialization.Serializable

@Serializable
data class JikanDataPublishedPropTo (
    val day: Int?,
    val month: Int?,
    val year: Int?
        )