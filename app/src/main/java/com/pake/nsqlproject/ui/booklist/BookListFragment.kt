package com.pake.nsqlproject.ui.booklist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pake.nsqlproject.R
import com.pake.nsqlproject.SharedViewModel
import com.pake.nsqlproject.data.Book
import com.pake.nsqlproject.data.PersonalList
import com.pake.nsqlproject.databinding.FragmentBookListBinding
import com.pake.nsqlproject.model.BookAdapter
import com.pake.nsqlproject.model.ManageData
import com.pake.nsqlproject.ui.editbook.EditBookFragment


class BookListFragment(var personalList: PersonalList) : Fragment(), BookAdapter.OnItemClickListener {

    private val sharedViewModel: SharedViewModel by activityViewModels()

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
        if (books.isEmpty()) {
            binding.tvEmptyList.visibility = View.VISIBLE
        }
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
        // do nothing for now
    }

    override fun onItemLongClick(view: View?, position: Int) {
        // get book from list
        val (book, listPosition) = getTrueBookFromList(personalList.books[position])

        val popup = PopupMenu(context, view)
        popup.inflate(R.menu.popup_menu_books)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.edit_book -> {
                    var dialog = EditBookFragment(book, listPosition)
                    dialog.show(parentFragmentManager,"editBook")
                    true
                }
                R.id.remove_book -> {
                    val manageData = ManageData(requireContext())
                    val allData = manageData.getData()
                    // Remove book from list
                    if (allData != null) {
                        allData.personalList[listPosition].books.remove(book)
                    }
                    personalList.books.remove(book)
                    // Update adapter
                    bookAdapter.notifyItemRemoved(position)
                    manageData.setData(allData)
                    Toast.makeText(context, "Delete ${book.name}", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        try {
            val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldMPopup.isAccessible = true
            val mPopup = fieldMPopup.get(popup)
            mPopup.javaClass
                .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(mPopup, true)
        } catch (e: Exception) {
            Log.e("Main", "Error showing menu icons.", e)
        } finally {
            popup.show()
        }
    }

    private fun getTrueBookFromList(book: Book): Pair<Book,Int> {
        var output: Book? = null
        var listPosition: Int = 0

        val allData = sharedViewModel.allData.value
        if (allData != null) {
            for ((index, list) in allData.personalList.withIndex()) {
                if (list.id == personalList.id){
                    listPosition = index
                }
            }

            val booksIterable = allData.personalList[listPosition].books.iterator()
            while (booksIterable.hasNext()) {
                var iterableBook = booksIterable.next()
                if (iterableBook.id == book.id) {
                    output = book
                    break
                }
            }
        }
        return Pair(output!!, listPosition)
    }
}