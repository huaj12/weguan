package com.juzhai.android.core.utils;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class ImageUtils {

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float radius) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, radius, radius, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 按宽度压缩。取中间部分图
	 * 
	 * @param scrBitmap
	 * @param tagerWidth
	 *            (dp)
	 * 
	 * @param tagerHeight
	 *            (dp)
	 * 
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap scrBitmap, int tagerWidth,
			int tagerHeight, Context context) {
		tagerWidth = UIUtil.dip2px(context, tagerWidth);
		tagerHeight = UIUtil.dip2px(context, tagerHeight);
		Bitmap result = zoomWidthBitmap(scrBitmap, tagerWidth, tagerHeight,
				context);
		if (result.getHeight() > tagerHeight) {
			int y = (result.getHeight() / 2) - (tagerHeight / 2);
			if (y + tagerHeight >= result.getHeight()) {
				y = 0;
			}
			return Bitmap.createBitmap(result, 0, y, tagerWidth, tagerHeight);
		}
		return result;

	}

	/**
	 * 按宽度等比例压缩
	 * 
	 * @param scrBitmap
	 * @param tagerWidth
	 *            (dp)
	 * @param tagerHeight
	 *            (dp)
	 * @return
	 */
	public static Bitmap ZoomBitmapNotCut(Bitmap scrBitmap, int tagerWidth,
			int tagerHeight, Context context) {
		tagerWidth = UIUtil.dip2px(context, tagerWidth);
		tagerHeight = UIUtil.dip2px(context, tagerHeight);
		return zoomWidthBitmap(scrBitmap, tagerWidth, tagerHeight, context);
	}

	private static Bitmap zoomWidthBitmap(Bitmap scrBitmap, int tagerWidth,
			int tagerHeight, Context context) {
		int width = scrBitmap.getWidth();
		int height = scrBitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) tagerWidth) / width;
		matrix.postScale(scaleWidth, scaleWidth);
		Bitmap result = Bitmap.createBitmap(scrBitmap, 0, 0, width, height,
				matrix, true);
		return result;
	}

	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}

	public static String getFileName() {
		return UUID.randomUUID().toString() + ".jpg";
	}
}
