package com.man.module;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * ��Ů��
 * ���Ҫ��ܳ�Ů��
 */
public class UglyWoman extends Woman {
	
	@Override
	public void drawMe(Canvas canvas) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.GREEN);
		// ����Ů
		if (image != null) {
			// ԭʼ��ֵ
			int imageWidth = image.getWidth();
			int imageHeight = image.getHeight();
			float imageAngle = this.getAngle();
			// ������ת
			Matrix m = new Matrix();
			m.reset();
			float pivotX = imageWidth / 2;
			float pivotY = imageHeight / 2;
			float transX = location.x - pivotX;
			float transY = location.y - pivotY;
			m.setRotate(imageAngle, pivotX, pivotY);
			m.postTranslate(transX, transY);
			canvas.drawBitmap(image, m, null);
		} else {
			canvas.drawCircle(location.x, location.y, radius, paint);
		}
	}

}
