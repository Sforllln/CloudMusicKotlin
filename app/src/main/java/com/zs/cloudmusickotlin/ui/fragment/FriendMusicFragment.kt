package com.zs.cloudmusickotlin.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.llln.rxjava2kotlin.fragment.BaseLazyFragment
import com.zs.cloudmusickotlin.R


class FriendMusicFragment : BaseLazyFragment() {
    override fun initPrepare() {
    }

    override fun onInvisible() {
    }

    override fun initData() {
    }

    override fun onVisible() {
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_friend_music, container, false)
    }


}
