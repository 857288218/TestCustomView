package com.example.testcustomview.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import test.Child

class TestFragment : Fragment() {

    // 编译成java生成一个static final的class，在TestFragment里有该类的常量static final Companion Companion
    companion object {
        fun createFragment(child: Child<Int>) = TestFragment().apply {
            arguments = Bundle().apply {
                // fragment使用Bundle传递对象不涉及跨进程，其实传递的引用；
                // 但activity通过intent传递对象涉及跟AMS跨进程通信，这是一次值传递（深拷贝）的过程，拿到的对象和传递的对象不是一个
                putSerializable("a", child)
                putSerializable("b", Child<Int>())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setFragmentResultListener("result_key") { resultKey, bundle ->

        }
        super.onCreate(savedInstanceState)
        val child = arguments?.getSerializable("a") as Child<Int>
        val childB = arguments?.getSerializable("b")
        child.name = "TestFragment"
        Log.d("testargue", child.name)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
}