package com.example.testcustomview.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.testcustomview.R

class TranslucentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translucent)
        Handler(Looper.getMainLooper()).postDelayed({
            MainActivity.liveDataTestPause.value = "pause"
            Handler(Looper.getMainLooper()).postDelayed({MainActivity.liveDataTestPause.value = "pause"}, 3000)
        }, 3000)
    }
}