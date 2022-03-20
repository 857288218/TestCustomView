package com.example.testcustomview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.testcustomview.R;

//望远镜效果
public class TelescopeView extends View {

    private Paint mPaint;
    private Bitmap mBitmap, mBitmapBg;
    private int mDx = -1, mDy = -1;
    private BitmapShader bitmapShader;

    public TelescopeView(Context context) {
        super(context);
    }

    public TelescopeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_work_nangua);
    }

    public TelescopeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmapBg == null) {
            //得到和控件宽高相等的bitmap
//            mBitmapBg = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
//            Canvas canvasBg = new Canvas(mBitmapBg);
//            canvasBg.drawBitmap(mBitmap, null, new Rect(0, 0, getWidth(), getHeight()), mPaint);
            Matrix matrix = new Matrix();
            matrix.setScale((float) getWidth() / mBitmap.getWidth(), (float) getHeight() / mBitmap.getHeight());
            bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            bitmapShader.setLocalMatrix(matrix);
        }
        if (mDx != -1 && mDy != -1) {
//            mPaint.setShader(new BitmapShader(mBitmapBg, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
            mPaint.setShader(bitmapShader);
            canvas.drawCircle(mDx, mDy, 150, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDx = (int) event.getX();
                mDy = (int) event.getY();
                invalidate();
                return true;        //down事件消费后后续事件才能传递过来，如果down事件不消费后续事件不会传递过来
            case MotionEvent.ACTION_MOVE:
                mDx = (int) event.getX();
                mDy = (int) event.getY();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mDx = -1;
                mDy = -1;
                break;
        }
        invalidate();
        return super.onTouchEvent(event);
    }
}
