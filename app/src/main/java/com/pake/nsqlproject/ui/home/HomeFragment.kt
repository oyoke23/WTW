package com.pake.nsqlproject.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.pake.nsqlproject.R
import com.pake.nsqlproject.SharedViewModel
import com.pake.nsqlproject.data.AllData
import com.pake.nsqlproject.data.PersonalList
import com.pake.nsqlproject.databinding.FragmentHomeBinding
import com.pake.nsqlproject.model.ManageData
import com.pake.nsqlproject.model.ViewPagerAdapter
import com.pake.nsqlproject.ui.editlist.EditListFragment

class HomeFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var personalLists: MutableList<PersonalList> = mutableListOf()
    private lateinit var allData: AllData
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var searchMode : Boolean = false
    private val manageData : ManageData by lazy {
        ManageData(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        Log.i("Home fragment", "Created")
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Home"
        setHasOptionsMenu(true)
        homeMain()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
            refreshLayout(personalLists, allData)
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
                allData = manageData.getData()!!
                personalLists = allData.personalList
                refreshLayout(personalLists, allData)
                return true
            }

        }
        searchItem.setOnActionExpandListener(expandListener)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun homeMain() {
        allData = manageData.getData()!!

        if (allData != null) {
            personalLists = allData.personalList
            sharedViewModel.saveAllData(allData)

            refreshLayout(personalLists, allData)

            // On long click on tab in tabLayout
            val tabs = binding.tabLayout.getChildAt(0) as ViewGroup
            for (i in 0 until tabs.childCount) {
                tabs.getChildAt(i).setOnLongClickListener { it ->
                    // get the position of the clicked tab
                    val position = tabs.indexOfChild(it)
                    val popup = PopupMenu(requireContext(), it)

                    popup.setOnMenuItemClickListener { itTab ->
                        when (itTab.itemId) {
                            R.id.change_name -> {
                                val personalList = personalLists.find { it.name == binding.tabLayout.getTabAt(position)!!.text.toString() }
                                val dialog = EditListFragment(personalList!!)
                                dialog.show(childFragmentManager, "editList")

                                true
                            }
                            R.id.remove_list -> {
                                // get the personal list from the name of tab
                                val personalList = personalLists.find { it.name == binding.tabLayout.getTabAt(position)!!.text.toString() }
                                personalLists.remove(personalList)
                                manageData.setData(allData)
                                //viewPagerAdapter.notifyItemRemoved(position)
                                Toast.makeText(
                                    requireContext(),
                                    "List ${binding.tabLayout.getTabAt(position)!!.text.toString()} deleted",
                                    Toast.LENGTH_SHORT
                                ).show()
                                refreshLayout(personalLists, allData)

                                false
                            }
                            else -> false
                        }
                    }
                    popup.inflate(R.menu.popup_menu_lists)

                    try {
                        val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                        fieldMPopup.isAccessible = true
                        val mPopup = fieldMPopup.get(popup)
                        mPopup.javaClass
                            .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                            .invoke(mPopup, true)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        popup.show()
                    }

                    true
                }
            }
        }



    }
    private fun filter(newText: String){
        val manageData = ManageData(requireContext())
        val allData = manageData.getData()
        val filteredLists = allData?.personalList
        var contador = 0

        if (filteredLists != null) {
            val filteredListsIterator = filteredLists.iterator()

            while(filteredListsIterator.hasNext()){

                val item = filteredListsIterator.next()
                item.books.clear()
                for(book in personalLists[contador].books){
                    if (book.name.lowercase().contains(newText.lowercase())){
                        item.books.add(book)
                    }
                }
                if (item.books.isEmpty()){
                    filteredListsIterator.remove()
                }
                contador += 1
            }
        }

        if (filteredLists != null) {
            refreshLayout(filteredLists, allData)
        }

    }
    private fun refreshLayout(personalList: MutableList<PersonalList>, allData: AllData?){
        val viewPager = binding.vpBookList
        val tabLayout = binding.tabLayout
        viewPagerAdapter = ViewPagerAdapter(this, personalList)
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (allData != null) {
                tab.text = allData.personalList[position].name
            }
        }.attach()
    }
}