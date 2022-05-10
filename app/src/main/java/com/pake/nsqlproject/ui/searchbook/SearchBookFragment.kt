package com.pake.nsqlproject.ui.searchbook

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.pake.nsqlproject.R
import com.pake.nsqlproject.SharedViewModel
import com.pake.nsqlproject.data.ApiItem
import com.pake.nsqlproject.data.jikan.JikanAllData
import com.pake.nsqlproject.data.jikan.JikanWithoutMeta
import com.pake.nsqlproject.databinding.FragmentSearchBookBinding
import com.pake.nsqlproject.model.ApiItemAdapter
import com.pake.nsqlproject.ui.addbook.AddBookFragment
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class SearchBookFragment : Fragment(), ApiItemAdapter.OnItemClickListener {
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding : FragmentSearchBookBinding? = null
    private val binding get() = _binding!!

    private lateinit var jikanAllData: JikanAllData
    private lateinit var jikanWithoutMeta: JikanWithoutMeta

    private lateinit var apiItemAdapter: ApiItemAdapter
    private lateinit var apiItem: ApiItem
    private var itemsList: MutableList<ApiItem> = mutableListOf()
    private var tempItemsList: MutableList<ApiItem> = mutableListOf()

    private var searchMode : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Search Book"
        _binding = FragmentSearchBookBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        getData("")
        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_search, menu)
        val searchItem = menu.findItem(R.id.search_button)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(newText: String?): Boolean {
                if (newText != "") {
                    getData(newText!!)
                }
                if (newText == "" && searchMode) {
                    getData(newText)
                }
                return false;
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        searchView.setOnCloseListener{
            initRecycler()
            false
        }

        val expandListener = object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                searchMode = true
                tempItemsList.clear()
                tempItemsList.addAll(itemsList)
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                searchMode = false
                itemsList.clear()
                itemsList.addAll(tempItemsList)
                initRecycler()
                return true
            }

        }
        searchItem.setOnActionExpandListener(expandListener)
        super.onCreateOptionsMenu(menu, inflater)
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
                            apiItem = ApiItem(id!!, title!!, image!!, chapters, synopsis, members)
                            itemsList.add(apiItem)

                    }
                }else{
                    jikanWithoutMeta = Json.decodeFromString(response)
                    Log.i("JIKAN WITHOUT META:", jikanWithoutMeta.toString())
                    jikanWithoutMeta.data.forEach {
                        val id = it.mal_id
                        val title = it.title
                        val image = it.images?.jpg?.image_url
                        var chapters2 = it.chapters
                        var synopsis2 = it.synopsis
                        val members = it.members

                        if (chapters2 == null){
                            chapters2 = -1
                        }
                        if (synopsis2 == null){
                            synopsis2 = "??"
                        }
                        apiItem = ApiItem(id!!, title!!, image!!, chapters2, synopsis2,members)
                        itemsList.add(apiItem)
                    }
                }
                if (itemsList.isEmpty()){
                    Toast.makeText(context, "No results found", Toast.LENGTH_SHORT).show()
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

        if (itemsList.isNotEmpty()) {
            itemsList.sortByDescending { it.members }
            itemsList = itemsList.toSet().toList() as MutableList<ApiItem>
        }

        apiItemAdapter = ApiItemAdapter(itemsList,this)
        recyclerView.adapter = apiItemAdapter
    }

    override fun onItemClick(position: Int) {
        val item = itemsList[position]
        sharedViewModel.saveJikanItem(item)
        AddBookFragment().show(childFragmentManager, "AddBookFragment")
    }

    override fun onItemLongClick(p0: View?, position: Int) {

    }
}