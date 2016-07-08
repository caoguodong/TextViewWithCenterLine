package com.caoguodong.textviewwithcenterline;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by caoguodong on 16/7/7.
 */

public class TextViewWithCenterLine extends TextView {

    private int centerLineColor;
    private float centerLineWidth;
    private final int CENTERLINEWIDTH = 2;
    private boolean isShowingCenterLine = true;

    public TextViewWithCenterLine(Context context) {
        this(context, null);
    }

    public TextViewWithCenterLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewWithCenterLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TextViewWithCenterLine);
        centerLineWidth = CENTERLINEWIDTH;
        centerLineColor = ta.getColor(R.styleable.TextViewWithCenterLine_centerLineColor, getCurrentTextColor());
        centerLineWidth = ta.getDimension(R.styleable.TextViewWithCenterLine_centerLineWidth, CENTERLINEWIDTH);
        centerLineWidth = ta.getDimensionPixelSize(R.styleable.TextViewWithCenterLine_centerLineWidth, CENTERLINEWIDTH);
        isShowingCenterLine = ta.getBoolean(R.styleable.TextViewWithCenterLine_showCenterLine, true);
    }

    private void drawCenterLine(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(centerLineColor);
        paint.setStrokeWidth(centerLineWidth);
        paint.setTextSize(getTextSize());

        int lineY;
        int startX, endX;
        Rect lineRect = new Rect();
        Layout layout = getLayout();

        String text = getText().toString();

        final int layoutDirection = getLayoutDirection();
        final int absoluteGravity = Gravity.getAbsoluteGravity(getGravity(), layoutDirection);

        String[] lineText = new String[getLineCount()];
        int lastIndex = 0;
        int currentIndex;
        for (int i = 1; i < getLineCount(); i++) {
            currentIndex = layout.getOffsetForHorizontal(i, 0);
            lineText[i - 1] = text.substring(lastIndex, currentIndex);
            lastIndex = currentIndex;
        }

        lineText[lineText.length - 1] = text.substring(lastIndex);

        for (int i = 0; i < getLineCount(); i++) {
            if (getLineBounds(i, lineRect) != 0) {
                startX = lineRect.left;
                endX = lineRect.right;
                float fontWidth = paint.measureText(lineText[i]);
                switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
                    case Gravity.LEFT:
                    case Gravity.START:
                        endX = (int) (startX + fontWidth);
                        break;
                    case Gravity.CENTER_HORIZONTAL:
                    case Gravity.CENTER:
                        float lineSpace = (endX-startX-fontWidth)/2;
                        startX = (int) (startX + lineSpace);
                        endX = (int) (startX+fontWidth);
                        break;
                    case Gravity.RIGHT:
                    case Gravity.END:
                        startX = (int) (endX - fontWidth);
                        break;
                }

                lineY = (int) (lineRect.top + (getLineHeight() - getLineSpacingExtra()) / 2);
                drawLine(startX, endX, lineY, paint, canvas);
            }
        }
    }

    private final void drawLine(float startX, float endX, float lineY, Paint paint, Canvas canvas) {
        canvas.drawLine(startX,
                lineY,
                endX,
                lineY,
                paint);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isShowingCenterLine) {
            drawCenterLine(canvas);
        }
    }
}
