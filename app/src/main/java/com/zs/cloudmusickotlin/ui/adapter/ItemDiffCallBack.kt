package com.zs.cloudmusickotlin.ui.adapter

import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.text.TextUtils
import com.zs.cloudmusickotlin.ui.fragment.LocalMusicFragment

/**
 * Created by Jeong Woo on 2017/9/9.
 *
 *
 *
 */
class ItemDiffCallBack(private var oldList: List<LocalMusicFragment.CenterChildData>,
                       private var newList: List<LocalMusicFragment.CenterChildData>) : DiffUtil.Callback() {


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = oldList[oldItemPosition].name == newList[newItemPosition].name


    override fun getOldListSize(): Int = if (oldList.isEmpty()) 0 else oldList.size


    override fun getNewListSize(): Int = if (newList.isEmpty()) 0 else newList.size


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = oldList[oldItemPosition].musicCount == newList[newItemPosition].musicCount

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldData = oldList[oldItemPosition]
        val newData = newList[oldItemPosition]
        val diffBundle = Bundle()
        if (TextUtils.equals(oldData.name, newData.name)) {
            diffBundle.putString("centerChildName", newData.name)
        }
        if (TextUtils.equals(oldData.musicCount, newData.musicCount)) {
            diffBundle.putString("centerChildCount", newData.musicCount)
        }
        return diffBundle
    }

}