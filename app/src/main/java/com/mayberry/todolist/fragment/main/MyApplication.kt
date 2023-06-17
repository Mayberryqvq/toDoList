package com.mayberry.todolist.fragment.main

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    companion object {
        private var _app: Application? = null

        @JvmStatic
        fun getApp() = _app!!
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        _app = this
    }
}
