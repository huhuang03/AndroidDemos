package com.example.tonghu.apidemo.animation;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;
import android.view.ViewGroup;

import com.example.tonghu.apidemo.R;

public class CustomEvalutor extends Activity{
	private MyView animationView;
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_evalutor);
		animationView = new MyView(this);
		((ViewGroup)findViewById(R.id.content)).addView(animationView);
	};
	
	public void run(View view) {
		animationView.animation();
	}
	
	class XYShapeHolder {
		ShapeHolder shapeHolder;
		
		public void setXY(PointF pointF) {
			shapeHolder.setX(pointF.x);
			shapeHolder.setY(pointF.y);
		}
		
		public PointF getXY() {
			return new PointF(shapeHolder.getX(), shapeHolder.getY());
		}
		
		public ShapeHolder getShapeHolder() {
			return shapeHolder;
		}
		
		public void setShapeHolder(ShapeHolder shapeHolder) {
			this.shapeHolder = shapeHolder;
		}
	}
	
	class MyView extends View implements AnimatorUpdateListener {
		private XYShapeHolder ball;
		private ObjectAnimator animator;

		public MyView(Context context) {
			super(context);
			addBall(25, 25);
		}
		
		private void addBall(int x, int y) {
			OvalShape ovalShape = new OvalShape();
			ovalShape.resize(50f, 50);
			ShapeDrawable drawable = new ShapeDrawable(ovalShape);
			ShapeHolder holder = new ShapeHolder(drawable);
			ball = new XYShapeHolder();
			ball.setShapeHolder(holder);
			ball.setXY(new PointF(x, y));
			
			Paint paint = ball.getShapeHolder().getShape().getPaint();
			int red = (int) (Math.random() * 255);
			int green = (int) (Math.random() * 255);
			int blue = (int) (Math.random() * 255);
			int color = 0xff000000 | red << 16 | green << 8 | blue;
			int dark = 0xff000000 | red / 4 << 16 | green / 4 << 8 | blue / 4;
			
			RadialGradient rg = new RadialGradient(37.5f, 12.5f, 50, color, dark, Shader.TileMode.CLAMP);
			paint.setShader(rg);
		}
		
		public void animation() {
			if (animator == null) {
				animator = ObjectAnimator.ofObject(ball, "xY", new XYEvalutor(), 
					ball.getXY(), new PointF(400, 700));
				animator.addUpdateListener(this);
			}
			animator.start();
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			canvas.save();
			canvas.translate(ball.getShapeHolder().getX(), ball.getShapeHolder().getY());
			ball.getShapeHolder().getShape().draw(canvas);
			canvas.restore();
		}

		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			invalidate();
		}
		
	}
	
	class XYEvalutor implements TypeEvaluator<PointF> {

		@Override
		public PointF evaluate(float fraction, PointF startValue,
				PointF endValue) {
			return new PointF(startValue.x + fraction * (endValue.x - startValue.x),
					startValue.y + fraction * (endValue.y - startValue.y));
		}
		
	}
}
