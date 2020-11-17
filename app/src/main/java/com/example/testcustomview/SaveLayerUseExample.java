package com.example.testcustomview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SaveLayerUseExample extends View {

    private Paint paint, paint2;
    private Bitmap bitmap;

    public SaveLayerUseExample(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint2 = new Paint();
        paint2.setColor(Color.GREEN);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_work_nangua);
    }

    public SaveLayerUseExample(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), paint, Canvas.ALL_SAVE_FLAG);
        canvas.skew(1.732f, 0);
        canvas.drawRect(0, 0, 150, 160, paint);
        canvas.restoreToCount(layerId);
        canvas.drawRect(new Rect(0, 0, 120, 150), paint2);
    }
}
