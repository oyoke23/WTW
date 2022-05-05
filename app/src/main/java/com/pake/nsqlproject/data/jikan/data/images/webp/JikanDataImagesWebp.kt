package com.pake.nsqlproject.data.jikan.data.images.webp
import kotlinx.serialization.Serializable

@Serializable
data class JikanDataImagesWebp (
    val image_url: String?,
    val small_image_url: String?,
    val large_image_url: String?
)