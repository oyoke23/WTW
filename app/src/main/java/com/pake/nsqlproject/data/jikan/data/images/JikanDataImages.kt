package com.pake.nsqlproject.data.jikan.data.images

import com.pake.nsqlproject.data.jikan.data.images.jpg.JikanDataImagesJpg
import com.pake.nsqlproject.data.jikan.data.images.webp.JikanDataImagesWebp
import kotlinx.serialization.Serializable

@Serializable
data class  JikanDataImages (
    val jpg: JikanDataImagesJpg,
    val webp: JikanDataImagesWebp
    )