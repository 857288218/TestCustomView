package com.example.testcustomview.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View.OnClickListener
import android.view.ViewTreeObserver
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.animation.addListener
import com.example.testcustomview.R
import com.example.testcustomview.util.getStaticLayout

// 可展开折叠的文本控件
class ExpandableTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs) {

    private var tipCollapse: String? = "收起"
    private var tipExpand: String? = "展开全文"
    private val mTvContent: TextView?
    private var mLineSpaceExtra = 0f
    private val mTvExpand: TextView

    // 是否折叠的标记
    private var mIsExpand = false
    private var mOriginText: CharSequence? = "" // 原始的文本,包括标签文本
    private var signName = "" // 标签文本
    private var mExpandText: CharSequence = "" // 展开的文本
    private var mCollapseText: CharSequence = "" // 收起的文本
    private var mExpandable = true // 是否支持点击展开
    private var mContentTextSize = 0
    private var mContentColor = 0
    private var mTipsColor = 0
    private var mMaxLines = 2 // 折叠后显示的行数，默认为3行
    private var mPosition = ALIGN_RIGHT
    private var mCollapseDrawable: Drawable? = null
    private var mExpandDrawable: Drawable? = null
    private var performedByUser = false
    private var mCancelAnim = false
    private var mTextTotalWidth = 0
    private val signNameSpan = RadiusStrokeBackgroundSpan(
        ContextCompat.getColor(context, R.color.purple_300),
        context.dp2px(0.5f).toFloat(),
        ContextCompat.getColor(context, R.color.purple_300),
        sp2px(context, 8f).toFloat(),
        context.dp2px(1f).toFloat(),
        context.dp2px(4f),
        context.dp2px(2f),
        context.dp2px(8f)
    )

    /**
     * 展开折叠监听器
     */
    private var toggleListener: OnToggleListener? = null
    private var mMeasured = false
    private var mTipMarginTop = 0
    private var mCollapseHeight = 0
    private var mExpandHeight = 0
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        // 如果不支持展开折叠，那么layout截取事件并响应事件
        return !mExpandable
    }

    private fun updateExpandArrowAndPosition(position: Int) {
        val params = LayoutParams(mTvExpand.layoutParams)
        when (position) {
            BOTTOM_START -> {
                params.topMargin = mTipMarginTop
                params.addRule(BELOW, R.id.tv_content)
                params.addRule(ALIGN_PARENT_LEFT, TRUE)
            }
            BOTTOM_CENTER -> {
                params.topMargin = mTipMarginTop
                params.addRule(BELOW, R.id.tv_content)
                params.addRule(CENTER_HORIZONTAL, TRUE)
            }
            BOTTOM_END -> {
                params.topMargin = mTipMarginTop
                params.addRule(BELOW, R.id.tv_content)
                params.addRule(ALIGN_PARENT_RIGHT, TRUE)
            }
            ALIGN_RIGHT -> {
                params.addRule(ALIGN_BOTTOM, R.id.tv_content)
                params.addRule(ALIGN_PARENT_RIGHT, TRUE)
            }
            else -> params.addRule(BELOW, R.id.tv_content)
        }
        mTvExpand.layoutParams = params
    }

    private fun initText(text: CharSequence?) {
        // 根据指定的折叠行数获取折叠文本
        mOriginText = "$signName$text"
        mTvContent!!.text = mOriginText
        if (!mMeasured) {
            if (viewTreeObserver != null) {
                viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        viewTreeObserver.removeOnPreDrawListener(this)
                        // 获取控件尺寸
                        if (width != 0) {
                            mTextTotalWidth = width - paddingLeft - paddingRight
                            mMeasured = true
                            toggleText()
                        }
                        return true
                    }
                })
            }
        }
    }

    /**
     * 指定显示文本,并指定赋值后展开还是折叠的状态
     *
     * @param text  显示文本
     * @param close true代表默认收起,false代表默认展开
     */
    fun setText(text: CharSequence?, close: Boolean) {
        mIsExpand = !close
        performedByUser = true
        initText(text)
    }

    fun setSignName(signName: String?) {
        this.signName = signName ?: ""
    }

    fun setExpandable(expandable: Boolean) {
        mExpandable = expandable
    }

    /**
     * 设置展开提示语，在setText方法之前调用
     *
     * @param label 展开提示语，如“展开”
     */
    fun setExpandLabel(label: String?) {
        if (!TextUtils.isEmpty(label)) {
            tipExpand = label
        }
    }

    /**
     * 设置折叠提示语，在setText方法之前调用
     *
     * @param label 折叠提示语，如“收起”
     */
    fun seCollapseLabel(label: String?) {
        if (!TextUtils.isEmpty(label)) {
            tipCollapse = label
        }
    }

    /**
     * 设置展开提示图标，在setText方法之前调用
     *
     * @param drawable 展开图标
     */
    fun setExpandDrawable(drawable: Drawable?) {
        if (drawable != null) {
            mExpandDrawable = drawable
            mExpandDrawable!!.setBounds(0, 0, mContentTextSize, mContentTextSize)
        }
    }

    /**
     * 设置折叠提示图标，在setText方法之前调用
     *
     * @param drawable 折叠图标
     */
    fun setCollapseDrawable(drawable: Drawable?) {
        if (drawable != null) {
            mCollapseDrawable = drawable
            mCollapseDrawable!!.setBounds(0, 0, mContentTextSize, mContentTextSize)
        }
    }

    @SuppressLint("AvoidUsageApiCheck")
    fun setExpandableTextViewClick(click: OnClickListener?) {
        mTvContent?.setOnClickListener(click)
    }

    fun setExpandableTextViewLongClick(longClick: OnLongClickListener?) {
        mTvContent?.setOnLongClickListener(longClick)
    }

    var text: CharSequence?
        get() = mOriginText
        set(text) {
            if (TextUtils.isEmpty(text)) return
            mIsExpand = !mIsExpand
            performedByUser = false
            initText(text)
        }

    /**
     * 展开或收起文本
     */
    fun toggleText() {
        // 修改展开折叠标志
        mIsExpand = !mIsExpand
        val canCollapse = formatText(mOriginText)
        if (!canCollapse) {
            mTvExpand.visibility = GONE
            addSignName(mOriginText)
            return
        } else {
            mTvExpand.visibility = VISIBLE
        }
        if (!performedByUser || !mExpandable) {
            addSignName(mCollapseText)
            mIsExpand = false
            if (mExpandDrawable != null) {
                mTvExpand.setCompoundDrawablesWithIntrinsicBounds(null, null, mExpandDrawable, null)
            }
            mTvExpand.text = tipExpand
            return
        }
        if (mIsExpand) {
            mTvContent!!.maxLines = Int.MAX_VALUE
            addSignName(mExpandText)
            if (!mCancelAnim) {
                // 展开动画
                val anim = ValueAnimator.ofInt(mCollapseHeight, mExpandHeight).setDuration(100)
                anim.addUpdateListener { animation ->
                    val params = mTvContent.layoutParams
                    val h = (animation?.animatedValue as? Int) ?: 0
                    params.height = h
                    if (h >= mExpandHeight) {
                        params.height = LayoutParams.WRAP_CONTENT
                    }
                    mTvContent.layoutParams = params
                }
                anim.addListener(onEnd = {
                    mTvExpand.visibility = VISIBLE
                })
                anim.start()
                mTvExpand.visibility = GONE
            }
            if (mCollapseDrawable != null) {
                mTvExpand.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    mCollapseDrawable,
                    null
                )
            } else {
                mTvExpand.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }
            mTvExpand.text = tipCollapse
            toggleListener?.onToggle(true)
        } else {
            if (!mCancelAnim) {
                // 收起动画
                val anim = ValueAnimator.ofInt(mExpandHeight, mCollapseHeight).setDuration(100)
                anim.addUpdateListener { animation ->
                    val params = mTvContent!!.layoutParams
                    val h = (animation?.animatedValue as? Int) ?: 0
                    params.height = h
                    if (h <= mCollapseHeight) {
                        params.height = LayoutParams.WRAP_CONTENT
                    }
                    mTvContent.layoutParams = params
                }
                anim.addListener(onEnd = {
                    // maxLines和text需要动画结束后再设置，否则收起动画效果看不出来
                    mTvContent!!.maxLines = mMaxLines
                    addSignName(mCollapseText)
                    mTvExpand.visibility = VISIBLE
                })
                anim.start()
                mTvExpand.visibility = GONE
            }
            if (mExpandDrawable != null) {
                mTvExpand.setCompoundDrawablesWithIntrinsicBounds(null, null, mExpandDrawable, null)
            } else {
                mTvExpand.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }
            mTvExpand.text = tipExpand
            toggleListener?.onToggle(false)
        }
    }

    private fun addSignName(content: CharSequence?) {
        val ss = getSignNameContentSp(content)
        if (ss != null) mTvContent?.text = ss else mTvContent?.text = content
    }

    private fun getSignNameContentSp(content: CharSequence?): SpannableString? {
        return if (signName.isNotEmpty()) {
            val ss = SpannableString(content)
            val start = content?.indexOf(signName) ?: 0
            val end = start + signName.length
            ss.setSpan(signNameSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            ss
        } else {
            null
        }
    }

    /**
     * 格式化折叠展开时的文本
     *
     * @return 是否超出给定行数
     */
    private fun formatText(text: CharSequence?): Boolean {
        val paint = mTvContent!!.paint
        val staticLayout = mTvContent.getStaticLayout(getSignNameContentSp(text) ?: text, mTextTotalWidth)
        val lineCount = staticLayout.lineCount
        return if (lineCount <= mMaxLines) {
            // 不足最大行数，直接设置文本
            // 少于最小展示行数，不再展示更多相关布局
            mTvExpand.visibility = GONE
            mCollapseText = text ?: ""
            mExpandText = text ?: ""
            false
        } else {
            // 超出最大行数
            mTvExpand.visibility = VISIBLE
            // #######1.获取折叠后的文本#######
            var lastLineStartIndex = staticLayout.getLineStart(mMaxLines - 1)
            var lastLineEndIndex = staticLayout.getLineEnd(mMaxLines - 1)
            if (lastLineStartIndex < 0) {
                lastLineStartIndex = 0
            }
            if (lastLineEndIndex > (text?.length ?: 0)) {
                lastLineEndIndex = text!!.length
            }
            if (lastLineStartIndex > lastLineEndIndex) {
                lastLineStartIndex = lastLineEndIndex
            }
            var lastLineText = text?.subSequence(lastLineStartIndex, lastLineEndIndex)
            var lastLineWidth = paint.measureText(lastLineText, 0, lastLineText?.length ?: 0)
            // 计算后缀的宽度
            var imgWidth = 0
            if (mExpandDrawable != null) {
                imgWidth = mExpandDrawable!!.intrinsicWidth
            }
            var expandedTextWidth = if (mPosition == ALIGN_RIGHT) {
                paint.measureText("$ELLIPSE$tipExpand").toInt() + imgWidth
            } else {
                paint.measureText(ELLIPSE).toInt()
            }
            // 如果大于屏幕宽度则需要减去部分字符
            if (lastLineWidth + expandedTextWidth >= mTextTotalWidth) {
                val cutCount = paint.breakText(
                    mOriginText,
                    lastLineStartIndex,
                    lastLineEndIndex,
                    false,
                    expandedTextWidth.toFloat(),
                    null
                )
                lastLineEndIndex -= cutCount
            }
            val appd = StringBuilder(ELLIPSE)
            // 再测量一下,有可能放置不下
            lastLineEndIndex =
                ensureLastLineEndIndex(paint, lastLineStartIndex, lastLineEndIndex, imgWidth, appd)
            // 因设置的文本可能是带有样式的文本，如SpannableStringBuilder，所以根据计算的字符数从原始文本中截取
            val spannable = SpannableStringBuilder()
            // 截取文本，还是因为原始文本的样式原因不能直接使用paragraphs中的文本
            val ellipsizeText = mOriginText!!.subSequence(0, lastLineEndIndex)
            spannable.append(ellipsizeText)
            spannable.append(ELLIPSE)
            mCollapseText = spannable
            mCollapseHeight =
                mTvContent.getStaticLayout(getSignNameContentSp(mCollapseText) ?: mCollapseText, mTextTotalWidth).height
            // #######2.获取展开后的字符串#######
            mExpandText = text ?: ""
            if (mPosition == ALIGN_RIGHT) {
                lastLineStartIndex = staticLayout.getLineStart(lineCount - 1)
                lastLineEndIndex = staticLayout.getLineEnd(lineCount - 1)
                if (lastLineStartIndex < 0) {
                    lastLineStartIndex = 0
                }
                if (lastLineEndIndex > (text?.length ?: 0)) {
                    lastLineEndIndex = text!!.length
                }
                if (lastLineStartIndex > lastLineEndIndex) {
                    lastLineStartIndex = lastLineEndIndex
                }
                lastLineText = text?.subSequence(lastLineStartIndex, lastLineEndIndex)
                lastLineWidth = paint.measureText(lastLineText, 0, lastLineText?.length ?: 0)
                val space = "  "
                // 计算后缀的宽度
                expandedTextWidth = paint.measureText(space + tipCollapse).toInt() + 1
                imgWidth = 0
                if (mCollapseDrawable != null) {
                    imgWidth = mCollapseDrawable!!.intrinsicWidth
                }
                expandedTextWidth += imgWidth
                // 如果大于屏幕宽度则需要换行
                if (lastLineWidth + expandedTextWidth > mTextTotalWidth) {
                    val stringBuilder = SpannableStringBuilder(text)
                    mExpandText = stringBuilder.append("\n")
                } else {
                    val param = mTvExpand.layoutParams as MarginLayoutParams
                    if (mIsExpand) {
                        // "收起"紧挨着文字显示在后面
                        param.rightMargin =
                            (mTextTotalWidth - lastLineWidth - expandedTextWidth).toInt()
                    } else {
                        param.rightMargin = 0
                    }
                    mTvExpand.layoutParams = param
                }
            }
            mExpandHeight =
                mTvContent.getStaticLayout(getSignNameContentSp(mExpandText) ?: mExpandText, mTextTotalWidth).height
            true
        }
    }

    private fun ensureLastLineEndIndex(
        paint: TextPaint,
        lastLineStartIndex: Int,
        lastLineEndIndex: Int,
        imgWidth: Int,
        appd: StringBuilder
    ): Int {
        var mLastLineEndIndex = lastLineEndIndex
        val originStr = mOriginText.toString()
        val lastLineWidth = paint.measureText(
            originStr.substring(
                lastLineStartIndex,
                mLastLineEndIndex
            ) + "" + ELLIPSE + "" + tipExpand
        ) + imgWidth
        if (lastLineWidth > mTextTotalWidth) {
            // 再减掉一个字
            mLastLineEndIndex--
            // 添加点占位
            val spaceWidth = paint.measureText(".").toInt()
            val spaceCount = (
                    (
                            mTextTotalWidth - paint.measureText(
                                originStr.substring(
                                    lastLineStartIndex,
                                    mLastLineEndIndex
                                ) + "" + ELLIPSE + "" + tipExpand
                            ) - imgWidth
                            ) / spaceWidth
                    ).toInt()
            repeat(spaceCount) {
                appd.append(".")
            }
        }
        return mLastLineEndIndex
    }

    fun setToggleListener(listener: OnToggleListener?) {
        toggleListener = listener
    }

    interface OnToggleListener {
        fun onToggle(expanded: Boolean)
    }

    companion object {
        private const val ELLIPSE = "..."
        private const val ALIGN_RIGHT = 0 // 控制按钮在文本右下角，与文本底部基线齐平
        private const val BOTTOM_START = 1 // 控制按钮在文本下方左侧
        private const val BOTTOM_CENTER = 2 // 控制按钮在文本下方中间
        private const val BOTTOM_END = 3 // 控制按钮在文本下方右侧侧

        const val TAG = "ExpandableTextView"
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView)
        mOriginText = typedArray.getString(R.styleable.ExpandableTextView_android_text)
        mLineSpaceExtra =
            typedArray.getDimension(R.styleable.ExpandableTextView_android_lineSpacingExtra, 0f)
        if (mLineSpaceExtra < 0) {
            mLineSpaceExtra = 0f
        }
        mExpandable = typedArray.getBoolean(R.styleable.ExpandableTextView_expandable, true)
        mTipMarginTop =
            typedArray.getDimensionPixelSize(R.styleable.ExpandableTextView_tipMarginTop, 0)
        mMaxLines = typedArray.getInteger(R.styleable.ExpandableTextView_collapseLines, mMaxLines)
        if (mMaxLines <= 0) {
            mMaxLines = 1
        }
        mContentColor =
            typedArray.getColor(R.styleable.ExpandableTextView_android_textColor, Color.WHITE)
        mTipsColor = typedArray.getColor(R.styleable.ExpandableTextView_tipsColor, Color.WHITE)
        mContentTextSize = typedArray.getDimensionPixelSize(
            R.styleable.ExpandableTextView_android_textSize,
            sp2px(context, 14f)
        )
        mPosition = typedArray.getInteger(R.styleable.ExpandableTextView_tipPosition, mPosition)
        mExpandDrawable = typedArray.getDrawable(R.styleable.ExpandableTextView_expandDrawable)
        if (mExpandDrawable != null) {
            mExpandDrawable!!.setBounds(0, 0, mContentTextSize, mContentTextSize)
        }
        mCollapseDrawable = typedArray.getDrawable(R.styleable.ExpandableTextView_collapseDrawable)
        if (mCollapseDrawable != null) {
            mCollapseDrawable!!.setBounds(0, 0, mContentTextSize, mContentTextSize)
        }
        val expandLabel = typedArray.getString(R.styleable.ExpandableTextView_expandTipLabel)
        if (!TextUtils.isEmpty(expandLabel)) {
            tipExpand = expandLabel
        }
        val collapseLabel = typedArray.getString(R.styleable.ExpandableTextView_collapseTipLabel)
        if (!TextUtils.isEmpty(collapseLabel)) {
            tipCollapse = collapseLabel
        }
        typedArray.recycle()
        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.layout_expandable_view, this, true)
        // 获取文本控件
        mTvContent = findViewById(R.id.tv_content)
        // 为了防止加载页面是出现抖动
        mTvContent.maxLines = mMaxLines
        mTvExpand = findViewById(R.id.tv_arrow)
        if (mLineSpaceExtra > 0) {
            mTvContent.setLineSpacing(mLineSpaceExtra, 1.0f)
        }
        mTvExpand.setTextColor(mTipsColor)
        mTvExpand.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContentTextSize.toFloat())
        mTvExpand.compoundDrawablePadding = 5
        if (mExpandDrawable != null) {
            mTvExpand.setCompoundDrawablesWithIntrinsicBounds(null, null, mExpandDrawable, null)
        }
        mTvContent.setTextColor(mContentColor)
        mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContentTextSize.toFloat())
        mTvExpand.text = tipExpand
        @SuppressLint("AvoidUsageApiCheck")
        if (mExpandable) {
            val clickListener = OnClickListener {
                performedByUser = true
                mCancelAnim = false
                toggleText()
            }
            // 默认点击文本也展开折叠
            mTvContent.setOnClickListener(clickListener)
            mTvExpand.setOnClickListener(clickListener)
        }
        if (!TextUtils.isEmpty(mOriginText)) text = mOriginText
        // 设置折叠展开标识控件的位置
        updateExpandArrowAndPosition(mPosition)
    }

    private fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }
}