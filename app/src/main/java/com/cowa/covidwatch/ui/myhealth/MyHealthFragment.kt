package com.cowa.covidwatch.ui.myhealth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cowa.covidwatch.R

class MyHealthFragment : Fragment() {

    private lateinit var myhealthViewModel: MyHealthViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        myhealthViewModel =
                ViewModelProviders.of(this).get(MyHealthViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_health, container, false)
        val textView: TextView = root.findViewById(R.id.text_myhealth)
        myhealthViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
