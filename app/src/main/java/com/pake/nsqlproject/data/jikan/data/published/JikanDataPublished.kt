package com.pake.nsqlproject.data.jikan.data.published
import com.pake.nsqlproject.data.jikan.data.published.prop.JikanDataPublishedProp
import kotlinx.serialization.Serializable

@Serializable
data class JikanDataPublished (
    val from: String?,
    val to: String?,
    val prop: JikanDataPublishedProp?,
    val string: String?
        )