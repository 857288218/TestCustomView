package com.example.testcustomview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener

class TestFragment : Fragment() {

    // 编译成java生成一个static final的class，在TestFragment里有该类的常量static final Companion Companion
    companion object {
        fun createFragment() = TestFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setFragmentResultListener("result_key") { resultKey, bundle ->

        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
}