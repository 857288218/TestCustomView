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
            return false;
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            Log.d("rjqtestevent", "TextViewGroup onInterceptTouchEvent ACTION_MOVE");
            return true;
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            Log.d("rjqtestevent", "TextViewGroup onInterceptTouchEvent ACTION_UP");
        } else if (ev.getAction() == MotionEvent.ACTION_CANCEL) {
            Log.d("rjqtestevent", "TextViewGroup onInterceptTouchEvent ACTION_CANCEL");
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("rjqtestevent", "TextViewGroup dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("rjqtestevent", "TextViewGroup dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("rjqtestevent", "TextViewGroup dispatchTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("rjqtestevent", "TextViewGroup dispatchTouchEvent ACTION_CANCEL");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("rjqtestevent", "TextViewGroup onTouchEvent ACTION_DOWN");
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.d("rjqtestevent", "TextViewGroup onTouchEvent ACTION_MOVE");
                return false;
//                break;
            case MotionEvent.ACTION_UP:
                Log.d("rjqtestevent", "TextViewGroup onTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("rjqtestevent", "TextViewGroup onTouchEvent ACTION_CANCEL");
                break;
        }
        return super.onTouchEvent(event);
    }
}
