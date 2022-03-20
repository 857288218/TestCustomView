package com.example.testcustomview

import android.app.Activity
import android.content.Intent

object TestObject {
    // public static final TestObject INSTANCE = new TestObject();

    // public static final String constVal = "constVal"
    const val constVal = "constVal"

    // private static final String valRjq = "valRjq" ；提供了getValRjq
    val valRjq = "valRjq"

    // private static final String valRjqPrivate = "valRjqPrivate"
    private val valRjqPrivate = "valRjqPrivate"

    // private static String varRjq = "varRjq"; 提供了setVarRjq(),getVarRjq()
    var varRjq = "varRjq"

    // private static String varRjqPrivate = "varRjqPrivate"
    private var varRjqPrivate = "varRjqPrivate"

    // public final void testFun()
    fun testFun() {

    }

    inline fun<reified T : Activity> Activity.testReified() {
        startActivity(Intent(this, T::class.java))
    }

    inline fun<T : Activity> Activity.testReified(clazz: Class<T>) {
        startActivity(Intent(this, clazz))
    }
}