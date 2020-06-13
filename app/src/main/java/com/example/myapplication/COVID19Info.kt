package com.example.myapplication

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toolbar
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout


class VideoFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.activity_info,container,false)

        val viewPager = view.findViewById<ViewPager>(R.id.viewPager)
        val adapter = fragmentManager?.let { ViewPagerAdapter(it) }

        if (adapter != null) {
            adapter.addFragment(OverviewFragment())
        }
        if (adapter != null) {
            adapter.addFragment(PreventionFragment())
        }
        if (adapter != null) {
            adapter.addFragment(SymptomsFragment())
        }

        viewPager.adapter = adapter

        val tablayout = view.findViewById<TabLayout>(R.id.tabLayout)

        tablayout.setupWithViewPager(viewPager)
        tablayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager.setCurrentItem(tab.position)
                }
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
        })


        val videoView = view.findViewById<VideoView>(R.id.COVIDVideo)
        var mediaController = MediaController(context);
        mediaController.setPadding(0, 0, 0, 100)
        mediaController.setAnchorView(videoView)
        videoView?.setMediaController(mediaController)
        var COVID19video = R.raw.covid19

        videoView?.setVideoURI(Uri.parse("android.resource://" + context?.packageName + "/" + COVID19video))
        videoView.seekTo(100)

        return view
    }

}