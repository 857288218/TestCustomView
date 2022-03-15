package com.example.testcustomview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class TestBtn extends AppCompatButton {
    public TestBtn(@NonNull Context context) {
        super(context);
    }

    public TestBtn(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestBtn(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("rjqtestevent", "TestBtn dispatchTouchEvent ACTION_DOWN");
//            getParent().requestDisallowInterceptTouchEvent(true);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            Log.d("rjqtestevent", "TestBtn dispatchTouchEvent ACTION_MOVE");
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.d("rjqtestevent", "TestBtn dispatchTouchEvent ACTION_UP");
        } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
            Log.d("rjqtestevent", "TestBtn dispatchTouchEvent ACTION_CANCEL");
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("rjqtestevent", "TestBtn onTouchEvent ACTION_DOWN");
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.d("rjqtestevent", "TestBtn onTouchEvent ACTION_MOVE");
//                return true;
                break;
            case MotionEvent.ACTION_UP:
                Log.d("rjqtestevent", "TestBtn onTouchEvent ACTION_UP");
//                return true;
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("rjqtestevent", "TestBtn onTouchEvent ACTION_CANCEL");
//                return true;
                break;
        }
        return false;
    }
}
