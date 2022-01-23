package com.pake.nsqlproject.ui.booklist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.pake.nsqlproject.data.PersonalList
import com.pake.nsqlproject.databinding.FragmentBookListBinding
import com.pake.nsqlproject.model.BookAdapter


class BookListFragment(var personalList: PersonalList) : Fragment() {

    private var _binding : FragmentBookListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookListBinding.inflate(inflater, container, false)
        initRecycler(personalList)
        return binding.root
    }

    private fun initRecycler(personalList: PersonalList) {
        Log.i("Init recycler", "initRecycler")
        Log.i("personalList", personalList.toString())
        binding.rvBookList.layoutManager = LinearLayoutManager(context)
        val adapter = BookAdapter(personalList.books)

        binding.rvBookList.adapter = adapter
    }
}