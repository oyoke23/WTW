package com.pake.nsqlproject.ui.booklist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.pake.nsqlproject.data.Book
import com.pake.nsqlproject.data.PersonalList
import com.pake.nsqlproject.databinding.FragmentBookListBinding
import com.pake.nsqlproject.model.BookAdapter


class BookListFragment(var personalList: PersonalList) : Fragment(), BookAdapter.OnItemClickListener {

    private var _binding : FragmentBookListBinding? = null
    private val binding get() = _binding!!
    private var books: MutableList<Book> = mutableListOf()
    private lateinit var bookAdapter: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookListBinding.inflate(inflater, container, false)
        books = personalList.books
        initRecycler(personalList)
        return binding.root
    }

    private fun initRecycler(personalList: PersonalList) {
        Log.i("Init recycler", "initRecycler")
        Log.i("personalList", personalList.toString())
        binding.rvBookList.layoutManager = LinearLayoutManager(context)
        bookAdapter = BookAdapter(books,this)

        binding.rvBookList.adapter = bookAdapter
    }

    override fun onItemClick(position: Int) {
        // get book from list
        val book = personalList.books[position]
        // Implement long press to delete
        Toast.makeText(context, "Click to ${book.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(position: Int) {
        // get book from list
        val book = personalList.books[position]
        // Implement long press to delete
        Toast.makeText(context, "Long press to ${book.name}", Toast.LENGTH_SHORT).show()

        // Remove book from list
        personalList.books.remove(book)
        // Update adapter
        bookAdapter.notifyItemRemoved(position)
    }
}