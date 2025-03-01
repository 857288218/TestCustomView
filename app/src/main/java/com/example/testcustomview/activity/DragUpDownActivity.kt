package com.example.testcustomview.activity

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.testcustomview.R
import com.example.testcustomview.activity.CustomBottomSheetBehavior.STATE_COLLAPSED
import com.example.testcustomview.activity.CustomBottomSheetBehavior.STATE_EXPANDED
import com.example.testcustomview.util.dp2px
import com.example.testcustomview.util.getDisplayHeight

private const val BG_ALPHA = 0.95f

// 上滑脱手展开、下滑脱手收缩: CoordinatorLayout + BottomSheetBehavior
class DragUpDownActivity : AppCompatActivity() {

    private lateinit var bottomSheetBehavior: CustomBottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag_up_down)
        bottomSheetBehavior = (findViewById<LinearLayout>(R.id.ll_content).layoutParams as CoordinatorLayout.LayoutParams)
            .behavior as CustomBottomSheetBehavior<View>
        findViewById<View>(R.id.view_bottom).post {
            // 设置折叠时高度
            bottomSheetBehavior.peekHeight = getDisplayHeight() - findViewById<View>(R.id.view_bottom).height
        }
        bottomSheetBehavior.addBottomSheetCallback(
            object : CustomBottomSheetBehavior.BottomSheetCallback() {
                private var isExpanded = false
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (!isExpanded) {
                        if (newState == STATE_EXPANDED) {
                            isExpanded = true
                        }
                    } else if (newState == STATE_COLLAPSED) {
                        isExpanded = false
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    // 设置底层图层的透明度层MaskView
                    findViewById<View>(R.id.view_alpha_mask).alpha = slideOffset * BG_ALPHA
                }
            }
        )

        findViewById<View>(R.id.view_bottom).setOnClickListener {
            Toast.makeText(this, "bottom click", Toast.LENGTH_SHORT).show()
        }
    }
}