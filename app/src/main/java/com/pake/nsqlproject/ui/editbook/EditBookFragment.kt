package com.pake.nsqlproject.ui.editbook

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import java.math.BigDecimal
import java.math.RoundingMode

class EditBookFragment(private var book: Book, private var listPosition: Int) : DialogFragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding : FragmentEditBookBinding? = null
    private val binding get() = _binding!!
    private var editedBook: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditBookBinding.inflate(inflater, container, false)
        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        mainEditBook()
        binding.btnEditBook.setOnClickListener {
            if(binding.etReadChapter.text.toString().isNotEmpty() &&
                !binding.etReadChapter.text.contains(".") &&
                !binding.etReadChapter.text.contains(",") &&
                binding.etScore.text.toString().isNotEmpty() &&
                binding.etScore.text.toString().matches(Regex("^\\d+(\\.\\d+)?$"))&&
                binding.etScore.text.toString().toDouble() > 0 &&
                binding.etScore.text.toString().toDouble() <= 10) {
                if(binding.tvTotalCh.text.toString() == "???"){
                    handleEditBook()
                    dismiss()
                }else if(binding.etReadChapter.text.toString().toInt() <= binding.tvTotalCh.text.toString().toInt()){
                    handleEditBook()
                    dismiss()
                } else if (binding.etReadChapter.text.toString().toInt() > binding.tvTotalCh.text.toString().toInt()){
                    Toast.makeText(context, "You read more chapters than the book has?", Toast.LENGTH_SHORT).show()
                }

            }
            else if (binding.etReadChapter.text.toString().isEmpty()){
                Toast.makeText(context, "Please enter the chapter you read", Toast.LENGTH_SHORT).show()
            }
            else if (binding.etReadChapter.text.contains(".") || binding.etReadChapter.text.contains(",")){
                Toast.makeText(context, "Don't use points or commas", Toast.LENGTH_SHORT).show()
            }
            else if(!binding.etScore.text.toString().matches(Regex("^\\d+(\\.\\d+)?$"))){
                Toast.makeText(context, "Please enter a valid score", Toast.LENGTH_SHORT).show()
            }
            else if (binding.etScore.text.toString().isEmpty() || binding.etScore.text.toString().toDouble() < 1 || binding.etScore.text.toString().toDouble() > 10){
                Toast.makeText(context, "Please enter your score (1-10)", Toast.LENGTH_SHORT).show()
            }
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
        if (book.name.length > 30) {
            val newString = book.name.substring(0, 30) + "..."
            binding.tvItemTitle.text = newString
        } else {
            binding.tvItemTitle.text = book.name
        }
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

        Log.i("Read chapter", book.readCh.toString())
        binding.etReadChapter.setText(book.readCh.toString())
        if (book.totalCh == -1) {
                binding.tvTotalCh.text = "???"
        } else {
            binding.tvTotalCh.text = book.totalCh.toString()
        }
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
        val tempData = sharedViewModel.allData.value
        val score = BigDecimal(binding.etScore.text.toString()).setScale(2, RoundingMode.HALF_UP).toDouble()
        val scoreString = score.toString()
        if (tempData != null) {
            tempData.personalList[listPosition].books.indexOf(book).let {
                tempData.personalList[listPosition].books[it].readCh = binding.etReadChapter.text.toString().toInt()
                tempData.personalList[listPosition].books[it].score = scoreString
                tempData.personalList[listPosition].books[it].status = parseStatusOutput(binding.spStatus.selectedItemPosition)
                manageData.setData(tempData)
                Toast.makeText(requireContext(), "Book edited!", Toast.LENGTH_SHORT).show()
                editedBook = true
            }
        }
    }

}