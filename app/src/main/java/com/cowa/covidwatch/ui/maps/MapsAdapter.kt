package com.cowa.covidwatch.ui.maps

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.cowa.covidwatch.ui.mapnat.NatMapFragment
import com.cowa.covidwatch.ui.news.NewsFragment
import com.example.citylist.ui.main.InterMapFragment


class MapsAdapter (fragmentmanager :FragmentManager  ): FragmentPagerAdapter(fragmentmanager){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> InterMapFragment()
            1 ->NatMapFragment()
           else ->NatMapFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            1 -> "محلية"
            0 -> "عالمية"
            else -> {
                return "خراءط"
            }
        }
    }



    override fun getCount(): Int {
        return  2
    }
}