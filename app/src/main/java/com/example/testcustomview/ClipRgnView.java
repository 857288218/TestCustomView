package com.example.testcustomview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class ClipRgnView extends View {

    private Path path;
    private Bitmap bitmap;
    private int clipWidth = 0;
    private int width;
    private int height;
    private static final int CLIP_HEIGHT = 30;

    public ClipRgnView(Context context) {
        super(context);
        init();
    }

    public ClipRgnView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClipRgnView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_work_mudan);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        int i = 0;
        while (i * CLIP_HEIGHT <= height) {
            if (i % 2 == 0) {
                path.addRect(new RectF(0, i * CLIP_HEIGHT, clipWidth, (i + 1) * CLIP_HEIGHT), Path.Direction.CW);
            } else {
                path.addRect(new RectF(width - clipWidth, i * CLIP_HEIGHT, width, (i + 1) * CLIP_HEIGHT), Path.Direction.CW);
            }
            i++;
        }
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, 0, 0, new Paint());
        if (clipWidth > width) {
            return;
        }
        clipWidth += 5;
        invalidate();
    }
}
