package com.example.testcustomview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class TextViewGroup extends LinearLayout {


    public TextViewGroup(Context context) {
        super(context);
    }

    public TextViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("rjqtestevent", "TextViewGroup onInterceptTouchEvent ACTION_DOWN");
            return true;
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            Log.d("rjqtestevent", "TextViewGroup onInterceptTouchEvent ACTION_MOVE");
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("rjqtestevent", "TextViewGroup dispatchTouchEvent ACTION_DOWN");
            case MotionEvent.ACTION_MOVE:
                Log.d("rjqtestevent", "TextViewGroup dispatchTouchEvent ACTION_MOVE");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("rjqtestevent", "TextViewGroup onTouchEvent ACTION_DOWN");
                return false;
            case MotionEvent.ACTION_MOVE:
                Log.d("rjqtestevent", "TextViewGroup onTouchEvent ACTION_MOVE");
                break;
        }
        return super.onTouchEvent(event);
    }
}
