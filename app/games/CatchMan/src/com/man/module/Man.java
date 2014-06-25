package com.man.module;

import com.man.cfg.CFG;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.FloatMath;

/**
 * �������
 */
public class Man extends People {

	/**
	 * ����
	 */
	protected PointF direction;
	
	/**
	 * Ŀ�ĵ�
	 */
	protected PointF target;
	
	/**
	 * ����һ����
	 */
	public void forwardTo (PointF target){
		// ��ʼ��
		if (this.target == null){
			this.target = new PointF();
		}
		if (this.direction == null){
			this.direction = new PointF();
		}
		
		// ����Ŀ��λ��
		this.target = target;
		
		// ���㷽����ֵ
		float dtx = this.target.x - this.location.x;
		float dty = this.target.y - this.location.y;
		this.direction.x = (dtx / FloatMath.sqrt(dtx * dtx + dty * dty) * CFG.MAN_SPEED);
		this.direction.y = (dty / FloatMath.sqrt(dtx * dtx + dty * dty) * CFG.MAN_SPEED);
		
		float angle = this.getRotateAngle(this.location, this.target);
		this.setAngle(angle);
	}

	/**
	 * ��һ�������ƶ�
	 */
	public void move(){
		// ��ʼ��
		if (this.target == null){
			this.target = new PointF();
		}
		if (this.direction == null){
			this.direction = new PointF();
		}
		// ��ʼ�ƶ�
		float x = location.x + direction.x;
		float y = location.y + direction.y;
		// �ƶ���Ŀ�ĵ�
		float dtx = x - this.target.x;
		float dty = y - this.target.y;
		if (FloatMath.sqrt(dtx * dtx + dty * dty) < 10) {
			x = location.x;
			y = location.y;
		}
		// ���߽��˾Ͳ��ƶ���Ҳ�����쳣
		if (x > CFG.SCREEN_LTX && x < CFG.SCREEN_RBX){
			location.x = x;
		}
		if (y > CFG.SCREEN_LTY && y < CFG.SCREEN_RBY){
			location.y = y;
		}
	}
	
	@Override
	public boolean collide(People people) {
		return false;
	}

	@Override
	public void drawMe(Canvas canvas) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		// ������
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
