package com.cowa.covidwatch.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.cowa.covidwatch.MainActivity
import com.cowa.covidwatch.R
import com.cowa.covidwatch.ui.maps.MapsViewModel
import com.google.android.material.tabs.TabLayout

class NewsFragment : Fragment() {

    private lateinit var vi : View
    lateinit var tabLayout : TabLayout
    lateinit var viewpager : ViewPager


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        vi = inflater.inflate(R.layout.fragment_news, container, false)
        tabLayout = vi.findViewById(R.id.ntab_layout) as TabLayout
        viewpager = vi.findViewById(R.id.nviewPager) as ViewPager
        viewpager.setAdapter(childFragmentManager?.let { NewsAdapter(it) })
        tabLayout.post(Runnable { tabLayout.setupWithViewPager(viewpager) })


        return vi
    }
}
