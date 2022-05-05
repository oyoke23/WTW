package com.pake.nsqlproject.data.jikan.pagination.items

import kotlinx.serialization.Serializable

@Serializable
data class JikanPaginationItems (
    val count: Int?,
    val total: Int?,
    val per_page: Int?
    )