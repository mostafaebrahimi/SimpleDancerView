package com.meandme.simpledancerview;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import java.util.Random;

public class DancerView extends View {
    private Paint paint;
    private Paint canvasPaint;
    private double rad=0,deg = 0;
    private boolean reverse = false;
    private Random rand = new Random();
    public DancerView(Context context) {
        super(context);
        init();
    }

    public DancerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DancerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DancerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init(){
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        canvasPaint = new Paint();
        canvasPaint.setStyle(Paint.Style.FILL);
        canvasPaint.setColor(Color.BLACK);
        startAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPaint(canvasPaint);
        int i=0,j=0;
        int radG = 0;
        deg = 0;

        while (i<3600 && deg<360) {
            int red = rand.nextInt(255);
            int green = rand.nextInt(255);
            int blue = rand.nextInt(255);
            paint.setARGB(255,red,green,blue);

            canvas.drawCircle((float) (Math.cos(deg) * radG)+getWidth()/2
                    , (float) (Math.sin(deg) * radG) + getHeight()/2, 8, paint);
            if(reverse)
                j++;
            deg += rad;
            radG += 2;
            i++;
        }
    }

    public void startAnimation(){
        PropertyValuesHolder degree = PropertyValuesHolder.ofFloat("deg",1f,0.5f);
        PropertyValuesHolder radius = PropertyValuesHolder.ofFloat("rad",0.5f,1f);
        PropertyValuesHolder col = PropertyValuesHolder.ofInt("color",0,255);
        PropertyValuesHolder rgb = PropertyValuesHolder.ofFloat("rgb",0.0f,1.0f);
        ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(degree,radius,col,rgb);

        valueAnimator.setDuration(15000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if(reverse)
                    rad = (float) valueAnimator.getAnimatedValue("deg");
                else rad = (float) valueAnimator.getAnimatedValue("rad");
                invalidate();
            }
        });
        valueAnimator.start();
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                reverse = !reverse;
            }
        });
    }
}
