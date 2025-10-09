package com.example.testcustomview.mianshi

import android.util.Log
import java.util.Arrays
import java.util.HashMap

class XiaoPeng {
    // 判断两个数组中元素是否完全一致，不需要元素顺序相同
    val array1 = arrayOf("a", "b", "c", "d", "e")
    val array2 = arrayOf("e", "b", "c", "a", "d")
    // sort时间复杂度是 O(nlogn), equals时间复杂度是O(n)，所以取最高的时间复杂度O(nlogn)
    fun testArraySameBySortEquals(): Boolean {
        Arrays.sort(array1)
        Arrays.sort(array2)
        return Arrays.equals(array1, array2)
    }
    // 时间复杂度O(n)
    fun testArraySameByHashMap(): Boolean {
        if (array1.size != array2.size) {
            return false
        }
        val hashMap = HashMap<String, Int>()
        array1.forEach {
            val value = hashMap[it]
            if (value == null) {
                hashMap[it] = 1
            } else {
                hashMap[it] = value + 1
            }
        }
        array2.forEach {
            val value = hashMap[it]
            if (value == null || value == 0) {
                return false
            } else {
                hashMap[it] = value - 1
            }
        }
        return true
    }

    // 五个线程排序输出：五个线程编号1、2、3、4、5，每个线程的执行完成时间不确定，要求按照排号顺序输出各个线程的结果，并且不能等所有线程执行完毕再排序输出。
    // 比如线程2先于线程1执行完了此时还不能输出，要等线程1输出完之后才能输出，其他线程以此类推。
    // 使用Thread.join()，其主要作用是让一个线程等待另一个线程执行完成。join()最核心的作用是确保线程的执行顺序。
    // 例如，主线程启动了一个子线程来执行一项耗时任务，而主线程的后续操作需要用到这个子线程的执行结果。
    // 此时，在主线程中调用子线程的join()，就可以确保在继续执行主线程之前，子线程已经执行完毕。Android中建议使用协程async await实现类似功能,挂起函数不卡主线程。join中是使用wait notify实现，会卡主线程。
    class TestThread(private val preThread: Thread?, private val id: Int): Thread() {
        override fun run() {
            super.run()
            // doSomething
            preThread?.join()
            Log.d("TestThread", "$id")
        }
    }
    fun testThreadOrderPrint() {
        val t1 = TestThread(null, 1)
        val t2 = TestThread(t1, 2)
        val t3 = TestThread(t2, 3)
        val t4 = TestThread(t3, 4)
        val t5 = TestThread(t4, 5)
        t1.start()
        t2.start()
        t3.start()
        t4.start()
        t5.start()
    }
}