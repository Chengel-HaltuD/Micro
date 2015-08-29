/*
 * Copyright (C) 2013 Chengel_HaltuD
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.micro.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * 
 * @ClassName: B
 * @Description: B是BitmapCompressor图片压缩工具类
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:57:24
 * @version V1.0
 *
 */
public class B {
	/**
	 * 质量压缩
	 * @author ping 2015-1-5 下午1:29:58
	 * @param image
	 * @param maxkb
	 * @return
	 */
	public static Bitmap compressBitmap(Bitmap image,int maxkb) {
		//L.showlog(压缩图片);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		//		Log.i(test,原始大小 + baos.toByteArray().length);
		while (baos.toByteArray().length / 1024 > maxkb) { // 循环判断如果压缩后图片是否大于(maxkb)50kb,大于继续压缩
			//			Log.i(test,压缩一次!);
			baos.reset();// 重置baos即清空baos
			options -= 10;// 每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}
		//		Log.i(test,压缩后大小 + baos.toByteArray().length);
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
	 * 官网：获取压缩后的图片
	 * 
	 * @param res
	 * @param resId
	 * @param reqWidth
	 *            所需图片压缩尺寸最小宽度
	 * @param reqHeight
	 *            所需图片压缩尺寸最小高度
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	/**
	 * 官网：获取压缩后的图片
	 * 
	 * @param res
	 * @param resId
	 * @param reqWidth
	 *            所需图片压缩尺寸最小宽度
	 * @param reqHeight
	 *            所需图片压缩尺寸最小高度
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromFile(String filepath,
			int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filepath, options);

		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filepath, options);
	}

	public static Bitmap decodeSampledBitmapFromBitmap(Bitmap bitmap,
			int reqWidth, int reqHeight) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 90, baos);
		byte[] data = baos.toByteArray();

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, 0, data.length, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(data, 0, data.length, options);
	}

	/**
	 * 计算压缩比例值(改进版 by touch_ping)
	 * 
	 * 原版2>4>8...倍压缩
	 * 当前2>3>4...倍压缩
	 * 
	 * @param options
	 *            解析图片的配置信息
	 * @param reqWidth
	 *            所需图片压缩尺寸最小宽度O
	 * @param reqHeight
	 *            所需图片压缩尺寸最小高度
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {

		final int picheight = options.outHeight;
		final int picwidth = options.outWidth;
		Log.i("test", "原尺寸:" +  picwidth +"*"+picheight);

		int targetheight = picheight;
		int targetwidth = picwidth;
		int inSampleSize = 1;

		if (targetheight > reqHeight || targetwidth > reqWidth) {
			while (targetheight  >= reqHeight
					&& targetwidth>= reqWidth) {
				Log.i("test","压缩:" +inSampleSize + "倍");
				inSampleSize += 1;
				targetheight = picheight/inSampleSize;
				targetwidth = picwidth/inSampleSize;
			}
		}

		Log.i("test","最终压缩比例:" +inSampleSize + "倍");
		Log.i("test", "新尺寸:" +  targetwidth +"*" +targetheight);
		return inSampleSize;
	}
}