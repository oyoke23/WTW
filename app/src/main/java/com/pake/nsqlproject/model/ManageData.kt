package com.pake.nsqlproject.model

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.pake.nsqlproject.MainActivity
import com.pake.nsqlproject.data.AllData
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.IOException

class ManageData (private val context: Context) {
    fun getData(): AllData? {
        val sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        var data = sharedPreferences.getString("data", "")

        if (data === null || data.isEmpty()) {
            Log.i("ManageData", "No data found")
            val jsonFileString = getJsonDataFromAsset(context, "data.json")
            sharedPreferences.edit().putString("data", jsonFileString).apply()
            data = jsonFileString
        } else {
            Log.i("ManageData", "Data found")
        }

        return data?.let { Json.decodeFromString<AllData>(it) }
    }

    fun setData(data: AllData?) {
        val json = Json.encodeToString(data)
        val sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("data", json).apply()
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

}

