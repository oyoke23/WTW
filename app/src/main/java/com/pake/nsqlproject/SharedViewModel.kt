package com.pake.nsqlproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pake.nsqlproject.data.AllData
import com.pake.nsqlproject.data.CompareBook
import com.pake.nsqlproject.data.ApiItem

class SharedViewModel: ViewModel() {
    private var _allData = MutableLiveData<AllData>()
    val allData: MutableLiveData<AllData> = _allData

    private var _compareBookList = MutableLiveData<List<CompareBook>>()
    val compareBookList: MutableLiveData<List<CompareBook>> = _compareBookList
    private var _listName1 = MutableLiveData<String>()
    val listName1: MutableLiveData<String> = _listName1
    private var _listName2 = MutableLiveData<String>()
    val listName2: MutableLiveData<String> = _listName2

    private var _apiItem = MutableLiveData<ApiItem>()
    val apiItem: MutableLiveData<ApiItem> = _apiItem

    fun saveAllData(data: AllData) {
        _allData.value = data
    }

    fun saveCompareBookList(listName1: String, listName2: String, compareList: List<CompareBook>) {
        _compareBookList.value = compareList
        _listName1.value = listName1
        _listName2.value = listName2
    }
    fun saveJikanItem(apiItem: ApiItem) {
        _apiItem.value = apiItem
    }
}