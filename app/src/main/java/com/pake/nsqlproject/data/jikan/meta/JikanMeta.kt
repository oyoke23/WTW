package com.pake.nsqlproject.data.jikan.meta
import com.pake.nsqlproject.data.jikan.meta.links.JikanMetaLinks
import kotlinx.serialization.Serializable

@Serializable
data class JikanMeta (
    val current_page: Int?,
    val from: Int?,
    val last_page: Int?,
    val links: MutableList<JikanMetaLinks>,
    val path : String?,
    val per_page: Int?,
    val to: Int?,
    val total: Int?
)