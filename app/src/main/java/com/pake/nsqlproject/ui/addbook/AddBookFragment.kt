package com.pake.nsqlproject.ui.addbook

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.pake.nsqlproject.data.Book
import com.pake.nsqlproject.R
import com.pake.nsqlproject.SharedViewModel
import com.pake.nsqlproject.databinding.FragmentAddBookBinding
import com.pake.nsqlproject.model.ManageData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class AddBookFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentAddBookBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddBookBinding.inflate(inflater, container, false)
        mainAddBook()
        binding.btnAddBook.setOnClickListener {
            addBook()
        }
        return binding.root
    }

    private fun mainAddBook() {
        // Adapter for "Lists" spinner
        activity?.let { it ->
            val personalLists = sharedViewModel.allData.value?.personalList
            val tempList = ArrayList<String>()

            personalLists?.forEach {
                Log.i("PersonalList", it.name)
                tempList.add(it.name)
            }

            ArrayAdapter(it.applicationContext, R.layout.spinner_item, tempList).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                binding.spLists.adapter = adapter
            }
        }

        // Adapter for "Status" spinner
        activity?.let {
            ArrayAdapter.createFromResource(
                it.applicationContext,
                R.array.status_types,
                R.layout.spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spStatus.adapter = adapter
            }
        }
    }

    private fun parseStatus(status: Int): String {
        return when (status) {
            0 -> "Plan to Watch"
            1 -> "Watching"
            2 -> "Completed"
            3 -> "On Hold"
            5 -> "Dropped"
            else -> "Unknown"
        }
    }

    private fun addBook() {
        val manageData = ManageData(requireContext())
        sharedViewModel.allData.value?.personalList?.get(binding.spLists.selectedItemPosition)?.books?.add(
            Book(
                binding.etTitle.text.toString(),
                parseStatus(binding.spStatus.selectedItemPosition),
                binding.etReadChapter.text.toString(),
                binding.etTotalChapters.text.toString(),
                binding.etScore.text.toString()
            )
        )

        manageData.setData(sharedViewModel.allData.value)

        Toast.makeText(requireContext(), "Book added", Toast.LENGTH_SHORT).show()
        // TODO: Clear data from fields and go back to home fragment
    }
}