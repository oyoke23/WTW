package com.pake.nsqlproject.ui.comparebooklist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pake.nsqlproject.SharedViewModel
import com.pake.nsqlproject.data.CompareBook
import com.pake.nsqlproject.databinding.FragmentCompareBookListBinding
import com.pake.nsqlproject.model.CompareBookAdapter


class CompareBookListFragment : Fragment(), CompareBookAdapter.OnItemClickListener {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentCompareBookListBinding? = null
    private val binding get() = _binding!!
    private lateinit var compareBookAdapter: CompareBookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCompareBookListBinding.inflate(inflater, container, false)
        initRecycler(sharedViewModel.compareBookList.value!!)
        return binding.root
    }

    private fun initRecycler(compareBookList: List<CompareBook>) {
        Log.i("Init recycler", "initRecycler")
        binding.rvCompareBookList.layoutManager = LinearLayoutManager(context)
        compareBookAdapter = CompareBookAdapter(compareBookList,this)
        binding.tvList1.text = sharedViewModel.listName1.value
        binding.tvList2.text = sharedViewModel.listName2.value

        binding.rvCompareBookList.adapter = compareBookAdapter
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(view: View?, position: Int) {
        Toast.makeText(context, "Long Clicked", Toast.LENGTH_SHORT).show()
    }


}