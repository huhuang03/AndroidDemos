package com.example.tonghu.apidemo.animation;

import java.util.ArrayList;
import java.util.List;

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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.example.tonghu.apidemo.R;

public class AnimationClone extends Activity {
	
	private MyAnimationView myAnimationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation_clone);
		myAnimationView = new MyAnimationView(this);
		((ViewGroup)findViewById(R.id.content)).addView(myAnimationView);
	}
	
	public void begin(View view) {
		myAnimationView.startAnimation();
	}
	
	class MyAnimationView extends View implements AnimatorUpdateListener {
		private List<ShapeHolder> balls;
		private AnimatorSet animation;

		public MyAnimationView(Context context) {
			super(context);
			balls = new ArrayList<ShapeHolder>();
			addBall(50, 25);
			addBall(150, 25);
			addBall(250, 25);
			addBall(350, 25);
		}
		
		private void createAnimation() {
			if (animation == null) {
				
				ObjectAnimator down1 = ObjectAnimator.ofFloat(balls.get(0), "y", balls.get(0).getX(), 
						getHeight() - balls.get(0).getHeight() ).setDuration(500);
				down1.addUpdateListener(this);
				ObjectAnimator down2 = down1.clone();
				down2.setTarget(balls.get(1));
				
				ObjectAnimator up3 = ObjectAnimator.ofFloat(balls.get(2), "y", 
						getHeight() - balls.get(2).getHeight() , balls.get(2).getY());
				up3.addUpdateListener(this);
				up3.setInterpolator(new DecelerateInterpolator());
				ObjectAnimator down3 = down1.clone();
				down3.setTarget(balls.get(2));
				down3.addUpdateListener(this);
				down3.setInterpolator(new AccelerateInterpolator());
				down3.addUpdateListener(this);
				
				AnimatorSet anim2 = new AnimatorSet();
				anim2.playSequentially(down3, up3);
				
				AnimatorSet anim3 = anim2.clone();
				anim3.setTarget(balls.get(3));
				
				animation = new AnimatorSet();
				animation.playTogether(down1, down2, anim2);
				animation.playSequentially(anim2, anim3);
			}
		}
		
		public void startAnimation() {
			createAnimation();
			animation.start();
		}
		
		private void addBall(float x, float y) {
			OvalShape ovalShape = new OvalShape();
			ovalShape.resize(100f, 100f);
			ShapeDrawable drawable = new ShapeDrawable(ovalShape);
			ShapeHolder shapeHolder = new ShapeHolder(drawable);
			balls.add(shapeHolder);
			shapeHolder.setX(x + 25f);
			shapeHolder.setY(y + 25f);
			
			int red = (int)(100 + Math.random() * 155);
            int green = (int)(100 + Math.random() * 155);
            int blue = (int)(100 + Math.random() * 155);
            int color = 0xff000000 | red << 16 | green << 8 | blue;
            Paint paint = drawable.getPaint(); //new Paint(Paint.ANTI_ALIAS_FLAG);
            int darkColor = 0xff000000 | red/4 << 16 | green/4 << 8 | blue/4;
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f,
                    50f, color, darkColor, Shader.TileMode.CLAMP);
//            RadialGradient gradient = new RadialGradient(37.5f, 12.5f,
//                    90f, darkColor, color, Shader.TileMode.CLAMP);
            paint.setShader(gradient);
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			Log.i("tonghu", "AnimationClone.MyAnimationView－onDraw :");
			for (ShapeHolder ball : balls) {
				canvas.save();
				canvas.translate(ball.getX(), ball.getY());
				ball.getShape().draw(canvas);
				canvas.restore();
			}
		}

		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			Log.i("tonghu",
					"AnimationClone.MyAnimationView－onAnimationUpdate :");
			// TODO Auto-generated method stub
			invalidate();
			
		}
		
	}

}
