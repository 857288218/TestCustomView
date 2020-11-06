package com.example.testcustomview;

import android.content.Context;
import android.util.AttributeSet;

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
}
