package com.pake.nsqlproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.pake.nsqlproject.databinding.ActivityMainBinding
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jsonFileString = getJsonDataFromAsset(applicationContext, "data.json")
        val allData = jsonFileString?.let { Json.decodeFromString<AllData>(it) }
        Log.i("All data: ", allData.toString())
        if (allData != null) {
            initRecycler(allData)
        }
    }

    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun initRecycler(allData: AllData){
        binding.rvBookList.layoutManager = LinearLayoutManager(this)
        val adapter = BookAdapter(allData.personalList[0].books)

        binding.rvBookList.adapter = adapter
    }
}