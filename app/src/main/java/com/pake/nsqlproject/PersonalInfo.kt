package com.pake.nsqlproject
import kotlinx.serialization.Serializable

@Serializable
data class PersonalInfo (val id: String, val name: String, val email: String, val hash: String){
}