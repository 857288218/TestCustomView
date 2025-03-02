package com.example.testcustomview.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testcustomview.R

// CoordinatorLayout + AppBarLayout 实现：可滑动头部 + 吸顶布局 + 可滑动列表
class CoordinatorAppBarFixTopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator_fix_top)
    }
}