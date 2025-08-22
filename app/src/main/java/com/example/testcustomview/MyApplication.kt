package com.example.testcustomview

import android.app.Application
import android.view.View
import android.view.View.OnClickListener
import com.example.testcustomview.mianshi.ByteDance

class MyApplication : Application() {

    var area: Int = 0

    override fun onCreate() {
        super.onCreate()
        ByteDance().test()
        Thread.sleep(111)
//        thread200()
    }

    private fun thread200() {
        Thread.sleep(200)
    }

    var shape = { a: String ->
        var name: String = ""
        fun a() {
            name = "ss"
        }
    }

    fun test(s: (Int) -> String) {

    }
}