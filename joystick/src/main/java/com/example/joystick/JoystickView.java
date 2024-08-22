package com.example.joystick;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class JoystickView extends View implements Runnable {
    private final Paint basePaint = new Paint();
    private final Paint stickPaint = new Paint();
    private int baseColor;
    private int stickColor;
    private OnMoveListener moveListener;
    private boolean useSpring = true;
    private Thread mThread = new Thread(this);
    private int moveUpdateInterval = DEFAULT_UPDATE_INTERVAL;

    private float mPosX = 0.0f;
    private float mPosY = 0.0f;

    private float mCenterX = 0.0f;
    private float mCenterY = 0.0f;

    private float stickRatio = 0.0f;
    private float baseRatio = 0.0f;

    private float stickRadius = 0.0f;
    private float baseRadius = 0.0f;

    public JoystickView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Joystick,
                0, 0);

        try {
            baseColor = a.getColor(R.styleable.Joystick_joystickBaseColor, Color.YELLOW);
            stickColor = a.getColor(R.styleable.Joystick_joystickStickColor, Color.BLUE);
            stickRatio = a.getFraction(R.styleable.Joystick_joystickStickRatio, 1, 1, 0.25f);
            baseRatio = a.getFraction(R.styleable.Joystick_joystickBaseRatio, 1, 1, 0.75f);
            useSpring = a.getBoolean(R.styleable.Joystick_joystickUseSpring, true);
        } finally {
            a.recycle();
        }

        basePaint.setAntiAlias(true);
        basePaint.setColor(baseColor);
        basePaint.setStyle(Paint.Style.FILL);

        stickPaint.setAntiAlias(true);
        stickPaint.setColor(stickColor);
        stickPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPosX = getWidth() / 2.0f;
        mPosY = getWidth() / 2.0f;
        mCenterX = mPosX;
        mCenterY = mPosY;

        int d = Math.min(w, h);
        stickRadius = d / 2.0f * stickRatio;
        baseRadius = d / 2.0f * baseRatio;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mPosX = event.getX();
        mPosY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mThread.isAlive()) mThread.interrupt();
                mThread = new Thread(this);
                mThread.start();
                if (moveListener != null) moveListener.onMove(getAngle(), getStrength());
                break;
            case MotionEvent.ACTION_UP:
                mThread.interrupt();
                if (useSpring) {
                    mPosX = mCenterX;
                    mPosY = mCenterY;
                    //if (moveListener != null) moveListener.onMove(getAngle(), getStrength()); // angle,strength 값 컨트롤시에
                    if (moveListener != null) moveListener.onMove((int)mPosX, (int)mPosY);
                }
                break;
        }

        float length = (float) Math.sqrt(Math.pow(mPosX - mCenterX, 2) + Math.pow(mPosY - mCenterY, 2));

        if (length > baseRadius) {
            mPosX = (mPosX - mCenterX) * baseRadius / length + mCenterX;
            mPosY = (mPosY - mCenterY) * baseRadius / length + mCenterY;
        }

        invalidate();
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2.0f, getWidth() / 2.0f, baseRadius, basePaint);
        canvas.drawCircle(mPosX, mPosY, stickRadius, stickPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int d = Math.min(measure(widthMeasureSpec), measure(heightMeasureSpec));
        setMeasuredDimension(d, d);
    }

    private int measure(int measureSpec) {
        if (MeasureSpec.getMode(measureSpec) == MeasureSpec.UNSPECIFIED) {
            return DEFAULT_SIZE;
        } else {
            return MeasureSpec.getSize(measureSpec);
        }
    }

    private int getAngle() {
        float xx = mPosX - mCenterX;
        float yy = mCenterY - mPosY;
        int angle = (int) Math.toDegrees(Math.atan2(yy, xx));
        return angle < 0 ? angle + 360 : angle;
    }

    private int getStrength() {
        float length = (float) Math.sqrt(Math.pow(mPosX - mCenterX, 2) + Math.pow(mPosY - mCenterY, 2));
        return (int) (length / baseRadius * 100);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            post(() -> {
                //if (moveListener != null) moveListener.onMove(getAngle(), getStrength()); // angle,strength 값 컨트롤시에 사용시 원점도 같이수정
                if (moveListener != null) moveListener.onMove((int)mPosX, (int)mPosY);
            });
            try {
                Thread.sleep(moveUpdateInterval);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void setOnMoveListener(OnMoveListener listener, int intervalMs) {
        moveListener = listener;
        moveUpdateInterval = intervalMs;
    }

    public void setOnMoveListener(OnMoveListener listener) {
        setOnMoveListener(listener, DEFAULT_UPDATE_INTERVAL);
    }

    public interface OnMoveListener {
        void onMove(int x, int y);
    }

    private static final int DEFAULT_SIZE = 200;
    private static final int DEFAULT_UPDATE_INTERVAL = 50;
}
