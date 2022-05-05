package com.pake.nsqlproject.data.jikan.data
import com.pake.nsqlproject.data.jikan.data.authors.JikanDataAuthors
import com.pake.nsqlproject.data.jikan.data.demographics.JikanDataDemographics
import com.pake.nsqlproject.data.jikan.data.explicit_genres.JikanDataExplicitGenres
import com.pake.nsqlproject.data.jikan.data.genres.JikanDataGenres
import com.pake.nsqlproject.data.jikan.data.images.JikanDataImages
import com.pake.nsqlproject.data.jikan.data.published.JikanDataPublished
import com.pake.nsqlproject.data.jikan.data.serializations.JikanDataSerializations
import com.pake.nsqlproject.data.jikan.data.themes.JikanDataThemes
import kotlinx.serialization.Serializable

@Serializable
data class JikanData (
    val mal_id: Int?,
    val url: String?,
    val images: JikanDataImages?,
    val title: String?,
    val title_english: String?,
    val title_japanese: String?,
    val title_synonyms: MutableList<String>,
    val type: String?,
    val chapters: Int?,
    val volumes: Int?,
    val status: String?,
    val publishing: Boolean?,
    val published: JikanDataPublished,
    val score: Double?,
    val scored: Double?,
    val scored_by: Int?,
    val rank: Int?,
    val popularity: Int?,
    val members: Int?,
    val favorites: Int?,
    val synopsis: String?,
    val background: String?,
    val authors: MutableList<JikanDataAuthors>,
    val serializations: MutableList<JikanDataSerializations>,
    val genres: MutableList<JikanDataGenres>,
    val explicit_genres: MutableList<JikanDataExplicitGenres>,
    val themes: MutableList<JikanDataThemes>,
    val demographics: MutableList<JikanDataDemographics>
)