package com.example.testcustomview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class XfermodeView extends View {

    private Bitmap dstBmp, srcBmp;
    private Paint paint;

    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        srcBmp = makeSrc();
        dstBmp = makeDst();
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);

        int layerId = canvas.saveLayer(0, 0, 800, 800, paint);
        canvas.drawBitmap(dstBmp, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcBmp, 200, 200, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    //创建一张圆形图片
    private Bitmap makeDst() {
        Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0Xffffcc44);
        canvas.drawOval(0, 0, 400, 400, paint);
        return bitmap;
    }

    private Bitmap makeSrc() {
        Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xff66aaff);
        canvas.drawRect(0, 0, 400, 400, paint);
        return bitmap;
    }
}
