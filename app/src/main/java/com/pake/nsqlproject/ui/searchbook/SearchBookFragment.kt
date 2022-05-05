package com.pake.nsqlproject.ui.searchbook

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.pake.nsqlproject.SharedViewModel
import com.pake.nsqlproject.data.JikanItem
import com.pake.nsqlproject.data.jikan.JikanAllData
import com.pake.nsqlproject.data.jikan.JikanWithoutMeta
import com.pake.nsqlproject.databinding.FragmentSearchBookBinding
import com.pake.nsqlproject.model.JikanAdapter
import com.pake.nsqlproject.ui.addbook.AddBookFragment
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class SearchBookFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()

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
                        var chapters = it.chapters
                        var synopsis = it.synopsis
                        val members = it.members

                        if (chapters == null){
                            chapters = -1
                        }
                        if (synopsis == null){
                            synopsis = "??"
                        }
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
                        var chapters = it.chapters
                        var synopsis = it.synopsis
                        val members = it.members

                        if (chapters == null){
                            chapters = -1
                        }
                        if (synopsis == null){
                            synopsis = "??"
                        }
                        jikanItem = JikanItem(id!!, title!!, image!!, chapters!!, synopsis!!,members)
                        itemsList.add(jikanItem)

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
        val recyclerView = binding.rvJikanItems
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        itemsList.sortByDescending { it.members }
        val sortedItemsList = itemsList.toSet().toList()

        jikanAdapter = JikanAdapter(sortedItemsList,this)
        recyclerView.adapter = jikanAdapter
    }

    fun onItemClick(position: Int) {
        val item = itemsList[position]
        sharedViewModel.saveJikanItem(item)
        AddBookFragment().show(childFragmentManager, "AddBookFragment")
    }

    fun onItemLongClick(p0: View?, position: Int) {

    }
}