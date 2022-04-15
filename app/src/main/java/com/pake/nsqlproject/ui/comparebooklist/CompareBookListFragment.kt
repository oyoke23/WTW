package com.pake.nsqlproject.ui.comparebooklist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pake.nsqlproject.R
import com.pake.nsqlproject.databinding.FragmentCompareBookListBinding
import com.pake.nsqlproject.databinding.FragmentCompareListsBinding
import com.pake.nsqlproject.model.CompareBookAdapter


class CompareBookListFragment : Fragment(), CompareBookAdapter.OnItemClickListener {

    private var _binding: FragmentCompareBookListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCompareBookListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onItemLongClick(view: View?, position: Int) {
        TODO("Not yet implemented")
    }


}