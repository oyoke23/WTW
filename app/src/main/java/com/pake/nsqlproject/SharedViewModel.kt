package com.pake.nsqlproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SharedViewModel: ViewModel() {
    private var _allData = MutableLiveData<AllData>()
    var allData: MutableLiveData<AllData> = _allData

    fun saveAllData(data: AllData) {
        _allData.value = data
    }

}