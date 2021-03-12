package com.example.testcustomview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

//放大镜效果
public class TelescopeView2 extends View {

    private Bitmap bitmap;
    private ShapeDrawable shapeDrawable;
    //放大镜的半径
    private static final int RADIUS = 150;
    //放大倍数
    private static final int FACTOR = 2;
    private final Matrix matrix = new Matrix();

    public TelescopeView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        //设置Shader绘制的起始位置
        matrix.setTranslate(RADIUS - FACTOR * x, RADIUS - FACTOR * y);
        shapeDrawable.getPaint().getShader().setLocalMatrix(matrix);
        shapeDrawable.setBounds(x - RADIUS, y - RADIUS, x + RADIUS, y + RADIUS);
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap == null) {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_work_nangua);
            bitmap = Bitmap.createScaledBitmap(bmp, getWidth(), getHeight(), false);

            BitmapShader shader = new BitmapShader(Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * FACTOR, bitmap.getHeight() * FACTOR, true),
                    Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            shapeDrawable = new ShapeDrawable(new OvalShape());
            shapeDrawable.getPaint().setShader(shader);
            shapeDrawable.setBounds(0, 0, RADIUS * 2, RADIUS * 2);
        }
        canvas.drawBitmap(bitmap, 0, 0, null);
        shapeDrawable.draw(canvas);
    }
}
