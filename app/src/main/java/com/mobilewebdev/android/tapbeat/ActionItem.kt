package com.mobilewebdev.android.tapbeat

interface ActionItem {
    fun doClick(px:Int, py:Int): Boolean
}