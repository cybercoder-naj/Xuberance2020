package com.sxcs.xuberance2020

import android.app.Application
import android.content.Context

class BaseApplication : Application() {

    companion object {
        private lateinit var context: Context

        fun getAppContext() = context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}