package com.man.module;

import com.man.cfg.CFG;

import android.graphics.PointF;

/**
 * Ů�˹���
 */
public class WomanFactory {

	/**
	 * �����λ�ò���Ů��
	 */
	public static Woman getWoman() {
		
		//����һ����Ů��
		Woman woman = new UglyWoman();
		
		//����Ů������
		float rnd1 = (float) Math.random();
		float rnd2 = (float) Math.random();
		float rnd3 = (float) Math.random();
		PointF point = new PointF();
		if (rnd1 > 0.5){
			//��������
			point.x = CFG.SCREEN_WIDTH * rnd3;
			point.y = (rnd2 > 0.5) ? 0 : CFG.SCREEN_HEIGHT;
		} else {
			//��������
			point.x = (rnd2 > 0.5) ? 0 : CFG.SCREEN_WIDTH;
			point.y = CFG.SCREEN_HEIGHT * rnd3;
		}
		//���ʵ������
		point.x = CFG.getRealX(point.x);
		point.y = CFG.getRealY(point.y);
		woman.setLocation(point);
		
		//����Ů��
		return woman;
	}
	
	/**
	 * ��ָ��λ��Ů��
	 */
	public static Woman getWoman(float x, float y) {
		
		//����һ����Ů��
		Woman woman = new UglyWoman();
		
		//����Ů������
		PointF point = new PointF(x, y);
		woman.setLocation(point);
		
		//����Ů��
		return woman;
	}
}
