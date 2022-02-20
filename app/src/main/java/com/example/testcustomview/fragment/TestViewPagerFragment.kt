package com.example.testcustomview.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testcustomview.R

class TestViewPagerFragment(val name: String) : Fragment() {

    constructor() : this("no")

    private var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("testViewPagerFragment", "onCreate: $name")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            Log.d("testViewPagerFragment", "new root: $name")
            rootView = LayoutInflater.from(context).inflate(R.layout.fragment_test, container, false)
        }
        Log.d("testViewPagerFragment", "onCreateView: $name")
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("testViewPagerFragment", "onDestroyView: $name")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("testViewPagerFragment", "onDestroy: $name")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("testViewPagerFragment", "onDetach: $name")
    }
}