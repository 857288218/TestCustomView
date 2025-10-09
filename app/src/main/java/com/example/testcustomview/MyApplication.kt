package com.example.testcustomview

import android.app.Application
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import com.example.testcustomview.mianshi.ByteDance
import com.example.testcustomview.mianshi.XiaoPeng
import com.example.testcustomview.mianshi.Zuoyebang

class MyApplication : Application() {

    var area: Int = 0

    override fun onCreate() {
        super.onCreate()
        ByteDance().test()

//        Thread.sleep(111)
//        thread200()

        // 输出this is a animal
        val animal: Animal = Dog()
        println(animal.info())

        Log.d("rjqtest", "判断两个数组中元素是否完全一致，不需要元素顺序相同,result=${XiaoPeng().testArraySameByHashMap()}")
        XiaoPeng().testThreadOrderPrint()

        Zuoyebang().mergeOrderArray()
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

// 验证扩展函数不支持多态：多态是针对对象的，扩展函数反编译后的java代码是静态的，所以没有多态可言
open class Animal
class Dog : Animal()
fun Animal.info() = "this is a animal"
fun Dog.info() = "this is a dog"