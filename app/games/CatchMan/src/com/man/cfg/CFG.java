package com.man.cfg;

import com.man.util.GameUtil;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * ��Ϸ����
 */
public class CFG {
	
	/**
	 * ��Ļ���
	 */
	public static int SCREEN_WIDTH = 320;
	
	/**
	 * ��Ļ�߶�
	 */
	public static int SCREEN_HEIGHT = 480;

	/**
	 * ��Ļƫ����
	 */
	public static float SCREEN_OFFSET_X = 0;
	
	/**
	 * ��Ļƫ����
	 */
	public static float SCREEN_OFFSET_Y = 0;
	
	/**
	 * �ӳ�ʱ�䣨ms��
	 */
	public static int SCREEN_DELAY = 50;
	
	/**
	 * ����ʱ�䣨ms��
	 */
	public static int SCREEN_STIME = 50;
	
	/**
	 * ��Ļ�߽�
	 */
	public static float SCREEN_LTX = 0;
	public static float SCREEN_LTY = 0;
	public static float SCREEN_RBX = SCREEN_WIDTH;
	public static float SCREEN_RBY = SCREEN_HEIGHT;
	
	/**
	 * �ɵ���ı߽�ֵ - ����Ů�˽�ɫ��NetGameController��
	 */
	public static float SCREEN_MARGIN = 50;

	/**
	 * �����С
	 */
	public static final float PEOPLE_RADIUS = 24;

	/**
	 * Ů���ٶ�
	 */
	public static final float WOMAN_SPEED = 10;

	/**
	 * �����ٶ�
	 */
	public static final float MAN_SPEED = 15;
	
	/**
	 * ��Ϸ����
	 */
	public static final int[] GAME_LEVEL = {1,3,5,3,6,3,6,4,7,4,7,4,8,5,9,2,10,5,11};
	
	/**
	 * ��ȡ��ʵX��λ��
	 */
	public static float getRealX(float x) {
		return SCREEN_OFFSET_X + x;
	}
	
	/**
	 * ��ȡ��ʵY��λ��
	 */
	public static float getRealY(float y) {
		return SCREEN_OFFSET_Y + y;
	}
	
	/**
	 * ������Ļ����Ӧ�����ߡ��߽磩
	 */
	public static void autoAdaption(Activity activity) {
		// ��Ļ����Ӧ
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		float screenRealWidth = dm.widthPixels;
		float screenRealHeight = dm.heightPixels;
//		float screenRealWidth = dm.widthPixels * dm.density;
//		float screenRealHeight = dm.heightPixels * dm.density;
		SCREEN_OFFSET_X = (screenRealWidth - SCREEN_WIDTH) / 2;
		SCREEN_OFFSET_Y = (screenRealHeight - SCREEN_HEIGHT) / 2;
		SCREEN_LTX = SCREEN_LTX + SCREEN_OFFSET_X;
		SCREEN_LTY = SCREEN_LTY + SCREEN_OFFSET_Y;
		SCREEN_RBX = SCREEN_RBX + SCREEN_OFFSET_X;
		SCREEN_RBY = SCREEN_RBY + SCREEN_OFFSET_Y;
		Log.e("CFG", "SCREEN_WIDTH:" + screenRealWidth + ",SCREEN_HEIGHT:" + screenRealHeight);
		Log.e("CFG", "SCREEN_OFFSET_X:" + SCREEN_OFFSET_X + ",SCREEN_OFFSET_Y:" + SCREEN_OFFSET_Y);
		// �����Ϸ֡ͬ������
		int freq = GameUtil.getCurCpuFreq();
		CFG.SCREEN_DELAY = (int) freq / 10000;
		Log.e("CFG", "CPU_FREQ:" + freq);
	}
}
