package com.cowa.covidwatch.ui.news

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.cowa.covidwatch.ui.articles.ArticlesFragment

import com.cowa.covidwatch.ui.mapnat.NatMapFragment
import com.cowa.covidwatch.ui.netnews.NetNewsFragment

class NewsAdapter (fragmentmanager : FragmentManager): FragmentPagerAdapter(fragmentmanager)  {



    override fun getItem(position: Int): Fragment {

        return when(position){
            1 -> NetNewsFragment()
            0-> ArticlesFragment()
            else ->ArticlesFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "مقالات"
            1-> "عبر الانترنت"
            else -> {
                return "خراءط"
            }
        }
    }



    override fun getCount(): Int {
        return  2
    }
}