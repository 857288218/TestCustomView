package com.example.testcustomview.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.testcustomview.R
import event.StickyStringEvent
import org.greenrobot.eventbus.EventBus

class TelescopeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_telescope)
        findViewById<TextView>(R.id.shimmer).setOnClickListener {
            startActivity(Intent(this, ThreeActivity::class.java))
        }

        EventBus.getDefault().postSticky(StickyStringEvent("sticky string msg"))
    }
}