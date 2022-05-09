package com.pake.nsqlproject.ui.editlist

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.pake.nsqlproject.R
import com.pake.nsqlproject.SharedViewModel
import com.pake.nsqlproject.data.PersonalList
import com.pake.nsqlproject.databinding.FragmentEditListBinding
import com.pake.nsqlproject.model.ManageData

class EditListFragment(private var personalList: PersonalList) : DialogFragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentEditListBinding? = null
    private val binding get() = _binding!!
    private var editedList: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditListBinding.inflate(inflater, container, false)
        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        mainEditList()
        binding.btnAddList.setOnClickListener {
            if (binding.etListName.text.toString().isNotEmpty()) {
                handleEditList()
                dismiss()
            }
            else {
                Toast.makeText(context, "Please enter a list name", Toast.LENGTH_SHORT).show()
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
        if (editedList){
            NavHostFragment.findNavController(this).navigate(R.id.homeFragment)
        }
    }

    private fun mainEditList() {
        binding.tvEditListTitle.text = personalList.name
    }

    private fun handleEditList() {
        val manageData = ManageData(requireContext())
        var tempData = sharedViewModel.allData.value

        tempData?.personalList?.forEach {
            if(it.name == personalList.name){
                it.name = binding.etListName.text.toString()
            }
        }
        manageData.setData(tempData)
        editedList = true
    }

}