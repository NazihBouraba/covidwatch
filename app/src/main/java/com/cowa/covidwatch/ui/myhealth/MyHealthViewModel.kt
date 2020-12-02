package com.cowa.covidwatch.ui.myhealth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyHealthViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is my health Fragment"
    }
    val text: LiveData<String> = _text
}