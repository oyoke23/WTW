package com.pake.nsqlproject.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pake.nsqlproject.R
import com.pake.nsqlproject.data.CompareBook
import com.pake.nsqlproject.databinding.ItemCompareBookBinding
import com.pake.nsqlproject.ui.comparebooklist.CompareBookListFragment
import com.squareup.picasso.Picasso

class CompareBookAdapter(private val bookList: List<CompareBook>, private val clickListener: CompareBookListFragment): RecyclerView.Adapter<CompareBookAdapter.BookHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BookHolder(layoutInflater.inflate(R.layout.item_compare_book, parent,false))
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.render(bookList[position])
    }

    override fun getItemCount(): Int = bookList.size

    inner class BookHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {
        private val binding = ItemCompareBookBinding.bind(view)

        fun render(compareBook: CompareBook) {

            if (compareBook.book_1 != null) {
                binding.tvTitle.text = compareBook.book_1?.name
                Picasso.get().load(compareBook.book_1?.image).into(binding.ivItem)
                binding.tvUserScore1.text = compareBook.book_1?.score.toString()
                binding.tvUserStatus1.text = compareBook.book_1?.status
                binding.tvUserReadCh1.text = compareBook.book_1?.readCh.toString()
                if (compareBook.book_1?.totalCh == -1){
                    binding.tvUserTotalCh1.text = "???"
                }
                else{
                    binding.tvUserTotalCh1.text = compareBook.book_1?.totalCh.toString()
                }
            } else {
                binding.tvUserScore1.text = "-"
                binding.tvUserStatus1.text = "-"
                binding.tvUserReadCh1.text = "-"
                binding.tvUserTotalCh1.text = "-"
            }

            if (compareBook.book_2 != null) {
                binding.tvTitle.text = compareBook.book_2?.name
                Picasso.get().load(compareBook.book_2?.image).into(binding.ivItem)
                binding.tvUserScore2.text = compareBook.book_2?.score.toString()
                binding.tvUserStatus2.text = compareBook.book_2?.status
                binding.tvUserReadCh2.text = compareBook.book_2?.readCh.toString()
                if (compareBook.book_2?.totalCh == -1){
                    binding.tvUserTotalCh2.text = "???"
                }
                else{
                    binding.tvUserTotalCh2.text = compareBook.book_2?.totalCh.toString()
                }
            } else {
                binding.tvUserScore2.text = "-"
                binding.tvUserStatus2.text = "-"
                binding.tvUserReadCh2.text = "-"
                binding.tvUserTotalCh2.text = "-"
            }

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