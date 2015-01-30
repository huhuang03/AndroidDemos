package com.example.tonghu.apidemo.graphic;

import com.example.tonghu.apidemo.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Paint.Align;
import android.graphics.Shader.TileMode;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;

public class AlphaBitmap extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new SampleView(this));
	}
	
	private static class SampleView extends View {
		Paint paint;
		Bitmap bitmap1;
		Bitmap bitmap2;
		Bitmap bitmap3;
		Shader shader;
		void drawIntoBitmap(Bitmap bp) {
			Paint p = new Paint();
			p.setAntiAlias(true);
			int x = bp.getWidth();
			int y = bp.getHeight();
			Canvas canvas = new Canvas(bitmap3);
			p.setAlpha(0x80);
			canvas.drawCircle(x / 2, y / 2, x / 2, p);
			
			p.setAlpha(0x30);
            p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
			p.setTextAlign(Align.CENTER);
			p.setTextSize(60);
            Paint.FontMetrics fm = p.getFontMetrics();
            canvas.drawText("Alpha", x/2, (y-fm.ascent)/2, p);
		}

		public SampleView(Context context) {
			super(context);
			paint = new Paint();
			bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.app_sample_code);
			bitmap2 = bitmap1.extractAlpha();
			bitmap3 = Bitmap.createBitmap(200, 200, Bitmap.Config.ALPHA_8);
			drawIntoBitmap(bitmap3);
			shader = new LinearGradient(0, 0, 100, 70, new int[]{
					Color.RED, Color.GREEN, Color.BLUE
			}, null, TileMode.MIRROR);
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			int y = 10;
			canvas.drawColor(Color.WHITE);
			
			paint.setColor(Color.RED);
			canvas.drawBitmap(bitmap1, 10, y, paint);
			y += bitmap1.getHeight() + 10;
			canvas.drawBitmap(bitmap2, 10, y, paint);
			y += bitmap2.getHeight();
			paint.setShader(shader);
			canvas.drawBitmap(bitmap3, 10, y, paint);
		}
		
	}
}
