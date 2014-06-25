package com.man;

import com.man.controller.NetGameController;
import com.man.view.GameView;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.KeyEvent;

/**
 * ��activity��
 */
public class SceneNetGame extends Activity {

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_MENU:
				break;
			case KeyEvent.KEYCODE_BACK:
				break;
			default:
				break;
		}
		return false;
	}
	
	/**
	 * ������
	 */
	private NetGameController controller;

	/**
	 * ��Ϸ���
	 */
	private GameView gameView;

	private PowerManager.WakeLock mWakeLock;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initActivity(); // ϵͳ��ʼ��
		initGame(); // ��Ϸ��ʼ��
		setContentView(gameView);
	}

	/**
	 * ϵͳ��ʼ��
	 */
	private void initActivity() {
		// ��Ļ��������Ĵ���
		PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
	}

	/**
	 * ��Ϸ��ʼ��
	 */
	private void initGame() {
		// ��������������Ϸ������
		Bundle args = this.getIntent().getExtras();
		controller = new NetGameController(this, args);
		gameView = new GameView(this);
		controller.setGameView(gameView);
		// ���ÿ������ĳ�ʼ��Դ
		Resources res = getResources();
		Bitmap bmw1 = BitmapFactory.decodeResource(res, R.drawable.w1);
		Bitmap bmw2 = BitmapFactory.decodeResource(res, R.drawable.w2);
		// ����ͼƬ��ԭʼ�ߴ�
		bmw1 = Bitmap.createScaledBitmap(bmw1, 48, 48, true);
		bmw2 = Bitmap.createScaledBitmap(bmw2, 48, 48, true);
		controller.setWomanImage(bmw1);
		controller.setManImage(bmw2);
		// ���ÿ���������Ϸ�߼�
		gameView.setController(controller);
		// ���ÿ������Ŀ����߼�
		gameView.setOnTouchListener(controller);
		// ��ʼ����Ϸ
		controller.newGame();
	}
	
	@Override
	protected void onPause() {
		mWakeLock.release();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mWakeLock.acquire();
		super.onResume();
	}
	
	public Context getContext () {
		return this;
	}
}