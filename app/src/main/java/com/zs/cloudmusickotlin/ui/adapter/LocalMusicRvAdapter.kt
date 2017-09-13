package com.zs.cloudmusickotlin.ui.adapter

import android.animation.ObjectAnimator
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.zs.cloudmusickotlin.R
import com.zs.cloudmusickotlin.ui.fragment.LocalMusicFragment.*
import com.zs.cloudmusickotlin.utils.ImageUtils
import kotlinx.android.synthetic.main.item_localmusic_bottom.view.*
import kotlinx.android.synthetic.main.item_localmusic_center.view.*
import kotlinx.android.synthetic.main.item_localmusic_center_child.view.*
import kotlinx.android.synthetic.main.item_localmusic_top.view.*
import java.util.*


/**
 * Created by zs Woo on 2017/8/30.
 *
 * RV 适配器
 *
 */
class LocalMusicRvAdapter(var context: Context,
                          private var topData: TopData,
                          private var centerData: CenterData,
                          private var centerChildData: ArrayList<CenterChildData>,
                          private var bottomData: BottomData)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val CENTER_CHILD_COUNT = 6 //顶部前6个栏目为默认
    private var onClick: OnClickListener? = null //栏目点击事件
    private var tag = true   //点击事件控制位
    private var bottomDividerImage: View? = null  //底部的分割线
    private var centerViewPosition: TextView? = null
    private var centerChildView: ArrayList<View> = arrayListOf() //收藏栏
    private var mCenterViewHolder: centerViewHolder? = null

    enum class VIEW_TYPE {
        TOP,
        CENTER,
        CENTER_CHILD,
        BOTTOM
    }

    fun upDateBottomData(bottomData: BottomData) {
        this.bottomData = bottomData
        notifyItemInserted(getAllCount())
    }

    fun upDataCenterChildData(centerChildData: ArrayList<CenterChildData>) {
        this.centerChildData = centerChildData
        if (centerChildView[0].visibility == View.GONE) {
//            centerData.arrowIcon = R.mipmap.rol_h
            Log.d("tag2", this.centerChildView.size.toString() + "  size")
            if (this.centerChildView.size == 1) {
                Log.d("tag2", "第一次刷新不可见")
                if (bottomDividerImage != null)
                    bottomDividerImage!!.visibility = View.GONE
                ObjectAnimator.ofFloat(mCenterViewHolder!!.itemView.center_arrow, "rotation", 0f, -90f).setDuration(200).start()
                tag = true
                centerChildView.forEach { v -> setVisibility(tag, v) }
                notifyItemRangeInserted(5, getAllCount())
            } else {

                notifyItemChanged(5)
            }

        } else {
            Log.d("tag2", "第一次刷新可见")
            notifyItemRangeInserted(5, getAllCount())
        }
    }

    fun upDataTopData(topData: TopData) {
        this.topData = topData
        notifyItemRangeChanged(0, 4)
    }


    override fun getItemViewType(position: Int): Int {
        if (position <= 4) return VIEW_TYPE.TOP.ordinal
        if (centerChildData.size > 0) {
            if (position == 5) return VIEW_TYPE.CENTER.ordinal
        }
        if (getBottomSize() != 0) {
            if (position == getAllCount() - 1) return VIEW_TYPE.BOTTOM.ordinal
        }
        return VIEW_TYPE.CENTER_CHILD.ordinal
    }


    private fun getBottomSize(): Int = if (bottomData.icons.size == 0) 0 else 1


    private fun getAllCount(): Int {
        val topCount = topData.count.size
        val centerCount = if (centerChildData.size > 0) 1 else 0
        val childCount = centerChildData.size
        val bottomCount = getBottomSize()
        return topCount + childCount + bottomCount + centerCount
    }


    override fun getItemCount(): Int {
        Log.d("tag2", getAllCount().toString())
        return getAllCount()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        if (viewType == VIEW_TYPE.TOP.ordinal) {
            view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_localmusic_top, parent, false)
            return topViewHolder(view)
        }
        if (viewType == VIEW_TYPE.CENTER.ordinal) {
            view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_localmusic_center, parent, false)
            return centerViewHolder(view)
        }
        if (viewType == VIEW_TYPE.CENTER_CHILD.ordinal) {
            view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_localmusic_center_child, parent, false)
            return centerChildViewHolder(view)
        }
        if (viewType == VIEW_TYPE.BOTTOM.ordinal) {
            view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_localmusic_bottom, parent, false)
            return bottomViewHolder(view)
        }
        return super.createViewHolder(parent, viewType)
    }

/*

    //配合DiffUtils 对比数据刷新
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int, payloads: MutableList<Any>?) {
        super.onBindViewHolder(holder, position, payloads)
        if (payloads == null || payloads.isEmpty()) {
            onBindViewHolder(holder, position)
            return
        }
        if (holder is centerChildViewHolder) {
            val mBundle: Bundle = payloads[0] as Bundle
            mBundle.keySet().forEach { key ->
                if (key == "centerChildName") {
                    holder.itemView.center_child_titles.text = mBundle.get(key) as CharSequence
                }
                if (key == "centerChildCount") {
                    holder.itemView.center_child_count.text = mBundle.get(key) as CharSequence
                }
            }
        }
    }
*/

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        if (holder is topViewHolder) {
            holder.itemView.tv_itemText_type1.text = topData.titles[position]
            ImageUtils.loadImageWithGlide(context, topData.icons[position], holder.itemView.iv_itemImage_type1)
            if (position == 4)
                holder.itemView.tv_itemText_count.text = "(专辑/歌手/MV/专栏)"
            else
                holder.itemView.tv_itemText_count.text = "(${topData.count[position]})"
            holder.itemView.setOnClickListener {

                onClick!!.onTopItemClick(position, topData.titles[position])

            }
        }

        if (holder is centerViewHolder) {
            this.mCenterViewHolder = holder
            holder.itemView.center_arrow.setImageResource(centerData.arrowIcon)
            centerViewPosition = holder.itemView.center_titles
            centerViewPosition!!.text = centerData.titles + "(${centerChildData.size})"
            holder.itemView.center_setting.setImageResource(centerData.settingIcon)
            if (holder.itemView.center_setting != null)
                holder.itemView.center_setting.setOnClickListener {
                    onClick!!.onCenterSettingClick()
                }

            holder.itemView.setOnClickListener {
                hintCenterChildView(holder)
            }
        }

        if (holder is centerChildViewHolder) {

            ImageUtils.loadImageWithGlide(context, centerChildData[position - CENTER_CHILD_COUNT].icon, holder.itemView.center_child_icon)
            holder.itemView.center_child_count.text = centerChildData[position - CENTER_CHILD_COUNT].musicCount

            ImageUtils.loadImageWithGlide(context, centerChildData[position - CENTER_CHILD_COUNT].chooseIcon, holder.itemView.center_child_moreChoose)
            holder.itemView.center_child_titles.text = centerChildData[position - CENTER_CHILD_COUNT].name
            if (!centerChildView.contains(holder.itemView))
                centerChildView.add(holder.itemView)

            holder.itemView.center_child_moreChoose.setOnClickListener {

                onClick!!.onCenterChildSettingClick(position, centerChildData[position - CENTER_CHILD_COUNT].name)

            }
            holder.itemView.setOnClickListener {

                onClick!!.onCenterChildClick(position, centerChildData[position - CENTER_CHILD_COUNT].name)

            }

        }
        if (holder is bottomViewHolder) {
            bottomDividerImage = holder.itemView.divider_image
            val lp: RecyclerView.LayoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
            lp.topMargin = 60
            holder.itemView.layoutParams = lp

            Log.d("tag2", (centerChildView[0].visibility == View.GONE).toString())
            if (centerChildView[0].visibility == View.GONE) {
                bottomDividerImage!!.visibility = View.GONE
            }

            holder.itemView.bottom_titles.text = bottomData.titles

            holder.itemView.bottom_item_text1.text = bottomData.text[0]
            ImageUtils.loadImageWithGlide(context, bottomData.icons[0], holder.itemView.bottom_item_image1)

            holder.itemView.bottom_item_text2.text = bottomData.text[1]
            ImageUtils.loadImageWithGlide(context, bottomData.icons[1], holder.itemView.bottom_item_image2)

            holder.itemView.bottom_item_text3.text = bottomData.text[2]
            ImageUtils.loadImageWithGlide(context, bottomData.icons[2], holder.itemView.bottom_item_image3)

            holder.itemView.bottom_item_image1.setOnClickListener { onClick!!.onBottomImageClick(1) }
            holder.itemView.bottom_item_image2.setOnClickListener { onClick!!.onBottomImageClick(2) }
            holder.itemView.bottom_item_image3.setOnClickListener { onClick!!.onBottomImageClick(3) }

        }
    }

    private fun hintCenterChildView(holder: centerViewHolder) {
        tag = !tag //标志位控制点击事件
        if (tag) {
            if (bottomDividerImage != null)
                bottomDividerImage!!.visibility = View.VISIBLE
            ObjectAnimator.ofFloat(holder.itemView.center_arrow, "rotation", -90f, 0f).setDuration(200).start()
        } else {
            if (bottomDividerImage != null)
                bottomDividerImage!!.visibility = View.GONE
            ObjectAnimator.ofFloat(holder.itemView.center_arrow, "rotation", 0f, -90f).setDuration(200).start()
        }
        centerChildView.forEach { v -> setVisibility(tag, v) }
    }


    private fun setVisibility(isVisible: Boolean, itemView: View) {
        val param = itemView.layoutParams as RecyclerView.LayoutParams
        if (isVisible) {
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT
            param.width = LinearLayout.LayoutParams.MATCH_PARENT
            itemView.visibility = View.VISIBLE
        } else {
            itemView.visibility = View.GONE
            param.height = 0
            param.width = 0
        }
        itemView.layoutParams = param
    }


    fun setOnClick(onClickListener: OnClickListener) {
        this.onClick = onClickListener
    }

    interface OnClickListener {
        fun onCenterSettingClick()
        fun onTopItemClick(position: Int, topItemName: String)
        fun onCenterChildClick(position: Int, listName: String)
        fun onCenterChildSettingClick(position: Int, listName: String)
        fun onBottomImageClick(position: Int)
    }


    class topViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class centerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class centerChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class bottomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}