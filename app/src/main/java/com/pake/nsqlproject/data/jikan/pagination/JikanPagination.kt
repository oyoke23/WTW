package com.pake.nsqlproject.data.jikan.pagination

import com.pake.nsqlproject.data.jikan.pagination.items.JikanPaginationItems
import kotlinx.serialization.Serializable

@Serializable
data class JikanPagination (
    val last_visible_page: Int?,
    val has_next_page: Boolean?,
    val current_page: Int?,
    val items: JikanPaginationItems
    )
