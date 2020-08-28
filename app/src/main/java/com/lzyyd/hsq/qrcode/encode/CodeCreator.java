package com.lzyyd.hsq.qrcode.encode;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class CodeCreator {

	/**
	 * 生成QRCode（二维码）
	 *
	 * @param str
	 * @return
	 * @throws WriterException
	 */
	public static Bitmap createQRCode(String url, int widthPix, int heightPix, Bitmap logoBm) throws WriterException {


		int IMAGE_HALFWIDTH = widthPix/10;

		if (url == null || url.equals("")) {
			return null;
		}

		// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
		BitMatrix matrix = new MultiFormatWriter().encode(url,
				BarcodeFormat.QR_CODE, widthPix, heightPix);

		int width = matrix.getWidth();
		int height = matrix.getHeight();
		int halfW = width / 2;
		int halfH = height / 2;

		Matrix m = new Matrix();
		float sx = (float) 2 * IMAGE_HALFWIDTH / logoBm.getWidth();
		float sy = (float) 2 * IMAGE_HALFWIDTH
				/ logoBm.getHeight();
		m.setScale(sx, sy);
		logoBm = Bitmap.createBitmap(logoBm, 0, 0,
				logoBm.getWidth(), logoBm.getHeight(), m, false);

		// 二维矩阵转为一维像素数组,也就是一直横着排了
		int[] pixels = new int[width * height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				/*if (matrix.get(x, y)) {
					pixels[y * width + x] = 0xff000000;
				}*/
				if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
						&& y > halfH - IMAGE_HALFWIDTH
						&& y < halfH + IMAGE_HALFWIDTH) {
					//该位置用于存放图片信息
					//记录图片每个像素信息
					pixels[y * width + x] = logoBm.getPixel(x - halfW
							+ IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
				} else {
					if (matrix.get(x, y)) {
						pixels[y * widthPix + x] = 0xff000000;
					} else {
						pixels[y * widthPix + x] = 0xffffffff;
					}
				}
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

		/*if (logoBm != null) {
			bitmap = addLogo(bitmap, logoBm);
		}*/


		return bitmap;
	}


	/**
	 * 在二维码中间添加Logo图案
	 */
	private static Bitmap addLogo(Bitmap src, Bitmap logo) {
		if (src == null) {
			return null;
		}
		if (logo == null) {
			return src;
		}
		//获取图片的宽高
		int srcWidth = src.getWidth();
		int srcHeight = src.getHeight();
		int logoWidth = logo.getWidth();
		int logoHeight = logo.getHeight();
		if (srcWidth == 0 || srcHeight == 0) {
			return null;
		}
		if (logoWidth == 0 || logoHeight == 0) {
			return src;
		}
		//logo大小为二维码整体大小的1/5
		float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
		Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
		try {
			Canvas canvas = new Canvas(bitmap);
			canvas.drawBitmap(src, 0, 0, null);
			canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
			canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);
			canvas.save();
			canvas.restore();
		} catch (Exception e) {
			bitmap = null;
			e.getStackTrace();
		}
		return bitmap;
	}

}
