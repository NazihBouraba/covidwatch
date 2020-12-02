package com.cowa.covidwatch.ui.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.cowa.covidwatch.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_maps.*

class MapsFragment : Fragment()  {

    private lateinit var mapsViewModel: MapsViewModel
    private lateinit var vi : View
    lateinit var tabLayout : TabLayout
    lateinit var viewpager : ViewPager


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            vi = inflater.inflate(R.layout.fragment_maps, container, false)
            tabLayout = vi.findViewById(R.id.tab_layout) as TabLayout
            viewpager = vi.findViewById(R.id.viewPager) as ViewPager
            viewpager.setAdapter(childFragmentManager?.let { MapsAdapter(it) })
            tabLayout.post(Runnable { tabLayout.setupWithViewPager(viewpager) })


        return vi

    }
}
