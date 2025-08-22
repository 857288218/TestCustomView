package com.example.testcustomview.activity

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.testcustomview.R
import com.example.testcustomview.util.getDisplayHeight

private const val BG_ALPHA = 0.95f

// CoordinatorLayout + BottomSheetBehavior 实现二段式：上滑脱手展开、下滑脱手收缩
// 如果需要加刷新控件，参考GalaxyCarControlModule项目中car_fragment_flyme_main.xml
class DragUpDownBottomSheetActivity : AppCompatActivity() {

    private lateinit var bottomSheetBehavior: CustomBottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag_up_down)
        val bottomView = findViewById<View>(R.id.view_bottom)
        bottomSheetBehavior = (findViewById<LinearLayout>(R.id.ll_content).layoutParams as CoordinatorLayout.LayoutParams)
            .behavior as CustomBottomSheetBehavior<View>
        bottomView.post {
            // 设置折叠状态时(STATE_COLLAPSED)高度
            bottomSheetBehavior.peekHeight = getDisplayHeight() - bottomView.height
        }
        bottomSheetBehavior.addBottomSheetCallback(
            object : CustomBottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    // 设置底层图层的透明度层MaskView的透明度
                    findViewById<View>(R.id.view_alpha_mask).alpha = slideOffset * BG_ALPHA
                }
            }
        )

        bottomView.setOnClickListener {
            Toast.makeText(this, "bottom click", Toast.LENGTH_SHORT).show()
        }
    }
}