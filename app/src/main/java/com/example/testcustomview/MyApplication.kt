package com.example.testcustomview

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Thread.sleep(111)
//        thread200()
    }

    private fun thread200() {
        Thread.sleep(200)
    }
}