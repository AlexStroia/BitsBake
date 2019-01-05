package co.alexdev.bitsbake.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import co.alexdev.bitsbake.utils.BitsBakeUtils;

public class ColorfulButton extends View {

    private Paint mPaint;
    private Rect mRect;

    public ColorfulButton(Context context) {
        super(context);
        init(null);
    }

    public ColorfulButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ColorfulButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public ColorfulButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attributeSet) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRect = new Rect();
        mPaint.setColor(Color.BLACK);
    }

    private void setRectValues() {
        //Todo mRectTop,Left,Right,Bottom
    }

    public void generateColor() {
        mPaint.setColor(BitsBakeUtils.generateRandomColor());
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(200,200,100,mPaint);
    }
}
