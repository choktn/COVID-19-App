package com.example.myapplication

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class ViewPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager){
    private val mFragmentList: ArrayList<Fragment> = ArrayList()
    private val titletabs = arrayOf<String>("Overview", "Prevention", "Symptoms")


    override fun getItem(position: Int): Fragment {
        return mFragmentList.get(position)
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titletabs[position]
    }
    fun addFragment(fragment: Fragment) {
        mFragmentList.add(fragment)
    }


}