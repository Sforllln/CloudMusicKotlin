package com.zs.cloudmusickotlin.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.llln.rxjava2kotlin.fragment.BaseLazyFragment
import com.zs.cloudmusickotlin.R
import com.zs.cloudmusickotlin.ui.fragment.foundmusic_child.AnchorStationFragment
import com.zs.cloudmusickotlin.ui.fragment.foundmusic_child.MusicListFragment
import com.zs.cloudmusickotlin.ui.fragment.foundmusic_child.RankingListFragment
import com.zs.cloudmusickotlin.ui.fragment.foundmusic_child.RecommendFragment
import kotlinx.android.synthetic.main.fragment_found_music.*


class FoundMusicFragment : BaseLazyFragment() {
    override fun initPrepare() {
        initTab()

    }

    override fun onInvisible() {

    }

    override fun initData() {

    }

    override fun onVisible() {

    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_found_music, container, false)
        return view
    }


    private fun initTab() {
        val titleImageList = arrayListOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher)
        val fragmentList = arrayListOf<Fragment>(RecommendFragment(), MusicListFragment(), AnchorStationFragment(), RankingListFragment())
        val titleList = arrayListOf("个性推荐", "歌单", "主播电台", "排行榜")

        tb_toolbar.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                vp_main.currentItem = tab!!.position
            }

        })
        vp_main.adapter = TopTabPagerAdapter(activity.supportFragmentManager, fragmentList, titleImageList, activity, titleList)
//
//        tb_toolbar.addTab(tb_toolbar.newTab())
//        tb_toolbar.addTab(tb_toolbar.newTab(), true)
//        tb_toolbar.addTab(tb_toolbar.newTab())

        tb_toolbar.tabMode = TabLayout.MODE_FIXED
        tb_toolbar.setupWithViewPager(vp_main)
    }

    class TopTabPagerAdapter(fm: FragmentManager?, private var listFragment: ArrayList<Fragment>,
                             var titleImageList: ArrayList<Int>, var context: Context, private var list_title: ArrayList<String>) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment = listFragment[position]

        override fun getCount(): Int = listFragment.size

        override fun getPageTitle(position: Int): CharSequence =
                list_title[position % list_title.size]


    }


}
