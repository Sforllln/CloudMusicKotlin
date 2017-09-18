package com.zs.cloudmusickotlin.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SimpleItemAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.llln.rxjava2kotlin.fragment.BaseLazyFragment
import com.zs.cloudmusickotlin.R
import com.zs.cloudmusickotlin.ui.adapter.LocalMusicRvAdapter
import com.zs.cloudmusickotlin.ui.widget.MyBottomSheetDialog
import com.zs.cloudmusickotlin.ui.widget.MyLinearLayoutManager
import com.zs.cloudmusickotlin.utils.DensityUtil
import com.zs.cloudmusickotlin.utils.HandlerUtil
import kotlinx.android.synthetic.main.fragment_local_music.*


class LocalMusicFragment : BaseLazyFragment(), LocalMusicRvAdapter.OnClickListener {


    data class TopData(var titles: ArrayList<String>, var icons: ArrayList<Int>, var count: ArrayList<Int>)
    data class CenterData(var arrowIcon: Int, var titles: String, var settingIcon: Int)
    data class CenterChildData(var icon: Int, var name: String, var musicCount: Int, var chooseIcon: Int)
    data class BottomData(var titles: String, var icons: ArrayList<Int>, var text: ArrayList<String>)


    private val titles = arrayListOf("本地音乐", "最近播放", "下载管理", "我的电台", "我的收藏")
    private val icons = arrayListOf(R.mipmap.localmusic, R.mipmap.play_yet, R.mipmap.download, R.mipmap.ownstation, R.mipmap.collection)
    private val count = arrayListOf(6, 0, 1, 1, 0)
    private lateinit var mTopData: TopData
    private lateinit var mCenterData: CenterData
    private lateinit var mCenterChildData: CenterChildData
    private var centerList: ArrayList<CenterChildData> = ArrayList()

    var centerListNew: ArrayList<CenterChildData> = ArrayList()

    var mBottomData: BottomData? = null
    private var isFirst = true

    private lateinit var bottomDialog: MyBottomSheetDialog

    var localMusicAdapter: LocalMusicRvAdapter? = null

    override fun initPrepare() {
        if (isFirst) {
            initRvData()
            initSwipeRefreshLayout()
            initRecyclerView()
            bottomDialog = MyBottomSheetDialog(activity)
        }
        isFirst = false
    }

    private fun initSwipeRefreshLayout() {
        srl_localMusic.setColorSchemeResources(R.color.colorPrimary)
        srl_localMusic.setOnRefreshListener {
            HandlerUtil.runOnUiThreadDelay(Runnable {
                update()
                srl_localMusic.isRefreshing = false
            }, 2000)
        }

    }

    private fun initRvData() {
        mTopData = TopData(titles, icons, count)

        mCenterData = CenterData(R.mipmap.rol, "创建的歌单", R.mipmap.setting)

        mBottomData = BottomData("", arrayListOf(), arrayListOf())

        mCenterChildData = CenterChildData(R.mipmap.love_music, "我喜欢的音乐", 0, R.mipmap.more_choose1)

        centerList.add(mCenterChildData)

        localMusicAdapter = LocalMusicRvAdapter(activity, mTopData, mCenterData, centerList, mBottomData!!)

    }

    private fun initRecyclerView() {
        rv_localMusic.layoutManager = MyLinearLayoutManager(context)
        rv_localMusic.setHasFixedSize(true)
        localMusicAdapter!!.setOnClick(this)
        rv_localMusic.adapter = localMusicAdapter
        //刷新不闪烁
        (rv_localMusic.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
//        rv_localMusic.itemAnimator.changeDuration = 0
    }

    private fun centerSettingClick() {
        bottomDialog.setContentView(R.layout.bottom_dialog_createmusiclist)
        bottomDialog.show()

        val createList = bottomDialog.findViewById(R.id.ll_create_music_list)
        val listManger = bottomDialog.findViewById(R.id.ll_musiclist_manger)

        createList!!.setOnClickListener {
            mToast("create List")
        }
        listManger!!.setOnClickListener {
            mToast("List manger")
        }
    }

    private fun childSettingClick(position: Int, listName: String) {
        bottomDialog.setContentView(R.layout.bottom_dialog_musiclistsetting)
        bottomDialog.show()

        val title: TextView = bottomDialog.findViewById(R.id.child_musicList_title) as TextView
        val downLoad = bottomDialog.findViewById(R.id.ll_download_music_list)
        val share = bottomDialog.findViewById(R.id.ll_share_music_list)
        val editMusicList = bottomDialog.findViewById(R.id.ll_edit_music_list)
        val deleteMusicList = bottomDialog.findViewById(R.id.ll_delete_list)

        title.text = "歌单: " + listName
        if (position == 6) {
            deleteMusicList!!.visibility = View.GONE
            editMusicList!!.visibility = View.GONE
            val lp: LinearLayout.LayoutParams = share!!.layoutParams as LinearLayout.LayoutParams
            lp.bottomMargin = DensityUtil.dip2px(activity, 8f)
            share.layoutParams = lp
        }

        downLoad!!.setOnClickListener { mToast("downLoad") }
        share!!.setOnClickListener { mToast("share") }
        editMusicList!!.setOnClickListener { mToast("editList") }
        deleteMusicList!!.setOnClickListener { mToast("delete") }
    }


    override fun onCenterChildClick(position: Int, listName: String) {
        Toast.makeText(activity, "onClick CC $position  -- $listName", Toast.LENGTH_SHORT).show()
    }


    override fun onTopItemClick(position: Int, topItemName: String) {
        Toast.makeText(activity, "onClick Top $position -- $topItemName", Toast.LENGTH_SHORT).show()
    }

    override fun onCenterChildSettingClick(position: Int, listName: String) {
        childSettingClick(position, listName)
    }

    override fun onCenterSettingClick() {
        centerSettingClick()
    }

    override fun onBottomImageClick(position: Int) {
        Toast.makeText(activity, "onClick BTI $position", Toast.LENGTH_SHORT).show()
    }


    override fun onInvisible() {

    }

    override fun initData() {
        HandlerUtil.runOnUiThreadDelay(Runnable {
            //update()
        }, 2000)
    }


    // 模拟测试数据更新操作
    private fun update() {
        mBottomData = BottomData("推荐歌单", arrayListOf(R.mipmap.ic_launcher_round, R.mipmap.ic_launcher, R.mipmap.ic_launcher), arrayListOf("强烈推荐", "一般推荐", "不推荐"))
        localMusicAdapter!!.upDateBottomData(mBottomData!!)

        val count = arrayListOf(18, 99, 122, 9, 0)
        mTopData = TopData(titles, icons, count)
        localMusicAdapter!!.upDataTopData(mTopData)


        val mCenterChildData5 = CenterChildData(R.mipmap.centerchildicon, "音乐1", 999, R.mipmap.more_choose1)
        val mCenterChildData2 = CenterChildData(R.mipmap.centerchildicon, "音乐2", 888, R.mipmap.more_choose1)
        val mCenterChildData3 = CenterChildData(R.mipmap.centerchildicon, "音乐3", 777, R.mipmap.more_choose1)
        val mCenterChildData4 = CenterChildData(R.mipmap.centerchildicon, "音乐4", 666, R.mipmap.more_choose1)
        centerList.clear()
        centerList.add(mCenterChildData)
        centerList.add(mCenterChildData5)
        centerList.add(mCenterChildData2)
        centerList.add(mCenterChildData3)
        centerList.add(mCenterChildData4)

        localMusicAdapter!!.upDataCenterChildData(centerList)
//        val diffResult :DiffUtil.DiffResult = DiffUtil.calculateDiff(ItemDiffCallBack(centerList,centerListNew))
//        diffResult.dispatchUpdatesTo(localMusicAdapter)
    }

    override fun onVisible() {

    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater!!.inflate(R.layout.fragment_local_music, container, false)


    private fun Fragment.mToast(message: CharSequence) = Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()

}
