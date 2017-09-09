package com.energolabs.evergo.ui.views

import android.content.Context
import android.graphics.Typeface
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.widget.TextView
import com.energolabs.evergo.R
import com.ogaclejapan.smarttablayout.SmartTabLayout

/**
 * Created by Jose Duque on 10/12/2015.
 * Copyright (C) 2016 Energo
 *
 *
 * Copy or sale of this class is forbidden.
 */
class EnergoSmartTabLayout : SmartTabLayout, ViewPager.OnPageChangeListener {

    var selectedItemTextColor = R.color.energo_market_color
    private var activeItemIndex = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun createDefaultTabView(title: CharSequence?): TextView {
        val textView = super.createDefaultTabView(title)
        textView.typeface = Typeface.DEFAULT
        textView.setSingleLine()
        return textView
    }

    fun init() {
        setItemActive(0)
    }

    override fun setViewPager(viewPager: ViewPager?) {
        super.setViewPager(viewPager)
        viewPager?.addOnPageChangeListener(this)
    }

    private fun setItemActive(pos: Int) {
        val tv = this.getTabAt(pos) as TextView?
        tv?.setTextColor(
                resources?.getColor(selectedItemTextColor) ?: return
        )
    }

    private fun setItemInActive(pos: Int) {
        val tv = this.getTabAt(pos) as TextView?
        tv?.setTextColor(
                resources?.getColor(android.R.color.black) ?: return
        )
    }


    override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
    ) {

    }

    override fun onPageSelected(position: Int) {
        if (this.activeItemIndex != -1) {
            setItemInActive(activeItemIndex)
        }
        setItemActive(position)
        this.activeItemIndex = position

    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}
