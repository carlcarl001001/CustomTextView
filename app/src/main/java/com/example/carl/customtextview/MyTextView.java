package com.example.carl.customtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.icu.util.Measure;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyTextView extends View {
    private String mText;
    private int mTextSize=15;
    private int mTextColor = Color.BLACK;
    private Paint mPaint;
    private String TAG="chen";

    // 在代码里new时调用
    public MyTextView(Context context) {
        //super(context);
        this(context,null);//使得代码创建时，也进入第二个构造函数
    }
    //在布局layout中使用，没使用style时调用
    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        //super(context, attrs);
        this(context,attrs,0);
    }
    //在布局layout中使用，使用style时调用
    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.MyTextView);
        mText = array.getString(R.styleable.MyTextView_cText);
        mTextColor = array.getColor(R.styleable.MyTextView_cTextColor,mTextColor);
        mTextSize = array.getDimensionPixelSize(R.styleable.MyTextView_cTextSize,sp2px(mTextSize));
        //回收
        array.recycle();
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);//
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //布局的宽高都时由这个返回指定
        //指定控件的宽高，需要测量
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //1.确定的值，这个时候不需要计算，给多少是多少
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //2.给的是wrap_content需要计算
        if (widthMode==MeasureSpec.AT_MOST){
            Rect bounds = new Rect();
            mPaint.getTextBounds(mText,0,mText.length(),bounds);
            width = bounds.width()+getPaddingLeft()+getPaddingRight();
        }
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode==MeasureSpec.AT_MOST){
            Rect bounds = new Rect();
            mPaint.getTextBounds(mText,0,mText.length(),bounds);
            height = bounds.height()+getPaddingTop()+getPaddingBottom();
        }
        //设置控件的宽高
        setMeasuredDimension(width,height);



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画文章 text x y paint
        //x 起始位置
        //y 基线
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        //top 是一个负值 bottom是一给正值 top bottom是baseLine到文字底部的距离(正值)
        Log.i(TAG,"fontMetricsInt.top:"+fontMetricsInt.top);
        Log.i(TAG,"fontMetricsInt.bottom:"+fontMetricsInt.bottom);
        Log.i(TAG,"getHeight():"+getHeight());
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2 - fontMetricsInt.bottom;
        int baseLine = getHeight()/2+dy;
        int x =getPaddingLeft();
        canvas.drawText(mText,x,baseLine,mPaint);
    }
    private int sp2px(int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,
                getResources().getDisplayMetrics());
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
