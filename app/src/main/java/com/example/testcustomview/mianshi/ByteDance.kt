package com.example.testcustomview.mianshi

import android.util.Log
import okhttp3.internal.notify
import okhttp3.internal.wait

class ByteDance {

    var i = 0
    val lock = Any()

    // 两个线程交替打印0-100
    inner class MyThread(private val name: String) : Thread() {
        override fun run() {
            super.run()
            while (i <= 100) {
                synchronized(lock) {
                    Log.d("rjqtest", "$name $i")
                    i++
                    // 通知另一个线程解除其等待状态，可以去获取对象锁。需要等到该线程退出synchronized块后释放对象锁
                    lock.notify()
                    // 使该线程进入等待状态，并立即释放对象锁
                    lock.wait()
                }
            }
        }
    }

    fun test() {
        MyThread("Thread1").start()
        MyThread("Thread2").start()
    }
}