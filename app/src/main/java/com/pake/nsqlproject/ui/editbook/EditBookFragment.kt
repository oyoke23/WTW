package com.pake.nsqlproject.ui.editbook

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.pake.nsqlproject.R
import com.pake.nsqlproject.SharedViewModel
import com.pake.nsqlproject.data.Book
import com.pake.nsqlproject.databinding.FragmentEditBookBinding
import com.pake.nsqlproject.model.ManageData

class EditBookFragment(book: Book, listPosition: Int) : DialogFragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding : FragmentEditBookBinding? = null
    private val binding get() = _binding!!
    private var editedBook: Boolean = false
    private var book: Book = book
    private var listPosition: Int = listPosition

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditBookBinding.inflate(inflater, container, false)
        mainEditBook()
        binding.btnEditBook.setOnClickListener {
            handleEditBook()
            dismiss()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (editedBook){
            NavHostFragment.findNavController(this).navigate(R.id.homeFragment)
        }
    }

    private fun mainEditBook() {
        binding.tvBookTitle.text = book.name
        activity?.let {
            ArrayAdapter.createFromResource(
                it.applicationContext,
                R.array.status_types,
                R.layout.spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spStatus.adapter = adapter
                binding.spStatus.setSelection(parseStatusInput(book.status))
                Log.i("Status", book.status)
                Log.i("Status number", parseStatusInput(book.status).toString())
            }
        }

        binding.etReadChapter.setText(book.readCh)
        binding.etTotalChapters.setText(book.totalCh)
        binding.etScore.setText(book.score)
    }
    private fun parseStatusOutput(status: Int): String {
        return when (status) {
            0 -> "Plan to read"
            1 -> "Reading"
            2 -> "Completed"
            3 -> "On Hold"
            4 -> "Dropped"
            else -> "Unknown"
        }
    }
    private fun parseStatusInput(status: String): Int {
        return when (status) {
            "Plan to read" -> 0
            "Reading" -> 1
            "Completed" -> 2
            "On Hold" -> 3
            "Dropped" -> 4
            else -> 0
        }
    }

    private fun handleEditBook() {
        val manageData = ManageData(requireContext())
        var tempData = sharedViewModel.allData.value
        if (tempData != null) {
            tempData.personalList[listPosition].books.indexOf(book).let {
                tempData.personalList[listPosition].books[it].readCh = binding.etReadChapter.text.toString()
                tempData.personalList[listPosition].books[it].totalCh = binding.etTotalChapters.text.toString()
                tempData.personalList[listPosition].books[it].score = binding.etScore.text.toString()
                tempData.personalList[listPosition].books[it].status = parseStatusOutput(binding.spStatus.selectedItemPosition)
                manageData.setData(tempData)
                Toast.makeText(requireContext(), "Book edited!", Toast.LENGTH_SHORT).show()
                editedBook = true
            }
        }
    }

}