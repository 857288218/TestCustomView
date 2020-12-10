package com.example.testcustomview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class TelescopeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_telescope)
        findViewById<TextView>(R.id.shimmer).setOnClickListener {
            startActivity(Intent(this, ThreeActivity::class.java))
        }
    }
}