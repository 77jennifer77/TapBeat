package com.mobilewebdev.android.tapbeat

interface Updatable {
    fun update()
    fun offScreen(): Boolean
    fun clicked(): Boolean
    fun reset()
}