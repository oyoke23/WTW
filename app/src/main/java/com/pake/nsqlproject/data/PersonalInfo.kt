package com.pake.nsqlproject.data
import kotlinx.serialization.Serializable

@Serializable
data class PersonalInfo (val id: String, val name: String, val email: String, val hash: String)