package com.example.testcustomview.mianshi

import android.util.Log

class Zuoyebang {

    // 合并两个有序数组
    private val nums1 = arrayOf(1, 2, 3, 4)
    private val nums2 = arrayOf(2, 4, 6)
    fun mergeOrderArray() {
        var p1 = 0
        var p2 = 0
        val result = IntArray(nums1.size + nums2.size)
        var cur = 0

        while (p1 < nums1.size || p2 < nums2.size) {
            cur = if (p1 == nums1.size) {
                nums2[p2++]
            } else if (p2 == nums2.size) {
                nums1[p1++]
            } else if (nums1[p1] < nums2[p2]) {
                nums1[p1++]
            } else {
                nums2[p2++]
            }
            result[p1 + p2 -1] = cur
        }

        var str = ""
        result.forEach {
            str += "$it "
        }
        Log.d("Zuoyebang:", str)
    }
}