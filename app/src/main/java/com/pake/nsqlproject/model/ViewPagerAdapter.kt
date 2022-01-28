package com.pake.nsqlproject.model

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pake.nsqlproject.data.PersonalList
import com.pake.nsqlproject.ui.booklist.BookListFragment

class ViewPagerAdapter(fragment: Fragment, private val personalLists: MutableList<PersonalList>): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = personalLists.size

    override fun createFragment(position: Int): Fragment {
        return BookListFragment(personalLists[position])
    }


}

