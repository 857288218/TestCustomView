package com.example.testcustomview

import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import java.util.*

class SentenceTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr) {
    private var mText: String? = null
    private var mOnSentenceClickListener: OnSentenceClickListener? = null
    private var mSpannableString: SpannableString? = null
    private var mSelectedBackSpan: BackgroundColorSpan? = null
    private val selectedColor: Int
    private var sentenceList: ArrayList<Sentence>? = null
    private var currentHighlightSentence: Sentence? = null

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.SentenceTextView)
        selectedColor = ta.getColor(R.styleable.SentenceTextView_highlightSentenceBgColor, Color.BLUE)
        ta.recycle()
        movementMethod = LinkMovementMethod.getInstance()
        // 去掉点击ClickableSpan后的背景色
        highlightColor = ContextCompat.getColor(context, android.R.color.transparent)
    }

    // 将句子拼起来构建SpannableString显示
    fun setSentenceList(sentenceList: ArrayList<Sentence>) {
        this.sentenceList = sentenceList
        val sb = StringBuilder()
        for (sentence in sentenceList) {
            sb.append(sentence.onebest)
        }
        mText = sb.toString()
        mSpannableString = SpannableString(mText)
        dealSentence()
        text = mSpannableString
    }

    private fun dealSentence() {
        sentenceList?.let {
            var start = 0
            var end = 0
            for (sentence in sentenceList!!) {
                end += sentence.onebest?.length ?: 0
                mSpannableString?.setSpan(getClickableSpan(sentence), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                start += sentence.onebest?.length ?: 0
            }
        }
    }

    fun setHighlightSentence(sentence: Sentence?) {
        val positions = getSentencePosition(sentence)
        if (positions.size == 2) {
            if (mSelectedBackSpan == null) {
                mSelectedBackSpan = BackgroundColorSpan(selectedColor)
            } else {
                mSpannableString?.removeSpan(mSelectedBackSpan)
            }
            currentHighlightSentence = sentence
            mSpannableString?.setSpan(mSelectedBackSpan, positions[0], positions[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            text = mSpannableString
        }
    }

    fun removeHighlightSentence() {
        currentHighlightSentence = null
        if (mSelectedBackSpan != null) {
            mSpannableString?.removeSpan(mSelectedBackSpan)
            text = mSpannableString
        }
    }

    private fun getClickableSpan(sentence: Sentence): ClickableSpan {
        return object : ClickableSpan() {
            override fun onClick(widget: View) {
                if (sentence != currentHighlightSentence) {
                    setHighlightSentence(sentence)
                    mOnSentenceClickListener?.onClick(sentence)
                }
            }

            override fun updateDrawState(ds: TextPaint) {

            }
        }
    }

    private fun getSentencePosition(sentence: Sentence?): IntArray {
        val positions = IntArray(2)
        sentenceList?.let {
            if (sentence != null && sentenceList!!.contains(sentence)) {
                var start = 0
                for (s in sentenceList!!) {
                    if (s == sentence) {
                        break
                    }
                    start += s.onebest!!.length
                }
                val end = start + sentence.onebest!!.length
                positions[0] = start
                positions[1] = end
            }
        }
        return positions
    }

    fun setOnSentenceClickListener(listener: OnSentenceClickListener?) {
        mOnSentenceClickListener = listener
    }

    interface OnSentenceClickListener {
        fun onClick(sentence: Sentence?)
    }
}