package com.man.module;

import com.man.cfg.CFG;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.FloatMath;

/**
 * ��
 */
public abstract class People {

	/**
	 * λ��
	 */
	protected PointF location;

	/**
	 * ��С(�뾶)
	 */
	protected float radius = CFG.PEOPLE_RADIUS;

	/**
	 * ����ͼ��
	 */
	protected Bitmap image = null;

	/**
	 * �����Ƕ�
	 */
	protected float angle = 0.0f;

	/**
	 * ���Լ����ڻ�����
	 * 
	 * @param canvas
	 */
	public abstract void drawMe(Canvas canvas);

	/**
	 * �������һ�����Ƿ���ײ
	 */
	public abstract boolean collide(People people);

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public PointF getLocation() {
		return location;
	}

	public void setLocation(PointF location) {
		this.location = location;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap bm) {
		this.image = bm;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float a) {
		this.angle = a;
	}

	/**
	 * ���������봹ֱ����н�
	 * @param p1
	 * @param p2
	 * @return
	 */
	public float getRotateAngle(PointF p1, PointF p2) {
		float tran_x = p1.x - p2.x;
		float tran_y = p1.y - p2.y;
		float angle = (float) (Math.asin(tran_x
				/ FloatMath.sqrt(tran_x * tran_x + tran_y * tran_y)) * 180 / Math.PI);
//		return angle;
		float degree = 0.0f;
		if (!Float.isNaN(angle)) {
			if (tran_x >= 0 && tran_y <= 0) {// ��һ����
				degree = angle;
			} else if (tran_x >= 0 && tran_y >= 0) {// �ڶ�����
				degree = 180 - angle;
			} else if (tran_x <= 0 && tran_y >= 0) {// ��������
				degree = 180 - angle;
			} else if (tran_x <= 0 && tran_y <= 0) {// ��������
				degree = 360 + angle;
			}
		}
		return degree;
	}
}
