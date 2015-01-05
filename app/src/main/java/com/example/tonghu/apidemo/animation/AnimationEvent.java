package com.example.tonghu.apidemo.animation;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tonghu.apidemo.R;

public class AnimationEvent extends Activity {
	TextView startText, repeatText, cancelText, endText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation_event);
		LinearLayout container = (LinearLayout) findViewById(R.id.container);
        final AnimationView animView = new AnimationView(this);
        container.addView(animView);
		startText = (TextView) findViewById(R.id.startText);
        startText.setAlpha(.5f);
        repeatText = (TextView) findViewById(R.id.repeatText);
        repeatText.setAlpha(.5f);
        cancelText = (TextView) findViewById(R.id.cancelText);
        cancelText.setAlpha(.5f);
        endText = (TextView) findViewById(R.id.endText);
        endText.setAlpha(.5f);
        
        final CheckBox endCB = (CheckBox) findViewById(R.id.endCB);


        Button starter = (Button) findViewById(R.id.startButton);
        starter.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                animView.animation(endCB.isChecked());
            }
        });

        Button canceler = (Button) findViewById(R.id.cancelButton);
        canceler.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                animView.cancelAnimation();
            }
        });

        Button ender = (Button) findViewById(R.id.endButton);
        ender.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                animView.endAnimation();
            }
        });
	}
	
	class AnimationView extends View implements AnimatorUpdateListener, AnimatorListener {
		private ShapeHolder ball;
		private AnimatorSet animation;
		private boolean endImmediately;
		
		public AnimationView(Context context) {
			super(context);
			ball = addBall(25, 25);
		}
		
		public void endAnimation() {
			// TODO Auto-generated method stub
			animation.end();
			
		}

		public void cancelAnimation() {
			// TODO Auto-generated method stub
			animation.cancel();
			
		}

		public void animation(boolean endImmediately) {
			this.endImmediately = endImmediately;
            startText.setAlpha(.5f);
            repeatText.setAlpha(.5f);
            cancelText.setAlpha(.5f);
            endText.setAlpha(.5f);
			if (animation == null) {
				ObjectAnimator yAnim = ObjectAnimator.ofFloat(ball, "y",
                        ball.getY(), getHeight() - 50f).setDuration(2000);
                yAnim.setRepeatCount(0);
                yAnim.setRepeatMode(ValueAnimator.REVERSE);
                yAnim.setInterpolator(new AccelerateInterpolator(2f));
                yAnim.addUpdateListener(this);
                yAnim.addListener(this);

                ObjectAnimator xAnim = ObjectAnimator.ofFloat(ball, "x",
                        ball.getX(), ball.getX() + 300).setDuration(1500);
                xAnim.setStartDelay(0);
                xAnim.setRepeatCount(0);
                xAnim.addUpdateListener(this);
                xAnim.setRepeatMode(ValueAnimator.REVERSE);
                xAnim.setInterpolator(new AccelerateInterpolator(2f));

                animation = new AnimatorSet();
                ((AnimatorSet) animation).playTogether(xAnim, yAnim);
//                ((AnimatorSet)animation).play(xAnim);
                animation.addListener(this);
			}
			animation.start();
		}
		
		private ShapeHolder addBall(int x, int y) {
			OvalShape circle = new OvalShape();
            circle.resize(50f, 50f);
            ShapeDrawable drawable = new ShapeDrawable(circle);
            ShapeHolder shapeHolder = new ShapeHolder(drawable);
            shapeHolder.setX(x - 25f);
            shapeHolder.setY(y - 25f);
            int red = (int)(Math.random() * 255);
            int green = (int)(Math.random() * 255);
            int blue = (int)(Math.random() * 255);
            int color = 0xff000000 | red << 16 | green << 8 | blue;
            Paint paint = drawable.getPaint(); //new Paint(Paint.ANTI_ALIAS_FLAG);
            int darkColor = 0xff000000 | red/4 << 16 | green/4 << 8 | blue/4;
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f,
                    50f, color, darkColor, Shader.TileMode.CLAMP);
            paint.setShader(gradient);
            return shapeHolder;
		}
		
		@Override
        protected void onDraw(Canvas canvas) {
            canvas.save();
            canvas.translate(ball.getX(), ball.getY());
            ball.getShape().draw(canvas);
            canvas.restore();
        }

		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			invalidate();
		}

		@Override
		public void onAnimationStart(Animator animation) {
			startText.setAlpha(1.0f);
		}

		@Override
		public void onAnimationEnd(Animator animation) {
			endText.setAlpha(1f);
		}

		@Override
		public void onAnimationCancel(Animator animation) {
			cancelText.setAlpha(1f);
		}

		@Override
		public void onAnimationRepeat(Animator animation) {
			// TODO Auto-generated method stub
			
		}
	}
}
