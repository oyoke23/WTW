package com.pake.nsqlproject.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pake.nsqlproject.R
import com.pake.nsqlproject.data.JikanItem
import com.pake.nsqlproject.databinding.JikanItemBinding
import com.pake.nsqlproject.ui.searchbook.SearchBookFragment
import com.squareup.picasso.Picasso

class JikanAdapter(private val itemList: List<JikanItem>, private val clickListener: SearchBookFragment): RecyclerView.Adapter<JikanAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemHolder(layoutInflater.inflate(R.layout.jikan_item, parent,false))
    }

    override fun onBindViewHolder(holder: JikanAdapter.ItemHolder, position: Int) {
        holder.render(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    inner class ItemHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {
        private val binding = JikanItemBinding.bind(view)

        fun render(item: JikanItem) {
            binding.tvTitle.text = item.title
            Picasso.get().load(item.image).into(binding.imgItem)
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