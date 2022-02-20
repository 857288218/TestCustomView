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
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("rjqtestevent", "TestBtn onTouchEvent ACTION_DOWN");
//                return false;
            case MotionEvent.ACTION_MOVE:
                Log.d("rjqtestevent", "TestBtn onTouchEvent ACTION_MOVE");
                break;
        }
        return false;
    }
}
