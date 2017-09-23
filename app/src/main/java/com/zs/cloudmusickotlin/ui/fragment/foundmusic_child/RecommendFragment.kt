package com.zs.cloudmusickotlin.ui.fragment.foundmusic_child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.llln.rxjava2kotlin.fragment.BaseLazyFragment
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.zs.cloudmusickotlin.R
import com.zs.cloudmusickotlin.ui.image.GlideImage
import kotlinx.android.synthetic.main.fragment_recommend.*

class RecommendFragment : BaseLazyFragment() {

    //for test
    private var defaultBannerImage = arrayListOf(R.mipmap.banner_default)


    override fun initPrepare() {

    }

    override fun onInvisible() {
        if (banner_recommend != null) {
            banner_recommend.stopAutoPlay()
        }
    }

    override fun initData() {
        initBanner()
    }

    override fun onVisible() {
        if (banner_recommend != null) {
            banner_recommend.startAutoPlay()
        }
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater!!.inflate(R.layout.fragment_recommend, container, false)


    private fun initBanner() {
        banner_recommend.setImageLoader(GlideImage()) //图片加载框架 这里采用Glide
        banner_recommend.setImages(defaultBannerImage) //设置banner的图片
        banner_recommend.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        banner_recommend.setBannerAnimation(Transformer.Default)   //动画
        banner_recommend.setDelayTime(3000) //轮播时间
        banner_recommend.setIndicatorGravity(BannerConfig.CENTER) //指示器位置
        banner_recommend.start()
    }


}
