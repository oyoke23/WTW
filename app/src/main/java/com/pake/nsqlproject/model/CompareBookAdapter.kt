package com.pake.nsqlproject.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pake.nsqlproject.R
import com.pake.nsqlproject.data.Book
import com.pake.nsqlproject.databinding.ItemBookBinding
import com.pake.nsqlproject.ui.comparebooklist.CompareBookListFragment

class CompareBookAdapter(private val bookList: List<Book>, private val clickListener: CompareBookListFragment): RecyclerView.Adapter<CompareBookAdapter.BookHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BookHolder(layoutInflater.inflate(R.layout.item_book, parent,false))
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.render(bookList[position])
    }

    override fun getItemCount(): Int = bookList.size

    inner class BookHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {
        private val binding = ItemBookBinding.bind(view)

        fun render(book: Book) {
            binding.tvTitle.text = book.name
            binding.tvStatus.text = book.status
            binding.tvReadCh.text = book.readCh
            binding.tvTotalCh.text = book.totalCh
            binding.tvScore.text = book.score
        }

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                clickListener.onItemClick(position)
            }
        }

        override fun onLongClick(p0: View?): Boolean {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                clickListener.onItemLongClick(p0,position)
            }
            return true
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onItemLongClick(view: View?, position: Int)
    }
}