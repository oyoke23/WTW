package com.pake.nsqlproject.data.jikan.data.published.prop
import com.pake.nsqlproject.data.jikan.data.published.prop.from.JikanDataPublishedPropFrom
import com.pake.nsqlproject.data.jikan.data.published.prop.to.JikanDataPublishedPropTo
import kotlinx.serialization.Serializable

@Serializable
data class JikanDataPublishedProp (
    val from: JikanDataPublishedPropFrom,
    val to: JikanDataPublishedPropTo
        )