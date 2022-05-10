package com.pake.nsqlproject.model

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.pake.nsqlproject.data.AllData
import com.pake.nsqlproject.data.Friend
import com.pake.nsqlproject.data.PersonalInfo
import com.pake.nsqlproject.data.PersonalList
import com.pake.nsqlproject.ui.createaccount.CreateAccountActivity
import kotlinx.serialization.*
import kotlinx.serialization.json.*

class ManageData (private val context: Context) {
    fun getData(): AllData {
        val sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val data = sharedPreferences.getString("data", "")

        if (data === null || data.isEmpty()) {
            val personalList: MutableList<PersonalList> = mutableListOf()
            val friendList: MutableList<Friend> = mutableListOf()
            val intent = Intent(context, CreateAccountActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(context, intent, null)
            return AllData(PersonalInfo("-1","",""),personalList,friendList)
        } else {
            Log.i("ManageData", "Data found")

        }

        return data.let { Json.decodeFromString<AllData>(it) }
    }

    fun setData(data: AllData?) {
        val json = Json.encodeToString(data)
        val sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("data", json).apply()
    }
}

