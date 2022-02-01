package com.pake.nsqlproject.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pake.nsqlproject.SharedViewModel
import com.pake.nsqlproject.data.PersonalList
import com.pake.nsqlproject.databinding.FragmentHomeBinding
import com.pake.nsqlproject.model.ManageData
import com.pake.nsqlproject.model.ViewPagerAdapter

class HomeFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var personalLists: MutableList<PersonalList> = mutableListOf()
    private lateinit var viewPagerAdapter: ViewPagerAdapter

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
        val manageData = ManageData(requireContext())
        val allData = manageData.getData()

        if (allData != null) {
            val tabLayout = binding.tabLayout
            val viewPager = binding.vpBookList
            personalLists = allData.personalList
            viewPagerAdapter = ViewPagerAdapter(this, personalLists)

            sharedViewModel.saveAllData(allData)
            viewPager.adapter = viewPagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = allData.personalList[position].name
            }.attach()

            // On long click on tab in tablayout delete the tab
            val tabs = binding.tabLayout.getChildAt(0) as ViewGroup
            for (i in 0 until tabs.childCount) {
                tabs.getChildAt(i).setOnLongClickListener {
                    // get the position of the clicked tab
                    val position = tabs.indexOfChild(it)
                    personalLists.removeAt(position)
                    manageData.setData(allData)
                    Toast.makeText(requireContext(), "Tab deleted ${position+1}", Toast.LENGTH_SHORT).show()
                    viewPagerAdapter.notifyItemRemoved(position)
                    true
                }
            }

        }
    }
}