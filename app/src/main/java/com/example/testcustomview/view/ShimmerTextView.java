package com.example.testcustomview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

//发光文字
public class ShimmerTextView extends AppCompatTextView {

    private int dx;
    private LinearGradient linearGradient;
    private Paint paint;
    private Matrix matrix;

    public ShimmerTextView(@NonNull Context context) {
        super(context);
    }

    public ShimmerTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = getPaint();
        matrix = new Matrix();
        int length = (int) paint.measureText(getText().toString());
        createAnimator(length);
        createLinearGradient(length);
    }

    public ShimmerTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        matrix.setTranslate(dx, 0);
        linearGradient.setLocalMatrix(matrix);
        paint.setShader(linearGradient);
        super.onDraw(canvas);
    }

    private void createAnimator(int length) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, length * 2);
        valueAnimator.addUpdateListener(animation -> {
            dx = (int) animation.getAnimatedValue();
            postInvalidate();
        });
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setDuration(2000);
        valueAnimator.start();
    }

    private void createLinearGradient(int length) {
        linearGradient = new LinearGradient(-length, 0, 0, 0, new int[]{getCurrentTextColor(), 0xff00ff00, getCurrentTextColor()},
                new float[]{0, 0.5f, 1}, Shader.TileMode.CLAMP);
    }
}
