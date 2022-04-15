package com.pake.nsqlproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pake.nsqlproject.data.AllData
import com.pake.nsqlproject.data.CompareBook

class SharedViewModel: ViewModel() {
    private var _allData = MutableLiveData<AllData>()
    var allData: MutableLiveData<AllData> = _allData

    private var _compareBookList = MutableLiveData<List<CompareBook>>()
    var compareBookList: MutableLiveData<List<CompareBook>> = _compareBookList

    fun saveAllData(data: AllData) {
        _allData.value = data
    }

    fun saveCompareBookList(list: List<CompareBook>) {
        _compareBookList.value = list
    }
}