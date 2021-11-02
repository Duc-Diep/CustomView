package com.ducdiep.excustomview.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class ClockViewModel :ViewModel(){
    var calendar = MutableLiveData<Calendar>()
    fun validateData(hours:String,minutes:String,second:String):Boolean{
        return hours.isNotEmpty()&&minutes.isNotEmpty()&&second.isNotEmpty()&&hours.toInt()<24&&minutes.toInt()<60&&second.toInt()<60
    }
}