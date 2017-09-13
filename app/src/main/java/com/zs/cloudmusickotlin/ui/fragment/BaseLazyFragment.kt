package com.example.llln.rxjava2kotlin.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Created by zs on 2017/7/5.
 *
 * Fragment懒加载
 *
 */

abstract class BaseLazyFragment : Fragment() {

    private var isPrepared: Boolean? = false
    private var isFirst: Boolean = true
    var mRootView: View? = null
    var isVisible: Boolean? = false
    var mContext: Context? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isPrepared = true
        //init
        initPrepare()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isVisible = true
            lazyLoad()
        } else {
            isVisible = false
            onInvisible()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = initView(inflater, container, savedInstanceState)
        }
        return mRootView
    }


    private fun lazyLoad() {
        if (!isPrepared!! || !isVisible!! || !isFirst) {
            onVisible()
            return
        }
        initData()
        isFirst = false
    }

    override fun onResume() {
        super.onResume()
        if (userVisibleHint) {
            userVisibleHint = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    /**
     * 在onActivityCreated中调用的方法，可以用来进行初始化操作。
     */
    abstract fun initPrepare()

    /**
     * fragment被设置为不可见时调用
     */
    abstract fun onInvisible()

    /**
     * 这里获取数据，刷新界面,第一次加载且只加载一次
     */
    abstract fun initData()

    /**
     * 再次可见
     * */
    abstract fun onVisible()

    /**
     * 初始化布局，请不要把耗时操作放在这个方法里，这个方法用来提供一个
     * 基本的布局而非一个完整的布局，以免ViewPager预加载消耗大量的资源。
     */
    abstract fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?

}





