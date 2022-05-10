package com.pake.nsqlproject.ui.comparebooklist

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pake.nsqlproject.R
import com.pake.nsqlproject.SharedViewModel
import com.pake.nsqlproject.data.CompareBook
import com.pake.nsqlproject.databinding.FragmentCompareBookListBinding
import com.pake.nsqlproject.model.CompareBookAdapter
import com.pake.nsqlproject.model.ManageData
import java.util.*


class CompareBookListFragment : Fragment(), CompareBookAdapter.OnItemClickListener {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentCompareBookListBinding? = null
    private val binding get() = _binding!!
    private lateinit var compareBookAdapter: CompareBookAdapter
    private var compareBookList: List<CompareBook> = listOf()
    private var searchMode : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCompareBookListBinding.inflate(inflater, container, false)
        // change action bar title to compare book list
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Compare Book List"
        compareBookList = sharedViewModel.compareBookList.value!!
        setHasOptionsMenu(true)
        initRecycler()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_search, menu)
        val searchItem = menu.findItem(R.id.search_button)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false;
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != "") {
                    filter(newText!!)
                }
                if (newText == "" && searchMode) {
                    filter(newText)
                }
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
                filter("")
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                searchMode = false
                initRecycler()
                return true
            }

        }
        searchItem.setOnActionExpandListener(expandListener)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun filter(newText: String){
        val tempList: List<CompareBook> = compareBookList.filter {
            it.book_1?.name?.lowercase()?.contains(newText.lowercase()) == true ||
                    it.book_2?.name?.lowercase()?.contains(newText.lowercase()) == true
        }

        binding.rvCompareBookList.adapter = CompareBookAdapter(tempList, this)
    }

    private fun initRecycler() {
        Log.i("Init recycler", "initRecycler")
        binding.rvCompareBookList.layoutManager = LinearLayoutManager(context)
        compareBookAdapter = CompareBookAdapter(compareBookList,this)
        binding.tvList1.text = sharedViewModel.listName1.value
        binding.tvList2.text = sharedViewModel.listName2.value

        binding.rvCompareBookList.adapter = compareBookAdapter
    }

    override fun onItemClick(position: Int) {
        // do nothing

    }

    override fun onItemLongClick(view: View?, position: Int) {
        // do nothing
    }


}