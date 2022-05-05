package com.pake.nsqlproject.ui.searchbook

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.pake.nsqlproject.R
import com.pake.nsqlproject.data.AllData
import com.pake.nsqlproject.data.Book
import com.pake.nsqlproject.data.JikanItem
import com.pake.nsqlproject.data.PersonalList
import com.pake.nsqlproject.data.jikan.JikanAllData
import com.pake.nsqlproject.data.jikan.JikanWithoutMeta
import com.pake.nsqlproject.databinding.FragmentBookListBinding
import com.pake.nsqlproject.databinding.FragmentSearchBookBinding
import com.pake.nsqlproject.model.BookAdapter
import com.pake.nsqlproject.model.JikanAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONArray

class SearchBookFragment : Fragment() {

    private var _binding : FragmentSearchBookBinding? = null
    private val binding get() = _binding!!

    private lateinit var jikanAllData: JikanAllData
    private lateinit var jikanWithoutMeta: JikanWithoutMeta

    private lateinit var jikanAdapter: JikanAdapter
    private lateinit var jikanItem: JikanItem
    private var itemsList: MutableList<JikanItem> = mutableListOf()

    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBookBinding.inflate(inflater, container, false)
        searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    getData(query)
                }
                return false;
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true;
            }
        })
        return binding.root
    }

    private fun getData(text: String){
        itemsList.clear()
        val queue = Volley.newRequestQueue(this.context)
        val url = "https://api.jikan.moe/v4/manga?q=$text"
        val jsonRequest = StringRequest(Request.Method.GET, url,
            { response ->
                if(response.contains("\"links\":")){
                    jikanAllData = Json.decodeFromString(response)
                    Log.i("JIKAN ALLDATA:", jikanAllData.toString())
                    jikanAllData.data.forEach {
                        val id = it.mal_id
                        val title = it.title
                        val image = it.images?.jpg?.image_url
                        val chapters = it.chapters
                        val synopsis = it.synopsis
                        val members = it.members
                        if (id != null && title != null && image != null){
                            jikanItem = JikanItem(id, title, image, chapters, synopsis,members)
                            itemsList.add(jikanItem)
                        }
                        Log.i("JIKAN ITEM:", itemsList.toString())
                    }
                }else{
                    jikanWithoutMeta = Json.decodeFromString(response)
                    jikanWithoutMeta.data.forEach {
                        val id = it.mal_id
                        val title = it.title
                        val image = it.images?.jpg?.image_url
                        val chapters = it.chapters
                        val synopsis = it.synopsis
                        val members = it.members
                        if (id != null && title != null && image != null){
                            jikanItem = JikanItem(id, title, image, chapters, synopsis,members)
                            itemsList.add(jikanItem)

                        }
                        Log.i("JIKAN WITHOUT META ITEM:", itemsList.toString())

                    }
                }

                initRecycler()
            },
            { error ->
                error.printStackTrace()
            })
        queue.add(jsonRequest)
    }

    private fun initRecycler() {
        binding.rvJikanItems.layoutManager = GridLayoutManager(this.context, 2)
        itemsList.sortByDescending { it.members }
        val sortedItemsList = itemsList.toSet().toList()
        jikanAdapter = JikanAdapter(sortedItemsList,this)
        binding.rvJikanItems.adapter = jikanAdapter
    }

    fun onItemClick(position: Int) {
        val item = itemsList[position]
        Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show()
    }

    fun onItemLongClick(p0: View?, position: Int) {

    }
}