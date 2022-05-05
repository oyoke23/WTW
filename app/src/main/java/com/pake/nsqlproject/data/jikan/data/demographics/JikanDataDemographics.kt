package com.pake.nsqlproject.data.jikan.data.demographics
import kotlinx.serialization.Serializable

@Serializable
data class JikanDataDemographics (
    val mal_id: Int?,
    val type: String?,
    val name: String?,
    val url: String?
)