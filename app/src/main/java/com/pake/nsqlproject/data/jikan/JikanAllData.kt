package com.pake.nsqlproject.data.jikan

import com.pake.nsqlproject.data.jikan.data.JikanData
import com.pake.nsqlproject.data.jikan.links.JikanLinks
import com.pake.nsqlproject.data.jikan.meta.JikanMeta
import com.pake.nsqlproject.data.jikan.pagination.JikanPagination
import kotlinx.serialization.Serializable

@Serializable
data class JikanAllData (
    val pagination: JikanPagination,
    val data: MutableList<JikanData>,
    val links: JikanLinks?,
    val meta: JikanMeta?
)
