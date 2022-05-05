package com.pake.nsqlproject.data.jikan.meta.links
import kotlinx.serialization.Serializable

@Serializable
data class JikanMetaLinks (
    val url: String?,
    val label: String?,
    val active: Boolean?
)