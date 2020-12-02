package com.cowa.covidwatch.ui.signalcase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cowa.covidwatch.R

class SignalCaseFragment : Fragment() {

    private lateinit var signalCaseViewModel: SignalCaseViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        signalCaseViewModel =
                ViewModelProviders.of(this).get(SignalCaseViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_signal_case, container, false)
        val textView: TextView = root.findViewById(R.id.text_signal_case)
        signalCaseViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
