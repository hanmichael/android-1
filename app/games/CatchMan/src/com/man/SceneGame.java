package com.man;

import com.man.controller.GameController;
import com.man.util.GameUtil;
import com.man.view.GameView;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * ��activity��
 */
public class SceneGame extends Activity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.restartGame:
				controller.newGame();
				break;
			case R.id.resumeGame:
				if (!controller.isLive()){
					controller.startGame();
				}
				break;
			case R.id.quitGame:
				controller.endGame(false);
				GameUtil.forward(this, SceneMenu.class);
				break;
			default:
				break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_MENU:
				if (controller.isLive()){
					controller.pauseGame();
				}
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
	private GameController controller;

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
		controller = new GameController(this);
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
	protected void onStop() {
		controller.endGame(false);
		super.onStop();
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