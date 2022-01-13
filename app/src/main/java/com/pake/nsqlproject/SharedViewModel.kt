package com.pake.nsqlproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pake.nsqlproject.data.AllData

class SharedViewModel: ViewModel() {
    private var _allData = MutableLiveData<AllData>()
    var allData: MutableLiveData<AllData> = _allData

    fun saveAllData(data: AllData) {
        _allData.value = data
    }
}