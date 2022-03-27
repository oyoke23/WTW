package com.pake.nsqlproject.data
import kotlinx.serialization.Serializable

@Serializable
data class PersonalInfo (val id: String, val name: String, var email: String, val hash: String)