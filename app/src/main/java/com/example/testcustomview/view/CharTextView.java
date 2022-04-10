package com.example.testcustomview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class CharTextView extends AppCompatTextView {

    public CharTextView(@NonNull Context context) {
        super(context);
    }

    public CharTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CharTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCharText(Character charText) {
        setText(String.valueOf(charText));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("rjqinvalidate", "CharTextView onDraw");
        super.onDraw(canvas);
    }
}
