package com.pake.nsqlproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pake.nsqlproject.data.Book
import com.pake.nsqlproject.databinding.ItemBookBinding

class BookAdapter(private val bookList: List<Book>): RecyclerView.Adapter<BookAdapter.BookHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BookHolder(layoutInflater.inflate(R.layout.item_book, parent,false))
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.render(bookList[position])
    }

    override fun getItemCount(): Int = bookList.size

    class BookHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemBookBinding.bind(view)

        fun render(book: Book) {
            binding.tvTitle.text = book.name
            binding.tvStatus.text = book.status
            binding.tvReadCh.text = book.readCh
            binding.tvTotalCh.text = book.totalCh
            binding.tvScore.text = book.score
        }
    }
}