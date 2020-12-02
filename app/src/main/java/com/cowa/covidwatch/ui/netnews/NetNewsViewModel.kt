package com.cowa.covidwatch.ui.netnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NetNewsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is internet news Fragment"
    }
    val text: LiveData<String> = _text
}