package com.pake.nsqlproject.ui.comparelists

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.pake.nsqlproject.R
import com.pake.nsqlproject.SharedViewModel
import com.pake.nsqlproject.data.CompareBook
import com.pake.nsqlproject.databinding.FragmentCompareListsBinding

class CompareListsDialogFragment : DialogFragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentCompareListsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCompareListsBinding.inflate(inflater, container, false)
        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        main()
        binding.btnCompareList.setOnClickListener {
            compareData()
            findNavController().navigate(R.id.compareBookListFragment)
            dismiss()
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
        val diffList = ArrayList<CompareBook>()

        data1List?.forEach { book ->
            if (!diffList.any { it.book_1?.id == book.id }) {
                diffList.add(CompareBook(book, null))
            }
        }

        data2List?.forEach { book ->
            if (!diffList.any { it.book_1?.id == book.id || it.book_2?.id == book.id }) {
                diffList.add(CompareBook(null, book))
            } else {
                diffList.find { it.book_1?.id == book.id || it.book_2?.id == book.id }?.let {
                    it.book_2 = book
                }
            }
        }

        Log.i("DiffList", diffList.toString())
        sharedViewModel.saveCompareBookList(list1, list2, diffList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}