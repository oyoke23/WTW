package com.pake.nsqlproject.data.jikan
import com.pake.nsqlproject.data.jikan.data.JikanData
import com.pake.nsqlproject.data.jikan.pagination.JikanPagination
import kotlinx.serialization.Serializable

@Serializable
data class JikanWithoutMeta (
    val pagination: JikanPagination,
    val data: MutableList<JikanData>
)