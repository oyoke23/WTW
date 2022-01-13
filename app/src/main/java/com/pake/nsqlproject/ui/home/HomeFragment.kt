package com.pake.nsqlproject.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pake.nsqlproject.data.AllData
import com.pake.nsqlproject.SharedViewModel
import com.pake.nsqlproject.BookAdapter
import com.pake.nsqlproject.databinding.FragmentHomeBinding
import java.io.IOException
import kotlinx.serialization.*
import kotlinx.serialization.json.*

class HomeFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeMain()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun homeMain() {
        val jsonFileString = activity?.let { getJsonDataFromAsset(it.applicationContext, "data.json") }
        val allData = jsonFileString?.let { Json.decodeFromString<AllData>(it) }
        if (allData != null) {
            sharedViewModel.saveAllData(allData)
            initRecycler(allData)
        }
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

    private fun initRecycler(allData: AllData){
        binding.tvListTitle.text = allData.personalList[0].name
        binding.rvBookList.layoutManager = LinearLayoutManager(context)
        val adapter = BookAdapter(allData.personalList[0].books)

        binding.rvBookList.adapter = adapter
    }
}