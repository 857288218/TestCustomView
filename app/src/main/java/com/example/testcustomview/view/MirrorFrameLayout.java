package com.example.testcustomview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MirrorFrameLayout extends FrameLayout {

    private boolean isMirror;

    public MirrorFrameLayout(@NonNull Context context) {
        super(context);
    }

    public MirrorFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MirrorFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 手势翻转
        if (isMirror) {
            ev.setLocation(this.getWidth() - ev.getX(),ev.getY());
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("rjqinvalidate", "MirrorFrameLayout onDraw");
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("rjqinvalidate", "MirrorFrameLayout onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d("rjqinvalidate", "MirrorFrameLayout onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.d("rjqinvalidate", "MirrorFrameLayout dispatchDraw");
        if (isMirror) {
            // 布局翻转
            canvas.scale(-1, 1, getWidth() / 2, getHeight() / 2);
        } else {
            canvas.scale(1, 1, getWidth() / 2, getHeight() / 2);
        }
        super.dispatchDraw(canvas);
    }

    public void setMirror(boolean mirror) {
        isMirror = mirror;
        invalidate();
    }
}

