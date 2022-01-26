package com.pake.nsqlproject.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.pake.nsqlproject.SharedViewModel
import com.pake.nsqlproject.databinding.FragmentHomeBinding
import com.pake.nsqlproject.model.ManageData
import com.pake.nsqlproject.model.ViewPagerAdapter

class HomeFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
            val adapter = ViewPagerAdapter(this, allData.personalList)

            sharedViewModel.saveAllData(allData)
            viewPager.adapter = adapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = allData.personalList[position].name
            }.attach()
        }
    }
}