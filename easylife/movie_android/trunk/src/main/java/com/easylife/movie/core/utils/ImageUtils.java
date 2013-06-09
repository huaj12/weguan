package com.easylife.movie.core.utils;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import android.app.Activity;
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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.WindowManager;

public class ImageUtils {
	private static long maxImageLength = 1024 * 100;
	private static int reduceQuality = 5;

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float radius,
			Context context) {
		radius = UIUtil.dip2px(context, radius);
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
	public static Bitmap zoomWidthCutMiddle(Bitmap scrBitmap, int tagerWidth,
			int tagerHeight, Context context) {
		tagerWidth = UIUtil.dip2px(context, tagerWidth);
		tagerHeight = UIUtil.dip2px(context, tagerHeight);
		Bitmap result = zoomBitmap(scrBitmap, tagerWidth, 0, context);
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
	public static Bitmap zoomWidthBitmapNotCut(Bitmap scrBitmap,
			int tagerWidth, int tagerHeight, Context context) {
		tagerWidth = UIUtil.dip2px(context, tagerWidth);
		tagerHeight = UIUtil.dip2px(context, tagerHeight);
		return zoomBitmap(scrBitmap, tagerWidth, 0, context);
	}

	private static Bitmap zoomBitmap(Bitmap scrBitmap, int tagerWidth,
			int tagerHeight, Context context) {
		int width = scrBitmap.getWidth();
		int height = scrBitmap.getHeight();
		Matrix matrix = new Matrix();
		if (tagerHeight != 0 && tagerWidth == 0) {
			float scaleHeight = ((float) tagerHeight) / height;
			matrix.postScale(scaleHeight, scaleHeight);
		} else if (tagerWidth != 0 && tagerHeight == 0) {
			float scaleWidth = ((float) tagerWidth) / width;
			matrix.postScale(scaleWidth, scaleWidth);
		} else if (tagerWidth != 0 && tagerHeight != 0) {
			float scaleHeight = ((float) tagerHeight) / height;
			float scaleWidth = ((float) tagerWidth) / width;
			matrix.postScale(scaleWidth, scaleHeight);
		}
		Bitmap result = Bitmap.createBitmap(scrBitmap, 0, 0, width, height,
				matrix, true);
		return result;
	}

	public static byte[] bitmap2Bytes(Bitmap bm, int quality) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);
		byte[] imageInByte = baos.toByteArray();
		recycle(baos);
		if (imageInByte.length > maxImageLength
				&& (quality - reduceQuality) > 0) {
			imageInByte = null;
			return bitmap2Bytes(bm, quality - reduceQuality);
		} else {
			return imageInByte;
		}
	}

	private static void recycle(ByteArrayOutputStream baos) {
		if (baos != null) {
			try {
				baos.flush();
				baos.close();
			} catch (Exception e) {
			} finally {
				baos = null;
			}
		}
	}

	public static String getFileName() {
		return UUID.randomUUID().toString() + ".jpg";
	}

	public static Bitmap getZoomWidthCutBelow(Drawable drawable, Context context) {
		WindowManager manage = ((Activity) context).getWindowManager();
		Display display = manage.getDefaultDisplay();
		int tagerWidth = display.getWidth();
		BitmapDrawable bd = (BitmapDrawable) drawable;
		return zoomBitmap(bd.getBitmap(), tagerWidth, 0, context);
	}

	public static Bitmap getZoomHeightCutRight(Drawable drawable,
			Context context) {
		WindowManager manage = ((Activity) context).getWindowManager();
		Display display = manage.getDefaultDisplay();
		int tagerHeight = display.getHeight();
		BitmapDrawable bd = (BitmapDrawable) drawable;
		return zoomBitmap(bd.getBitmap(), 0, tagerHeight, context);
	}

	public static Bitmap getZoomWidthCutAbove(Drawable drawable, Context context) {
		WindowManager manage = ((Activity) context).getWindowManager();
		Display display = manage.getDefaultDisplay();
		int tagerWidth = display.getWidth();
		int tagerHeight = display.getHeight();
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap result = zoomBitmap(bd.getBitmap(), tagerWidth, 0, context);
		// 压缩后的高度大于屏幕高度截取上方
		if (result.getHeight() > tagerHeight) {
			int y = result.getHeight() - tagerHeight;
			return Bitmap.createBitmap(result, 0, y, tagerWidth, tagerHeight);
		}
		return result;
	}

	public static Bitmap getZoomWidthCutMiddle(Drawable drawable,
			Context context) {
		WindowManager manage = ((Activity) context).getWindowManager();
		Display display = manage.getDefaultDisplay();
		int tagerWidth = display.getWidth();
		int tagerHeight = display.getHeight();
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap scrBitmap = bd.getBitmap();
		Bitmap result = zoomBitmap(scrBitmap, tagerWidth, 0, context);
		if (result.getHeight() > tagerHeight) {
			int y = (result.getHeight() / 2) - (tagerHeight / 2);
			if (y + tagerHeight >= result.getHeight()) {
				y = 0;
			}
			return Bitmap.createBitmap(result, 0, y, tagerWidth, tagerHeight);
		}
		return result;
	}
}
