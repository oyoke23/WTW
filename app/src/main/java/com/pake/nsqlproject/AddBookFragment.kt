package com.pake.nsqlproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.pake.nsqlproject.databinding.FragmentAddBookBinding
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddBookFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddBookFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentAddBookBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddBookFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddBookFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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
        sharedViewModel.allData.value?.personalList?.get(binding.spLists.selectedItemPosition)?.books?.add(
            Book(
                binding.etTitle.text.toString(),
                parseStatus(binding.spStatus.selectedItemPosition),
                binding.etReadChapter.text.toString(),
                binding.etTotalChapters.text.toString(),
                binding.etScore.text.toString()
            )
        )

        sharedViewModel.allData.value?.personalList?.get(binding.spLists.selectedItemPosition)?.books?.forEach {
            Log.i("Book", it.name)
        }

        val jsonString = Json.encodeToString(sharedViewModel.allData.value)
        Log.i("JSON", jsonString)
    }
}