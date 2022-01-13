package com.pake.nsqlproject.data
import kotlinx.serialization.Serializable

@Serializable
data class AllData (val personalInfo: PersonalInfo,
                    val personalList: MutableList<PersonalList>,
                    val friendList: MutableList<Friend>)