package ifreecomm.quickindexdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import ifreecomm.quickindexdemo.util.ScreenUtils;

/**
 * 触摸View时，获取触摸的是那个字母
 * Created by IT小蔡 on 2018-5-15.
 */

public class QuickIndexBar extends View {

    private static final String TAG = "QuickIndexBar";
    private String[] indexArr = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};
    private Paint mPaint;
    private int measureWidth;
    private int measureHeight;
    private float textSize;
    private float textWidth;
    private float gridHeight;
    private int textHeight;
    private OnQuickIndexTouchListener onQuickIndexTouchListener;
    private int touchIndex = -1;

    public QuickIndexBar(Context context) {
        this(context, null);
    }

    public QuickIndexBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickIndexBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//设置抗锯齿的画笔
        mPaint.setColor(Color.BLACK);
        textSize = ScreenUtils.dpToPx(getContext(), 16);
        mPaint.setTextSize(textSize);
        mPaint.setTextAlign(Paint.Align.CENTER);//设置文本起点是文字边框的底边中心
        //1. 计算文字宽度
        textWidth = mPaint.measureText(indexArr[0]);
        Rect textRect = new Rect();
        mPaint.getTextBounds(indexArr[0], 0, 1, textRect);
        textWidth = textRect.width();
        textHeight = textRect.height();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            if (widthSize < textWidth) {
                width = (int) textWidth; //设置控件最小宽度
            } else {
                width = widthSize;
            }
        }else if(widthMode == MeasureSpec.AT_MOST){
            width = (int) textWidth; //设置控件最小宽度
        }
        setMeasuredDimension(width, getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    /**
     * onMeasure执行完会回调，一般这在方法中去过去view的宽高
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        measureWidth = getMeasuredWidth();
        measureHeight = getMeasuredHeight();
        gridHeight = measureHeight * 1.0f / 26;//得到格子的高度（字母高度均分）
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < indexArr.length; i++) {
            if (i == touchIndex) {
                mPaint.setColor(Color.BLUE);
                canvas.drawText(indexArr[i], measureWidth / 2, textHeight * 1.0f / 2 + (i + 0.5f) * gridHeight, mPaint);
                mPaint.setColor(Color.BLACK);
            } else {
                canvas.drawText(indexArr[i], measureWidth / 2, textHeight * 1.0f / 2 + (i + 0.5f) * gridHeight, mPaint);
            }

        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

            case MotionEvent.ACTION_MOVE:
//                for (int i = 0; i < indexArr.length; i++) {
//                    if (y <= (i + 1) * gridHeight) {
//                        if (touchIndex != i) {
//                            Log.e(TAG, indexArr[i]);
//                            touchIndex = i;
//                            invalidate();
//                            if (onQuickIndexTouchListener != null) {
//                                onQuickIndexTouchListener.onTouchIndex(indexArr[i]);
//                            }
//                        }
//
//                        break;
//                    }
//                }
                int index = (int) (y / gridHeight);
                if (touchIndex != index && index < 26) {
                    setBackgroundColor(Color.parseColor("#DBD9D8"));
                    Log.e(TAG, indexArr[index]);
                    touchIndex = index;
                    invalidate();
                    if (onQuickIndexTouchListener != null) {
                        onQuickIndexTouchListener.onTouchIndex(indexArr[index]);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                if (touchIndex != -1) {
                    touchIndex = -1;
                    invalidate();
                    if (onQuickIndexTouchListener != null) {
                        setBackgroundColor(Color.parseColor("#FFFFFF"));
                        onQuickIndexTouchListener.quickCancal();
                    }
                }

                break;

        }
        return true;
    }

    public interface OnQuickIndexTouchListener {
        void onTouchIndex(String letter);

        void quickCancal();
    }

    public void setOnQuickIndexTouchListener(OnQuickIndexTouchListener onQuickIndexTouchListener) {
        this.onQuickIndexTouchListener = onQuickIndexTouchListener;
    }
}
