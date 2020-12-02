package com.cowa.covidwatch.ui.mapinter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InterMapViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is international map Fragment"
    }
    val text: LiveData<String> = _text
}