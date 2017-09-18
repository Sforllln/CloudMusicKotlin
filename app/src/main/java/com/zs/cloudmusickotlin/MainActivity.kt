package com.zs.cloudmusickotlin

import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.WindowManager
import android.widget.RadioGroup
import android.widget.Toast
import com.zs.cloudmusickotlin.ui.fragment.FoundMusicFragment
import com.zs.cloudmusickotlin.ui.fragment.FriendMusicFragment
import com.zs.cloudmusickotlin.ui.fragment.LocalMusicFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, RadioGroup.OnCheckedChangeListener {

    private val LOCAL_MUSIC_PAGE = 0
    private val FOUND_MUSIC_PAGE = 1
    private val FRIEND_MUSIC_PAGE = 2


    private val fragmentList = arrayListOf<Fragment>(LocalMusicFragment(), FoundMusicFragment(), FriendMusicFragment())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBarTransparent()
        initView()
        initViewPagerAndRadioButton()
    }


    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            rb_foundMusic.id -> vp_withToolbar.currentItem = FOUND_MUSIC_PAGE
            rb_localMusic.id -> vp_withToolbar.currentItem = LOCAL_MUSIC_PAGE
            rb_musicFriend.id -> vp_withToolbar.currentItem = FRIEND_MUSIC_PAGE
        }
    }


    private fun initViewPagerAndRadioButton() {
        rb_foundMusic.isSelected = true
        rg_toolbar.setOnCheckedChangeListener(this)
        vp_withToolbar.adapter = ToolBarTab(supportFragmentManager, fragmentList)
        vp_withToolbar.offscreenPageLimit = 2//避免切换时 scrollbar显示
        vp_withToolbar.currentItem = FOUND_MUSIC_PAGE
        vp_withToolbar.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                resetRadioButtonStatus()
                when (position) {
                    LOCAL_MUSIC_PAGE -> {
                        rb_localMusic.isSelected = true
                        rb_localMusic.isChecked = true
                    }
                    FOUND_MUSIC_PAGE -> {
                        rb_foundMusic.isSelected = true
                        rb_foundMusic.isChecked = true
                    }
                    FRIEND_MUSIC_PAGE -> {
                        rb_musicFriend.isSelected = true
                        rb_musicFriend.isChecked = true
                    }
                }
            }
        })
    }

    private fun resetRadioButtonStatus() {
        if (rb_foundMusic.isSelected) rb_foundMusic.isSelected = false
        if (rb_localMusic.isSelected) rb_localMusic.isSelected = false
        if (rb_musicFriend.isSelected) rb_musicFriend.isSelected = false
    }


    private fun initView() {
        toolbar.title = ""
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.setDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        bar_search.setOnClickListener {
            Toast.makeText(this, "bar_search", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val localLayoutParams = window.attributes
            localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                //将侧边栏顶部延伸至status bar
                drawer_layout.fitsSystemWindows = true
                //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
                drawer_layout.clipToPadding = false
            }
        }
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    class ToolBarTab(fm: FragmentManager, private var fragmentList: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int = fragmentList.size


        override fun getItem(position: Int): Fragment = fragmentList[position]

    }


}
