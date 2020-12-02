package com.cowa.covidwatch.ui.signalcase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignalCaseViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is signal case Fragment"
    }
    val text: LiveData<String> = _text
}