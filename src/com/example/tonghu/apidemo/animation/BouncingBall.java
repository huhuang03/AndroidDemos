package com.example.tonghu.apidemo.animation;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

public class BouncingBall extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyAnimationView(this));
	}
	
	class MyAnimationView extends View {
        private static final int RED = 0xffFF8080;
        private static final int BLUE = 0xff8080FF;
        private List<ShapeHolder> balls = new ArrayList<ShapeHolder>();
		public MyAnimationView(Context context) {
			super(context);
			setBackgroundColor(Color.RED);
			ObjectAnimator oa = ObjectAnimator.ofInt(this, "backgroundColor", RED, BLUE);
			
			oa.setDuration(3000);
			oa.setEvaluator(new ArgbEvaluator());
			oa.setRepeatCount(ValueAnimator.INFINITE);
			oa.setRepeatMode(ValueAnimator.REVERSE);
			oa.start();
		}
		
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			if (event.getAction() != MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_MOVE) {
				return false;
			}
			ShapeHolder ball = addBall(event.getX(), event.getY());
			float startY = event.getY();
			float h = getHeight();
			float endY = getHeight() - 50f;
			float eventY = event.getY();
			int duration = (int) (500 * ((h - eventY) / h));
			ValueAnimator bounceAnim = ObjectAnimator.ofFloat(ball, "y", startY, endY);
			bounceAnim.setDuration(duration);
			bounceAnim.setInterpolator(new AccelerateInterpolator());
			ValueAnimator holderAnim = ObjectAnimator.ofFloat(ball, "x", ball.getX(), ball.getX() - 25);
			holderAnim.setDuration(duration / 4);
			holderAnim.setRepeatCount(1);
			holderAnim.setRepeatMode(ValueAnimator.REVERSE);
			ValueAnimator squashAnim2 = ObjectAnimator.ofFloat(ball, "width", ball.getWidth(), ball.getWidth() + 50);
			squashAnim2.setDuration(duration/4);
			squashAnim2.setRepeatCount(1);
			squashAnim2.setRepeatMode(ValueAnimator.REVERSE);
			ValueAnimator stretchAnim1 = ObjectAnimator.ofFloat(ball, "y", endY, endY + 25);
			stretchAnim1.setRepeatCount(1);
			stretchAnim1.setInterpolator(new DecelerateInterpolator());
			stretchAnim1.setDuration(duration / 4);
			stretchAnim1.setRepeatMode(ValueAnimator.REVERSE);
			ValueAnimator stretchAnim2 = ObjectAnimator.ofFloat(ball, "height", ball.getHeight(), ball.getHeight() - 25);
			stretchAnim2.setRepeatCount(1);
			stretchAnim2.setDuration(duration / 4);
			stretchAnim2.setRepeatMode(ValueAnimator.REVERSE);
			stretchAnim2.setInterpolator(new DecelerateInterpolator());
			
			ValueAnimator bounceBackAnim = ObjectAnimator.ofFloat(ball, "y", endY, startY);
			bounceBackAnim.setDuration(duration);
			bounceBackAnim.setInterpolator(new DecelerateInterpolator());
			
			AnimatorSet bouncer = new AnimatorSet();
			bouncer.play(bounceAnim).before(holderAnim);
			bouncer.play(holderAnim).with(squashAnim2).with(stretchAnim1).with(stretchAnim2);
			bouncer.play(bounceBackAnim).after(holderAnim);
			
			ValueAnimator fadeAnim = ObjectAnimator.ofFloat(ball, "alpha", 1f, 0f);
			fadeAnim.setDuration(250);
			fadeAnim.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					super.onAnimationEnd(animation);
					balls.remove(((ObjectAnimator)animation).getTarget());
				}
			});
			
			AnimatorSet animatorSet = new AnimatorSet();
			animatorSet.play(bouncer).before(fadeAnim);
			
			animatorSet.start();
			
			return true;
		}
		
		
		private ShapeHolder addBall(float x, float y) {
			OvalShape circle = new OvalShape();
			circle.resize(50f, 50f);
			ShapeDrawable shapeDrawable = new ShapeDrawable(circle);
			ShapeHolder shapeHolder = new ShapeHolder(shapeDrawable);
			shapeHolder.setX(x - 25f);
			shapeHolder.setY(y - 25f);
			int red = (int) (Math.random() * 255);
			int blue = (int) (Math.random() * 255);
			int green = (int) (Math.random() * 255);
			int color = 0xff000000 | red << 16 | blue << 8 | green;
			int darkColor = 0xff000000 | red/4 << 16 | green/4 << 8 | blue/4;
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f,
                    50f, color, darkColor, Shader.TileMode.CLAMP);
            shapeDrawable.getPaint().setShader(gradient);
            balls.add(shapeHolder);
            return shapeHolder;
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			for (ShapeHolder ball : balls) {
				canvas.save();
				canvas.translate(ball.getX(), ball.getY());
				ball.getShape().draw(canvas);
				canvas.restore();
			}
		}
	}
}
