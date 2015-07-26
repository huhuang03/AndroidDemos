package com.example.tonghu.apidemo.graphic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Xfermoders extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new SampleView(this));
	}
	
	public static final Xfermode[] sModes = {
        new PorterDuffXfermode(PorterDuff.Mode.CLEAR),
        new PorterDuffXfermode(PorterDuff.Mode.SRC),
        new PorterDuffXfermode(PorterDuff.Mode.DST),
        new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
        new PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
        new PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
        new PorterDuffXfermode(PorterDuff.Mode.DST_IN),
        new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
        new PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
        new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
        new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
        new PorterDuffXfermode(PorterDuff.Mode.XOR),
        new PorterDuffXfermode(PorterDuff.Mode.DARKEN),
        new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
        new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
        new PorterDuffXfermode(PorterDuff.Mode.SCREEN)
    };
	
	public class SampleView extends View{
		private static final int W = 64;
        private static final int H = 64;
        private static final int ROW_MAX = 4;   // number of samples per row
		private Bitmap dstB;
		private Bitmap srcB;
		private Paint paint;
		private BitmapShader bg;
		private Paint labelP;

		Bitmap createDst(int width, int height){
			Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(bm);
			Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
			
			p.setColor(0xFFFFCC44);
			c.drawOval(new RectF(0, 0, width*3/4, height*3/4), p);
			return bm;
		}
		
		Bitmap createSrc(int w, int h) {
			Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(bm);
			Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
			
			p.setColor(0xFF66AAFF);
			c.drawRect(w/3, h/3, w*19/20, h*19/20, p);
			return bm;
		}
		
		
		
		public SampleView(Context context) {
			super(context);
			dstB = createDst(W, H);
			srcB = createSrc(W, H);
			paint = new Paint();
			
			Bitmap bm = Bitmap.createBitmap(new int[]{0xFFFFFFFF, 0xFFCCCCCC,
					0xFFCCCCCC, 0xFFFFFFFF}, 2, 2, Bitmap.Config.RGB_565);
			
			bg = new BitmapShader(bm, TileMode.REPEAT, TileMode.REPEAT);
			Matrix matrix = new Matrix();
			matrix.setScale(6, 6);
			bg.setLocalMatrix(matrix);
			
			labelP = new Paint(Paint.ANTI_ALIAS_FLAG);
			labelP.setTextAlign(Align.CENTER);
		}
		
		private final String[] labels = {
            "Clear", "Src", "Dst", "SrcOver",
            "DstOver", "SrcIn", "DstIn", "SrcOut",
            "DstOut", "SrcATop", "DstATop", "Xor",
            "Darken", "Lighten", "Multiply", "Screen"
        };
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			Log.i("tonghu", "Xfermoders.SampleView－onDraw :" + canvas.getWidth() + ", " + canvas.getHeight());
			canvas.drawColor(Color.WHITE);
			canvas.translate(15, 35);
			int x = 0;
			int y = 0;
			
			for (int i = 0; i < sModes.length; i++) {
				paint.setShader(null);
				paint.setColor(Color.BLACK);
				paint.setStyle(Style.STROKE);
				canvas.drawRect(x - 0.5f, y - 0.5f, x + W + 0.5f, y + H + 0.5f, paint);
				
				paint.setShader(bg);
				paint.setStyle(Style.FILL);
				canvas.drawRect(x, y, x + W, y + H, paint);
			
				paint.setShader(null);
				int saveLayer = canvas.saveLayer(x, y, x + W, y + H, paint, Canvas.ALL_SAVE_FLAG);
				canvas.drawBitmap(dstB, x, y, paint);
				paint.setXfermode(sModes[i]);
				canvas.drawBitmap(srcB, x, y, paint);
				canvas.drawText(labels[i], x  + W/2, y - labelP.getTextSize() / 2, labelP);
				paint.setXfermode(null);
				canvas.restoreToCount(saveLayer);
				
				x += W + 10;
				if ((i % ROW_MAX) == ROW_MAX -1) {
					x = 0;
					y += H + 30;
				}
			}
		}
		
	}
}
