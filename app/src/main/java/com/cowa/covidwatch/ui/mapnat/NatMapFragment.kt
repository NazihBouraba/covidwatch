package com.cowa.covidwatch.ui.mapnat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cowa.covidwatch.R

class NatMapFragment : Fragment() {

    private lateinit var natMapViewModel: NatMapViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        natMapViewModel =
                ViewModelProviders.of(this).get(NatMapViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_nat_map, container, false)
        val textView: TextView = root.findViewById(R.id.text_nat_map)
        natMapViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
