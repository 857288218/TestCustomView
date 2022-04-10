package com.example.testcustomview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewStub
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.testcustomview.R
import com.example.testcustomview.fragment.TestFragment
import com.example.testcustomview.fragment.TestViewPagerFragment
import com.google.android.material.tabs.TabLayout
import bean.Child

class ViewStubActivity : AppCompatActivity() {

    private var viewStub: ViewStub? = null
    private var viewPager: ViewPager? = null
    private var tabLayout: TabLayout? = null
    private var frameLayout: FrameLayout? = null
    var child = Child<Int>().apply {
        name = "ViewStubActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_stub)
//        viewStub = findViewById(R.id.view_stub)
//        viewStub?.visibility = View.VISIBLE
        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)
        tabLayout?.setupWithViewPager(viewPager)
//        viewPager?.adapter = Adapter(supportFragmentManager)

        frameLayout = findViewById(R.id.frameLayout)
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, TestFragment.createFragment(Child()), "tag").commit()
        Handler(Looper.getMainLooper()).postDelayed({ Log.d("testargue", child.name)}, 2000)
    }

    class Adapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount(): Int {
            return 1
        }

        override fun getItem(position: Int): Fragment {
            val fragment = TestViewPagerFragment("$position")
            fragment.arguments = Bundle().apply {
                putString("hhh", "hhhh")
            }
            return fragment
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
}