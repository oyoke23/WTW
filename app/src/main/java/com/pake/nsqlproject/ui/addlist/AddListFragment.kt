package com.pake.nsqlproject.ui.addlist

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.pake.nsqlproject.R
import com.pake.nsqlproject.SharedViewModel
import com.pake.nsqlproject.data.Book
import com.pake.nsqlproject.data.PersonalList
import com.pake.nsqlproject.databinding.FragmentAddListBinding
import com.pake.nsqlproject.model.ManageData

class AddListFragment : DialogFragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentAddListBinding? = null
    private val binding get() = _binding!!
    private var addedList = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddListBinding.inflate(inflater, container, false)
        binding.btnAddList.setOnClickListener {
            handleAddList()
            dismiss()
        }
        return binding.root
    }

    private fun handleAddList() {
        val manageData = ManageData(requireContext())
        var tempData = sharedViewModel.allData.value

        if (sharedViewModel.allData.value?.personalList?.size == 0) {
            tempData?.personalList?.add(PersonalList("1", "-1", binding.etListName.text.toString(), mutableListOf<Book>()))
        } else {
            tempData?.personalList?.add(PersonalList(sharedViewModel.allData.value?.personalList?.last()?.id + 1, "-1", binding.etListName.text.toString(), mutableListOf<Book>()))
        }

        manageData.setData(tempData!!)
        addedList = true

        Toast.makeText(requireContext(), "List added", Toast.LENGTH_SHORT).show()
        // TODO: Clear data from fields and go back to home fragment
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (addedList){
            findNavController(this).navigate(R.id.homeFragment)
        }
    }
}