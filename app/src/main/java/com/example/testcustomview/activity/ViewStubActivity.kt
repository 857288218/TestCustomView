package com.example.testcustomview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewStub
import com.example.testcustomview.R

class ViewStubActivity : AppCompatActivity() {

    private var viewStub: ViewStub? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_stub)
//        viewStub = findViewById(R.id.view_stub)
//        viewStub?.visibility = View.VISIBLE
    }
}