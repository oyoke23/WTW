package com.pake.nsqlproject.data.jikan.data.images.jpg

import kotlinx.serialization.Serializable

@Serializable
data class  JikanDataImagesJpg (
    val image_url: String?,
    val small_image_url: String?,
    val large_image_url: String?
)