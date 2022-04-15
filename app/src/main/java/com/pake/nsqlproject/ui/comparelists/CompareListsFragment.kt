package com.pake.nsqlproject.ui.comparelists

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.pake.nsqlproject.R
import com.pake.nsqlproject.SharedViewModel
import com.pake.nsqlproject.data.Book
import com.pake.nsqlproject.databinding.FragmentCompareListsBinding

class CompareListsFragment : DialogFragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentCompareListsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCompareListsBinding.inflate(inflater, container, false)
        main()
        binding.btnCompareList.setOnClickListener {
            compareData()
        }
        return binding.root
    }

    private fun main() {
        activity?.let { it ->
            val personalLists = sharedViewModel.allData.value?.personalList
            val tempList = ArrayList<String>()

            personalLists?.forEach {
                Log.i("CompareList", it.name)
                tempList.add(it.name)
            }

            ArrayAdapter(it.applicationContext, R.layout.spinner_item, tempList).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                binding.spLists1.adapter = adapter
                binding.spLists2.adapter = adapter
            }
        }
    }

    private fun compareData() {
        val list1 = binding.spLists1.selectedItem.toString()
        val list2 = binding.spLists2.selectedItem.toString()
        Log.i("List1", list1)
        Log.i("List2", list2)
        val data1 = sharedViewModel.allData.value?.personalList?.find { it.name == list1 }
        val data2 = sharedViewModel.allData.value?.personalList?.find { it.name == list2 }
        val data1List = data1?.books
        val data2List = data2?.books
        val diffList = ArrayList<Book>()

        /*data1List?.forEach { book ->
            if (!diffList.contains(book)) {
                diffList.add(book)
            }
        }

        data2List?.forEach { book ->
            if (!diffList.contains(book)) {
                diffList.add(book)
            } else {
                var tempBook = diffList.find { book.name == it.name }
            }
        }*/

        Log.i("DiffList", diffList.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}