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
    private var _listName1 = MutableLiveData<String>()
    var listName1: MutableLiveData<String> = _listName1
    private var _listName2 = MutableLiveData<String>()
    var listName2: MutableLiveData<String> = _listName2

    fun saveAllData(data: AllData) {
        _allData.value = data
    }

    fun saveCompareBookList(listName1: String, listName2: String, compareList: List<CompareBook>) {
        _compareBookList.value = compareList
        _listName1.value = listName1
        _listName2.value = listName2
    }
}