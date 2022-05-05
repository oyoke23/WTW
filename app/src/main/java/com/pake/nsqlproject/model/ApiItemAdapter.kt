package com.pake.nsqlproject.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pake.nsqlproject.R
import com.pake.nsqlproject.data.ApiItem
import com.pake.nsqlproject.databinding.JikanItemBinding
import com.pake.nsqlproject.ui.searchbook.SearchBookFragment
import com.squareup.picasso.Picasso

class ApiItemAdapter(private val itemList: List<ApiItem>, private val clickListener: SearchBookFragment): RecyclerView.Adapter<ApiItemAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemHolder(layoutInflater.inflate(R.layout.jikan_item, parent,false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.render(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    inner class ItemHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {
        private val binding = JikanItemBinding.bind(view)

        fun render(item: ApiItem) {
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