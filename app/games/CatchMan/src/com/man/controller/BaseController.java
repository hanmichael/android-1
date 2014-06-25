package com.man.controller;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PointF;
import android.os.Handler;

import com.man.SceneMenu;
import com.man.cfg.CFG;
import com.man.module.Man;
import com.man.module.PeopleMoveException;
import com.man.module.UglyWoman;
import com.man.module.Woman;
import com.man.module.WomanFactory;
import com.man.plug.AlertGame;
import com.man.plug.MsgNetScore;
import com.man.plug.MsgScore;
import com.man.util.GameUtil;
import com.man.view.GameView;

/**
 * ��Ϸ������
 */
public class BaseController implements Runnable {
	
	/**
	 * ����
	 */
	protected Man man;

	/**
	 * Ů����
	 */
	protected List<Woman> women;

	/**
	 * ����ͼƬ
	 */
	private Bitmap manImage = null;
	
	/**
	 * Ů��ͼƬ
	 */
	private Bitmap womanImage = null;
	
	/**
	 * ��Ϸ����
	 */
	protected GameView gameView;

	/**
	 * Handler
	 */
	protected Handler handler;

	/**
	 * Context
	 */
	protected Context context;
	
	/**
	 * Handler
	 */
	protected Activity activity;
	
	/**
	 * ������Ϸ��ʾ��
	 */
	protected MsgScore msgScore;

	/**
	 * ������Ϸ��ʾ��
	 */
	protected MsgNetScore msgNetScore;
	
	/**
	 * ��Ϸʱ�䣨�룩
	 */
	protected float gameTime = 0;
	
	/**
	 * ��Ϸ�Ƿ�����
	 */
	private volatile boolean isLive = false;
	
	/**
	 * ����һ��������
	 */
	public BaseController(Activity activity) {
		this.handler = new Handler();
		this.activity = activity;
		this.context = activity;
	}

	/**
	 * �Ƿ���������
	 * @return
	 */
	public void setLive(boolean value) {
		isLive = value;
	}
	
	/**
	 * �Ƿ���������
	 * @return
	 */
	public boolean isLive() {
		return isLive;
	}
	
	public void setManImage(Bitmap bm) {
		this.manImage = bm;
	}
	
	public void setWomanImage(Bitmap bm) {
		this.womanImage = bm;
	}
	
	public void setGameView(GameView gameView) {
		this.gameView = gameView;
	}

	/**
	 * �ػ泡��
	 */
	public void drawAll(Canvas canvas) {
		// �Ż����
		PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
		canvas.setDrawFilter(pfd);
		// ��������
		man.drawMe(canvas);
		// ����Ů����
		if (women.size() > 0) {
			for (Woman woman : women) {
				woman.drawMe(canvas);
			}
		}
		// ����������Ϸ��ʾ��
		if (msgScore != null) {
			msgScore.drawMe(canvas);
		}
		// ����������Ϸ��ʾ��
		else if(msgNetScore != null) {
			msgNetScore.drawMe(canvas);
		}
	}
	
	/**
	 * ��Ϸ���߼�
	 */
	@Override
	public void run() {
		// ��������ʵ��
	}

	/**
	 * ������Ϸ���߼�
	 */
	protected void runGame() {
		// ��ʱִ��
		handler.postDelayed(this, CFG.SCREEN_DELAY);

		// ��Ϸ��ʱ
		gameTime += CFG.SCREEN_STIME / 1000.0;
		msgScore.setTime((int) gameTime);
		
		// ��Ϸִ��
		if (isLive()) {
			// �ƶ�����
			man.move();
			// �Ƴ�����Ů��
			removeWomen();
			// �������ɸ�Ů��
			createWomen();
			// �ػ滭��
			// GameView.redraw -> GameView.onDraw -> Controller.drawAll
			gameView.redraw();
			// �����ײ
			checkCollide();
		}
	}
	
	/**
	 * �����е���������ײ
	 */
	protected void checkCollide() {
		// �����Ů����ײ����������Ϸ����
		if (women.size() > 0) {
			for (Woman woman : women) {
				if (woman.collide(man)) {
					if (woman instanceof UglyWoman) {
						endGame(true);
					}
				}
			}
		}
	}
	
	protected Man newMan() {
		// ��������
		man = new Man();
		man.setImage(manImage); // ����ͼƬ
		float centerX = CFG.getRealX(CFG.SCREEN_WIDTH / 2);
		float centerY = CFG.getRealY(CFG.SCREEN_HEIGHT / 2);
		man.setLocation(new PointF(centerX, centerY));
		return man;
	}
	
	protected Man newMan(float x, float y) {
		// ��������
		man = new Man();
		man.setImage(manImage); // ����ͼƬ
		man.setLocation(new PointF(x, y));
		return man;
	}
	
	protected Woman newWoman() {
		Woman woman = WomanFactory.getWoman();
		woman.setImage(womanImage); // ����ͼƬ
		woman.lookPeople(man); // ��Ů�˿�������
		return woman;
	}
	
	protected Woman newWoman(float x, float y) {
		Woman woman = WomanFactory.getWoman(x, y);
		woman.setImage(womanImage); // ����ͼƬ
		woman.lookPeople(man); // ��Ů�˿�������
		return woman;
	}
	
	protected void addWoman(Woman woman) {
		women.add(woman);
	}
	
	protected void createWomen() {
		// AI���������ɸ�Ů��
		if (women.size() < CFG.GAME_LEVEL[((int) (gameTime / 10)) % CFG.GAME_LEVEL.length]) {
			int tmpCount = CFG.GAME_LEVEL[((int) (gameTime / 10)) % CFG.GAME_LEVEL.length] - women.size();
			for (int i = 0; i < tmpCount; i++) {
				women.add(newWoman());
			}
		}
	}
	
	protected void removeWomen() {
		// ��Ҫ�Ƴ���
		List<Woman> removeWomen = null;
		
		// �ƶ�Ů����
		if (women.size() > 0) {
			for (Woman woman : women) {
				try {
					woman.move();
				} catch (PeopleMoveException e) {
					// ������Ů�˳�����
					if (removeWomen == null) {
						removeWomen = new ArrayList<Woman>();
					}
					removeWomen.add(woman);
				}
			}
		}
		
		// �Ƴ������
		if (removeWomen != null) {
			women.removeAll(removeWomen);
		}
	}
	
	/**
	 * ����Ϸ
	 */
	public void newGame() {
		// ����һ������
		man = newMan();
		// ����һ��Ů��
		women = new ArrayList<Woman>();
		// ��ʱ
		gameTime = 0;
		// ����һ��ʱ����ʾ��
		msgScore = new MsgScore();
		msgScore.setTime((int) gameTime);
		msgScore.setScore(GameUtil.getHighScore(activity));
		// ��ʼ
		startGame();
	}
	
	/**
	 * ��ʼ��Ϸ
	 */
	public void startGame() {
		handler.removeCallbacks(this);
		handler.post(this);
		setLive(true);
	}

	/**
	 * ��ͣ��Ϸ
	 */
	public void pauseGame() {
		handler.removeCallbacks(this);
		setLive(false);
	}
	
	/**
	 * ������Ϸ
	 * @param showDialog
	 */
	public void endGame(boolean showDialog) {
		// ���ñ�־
		setLive(false);
		// ֹͣ����
		handler.removeCallbacks(this);
		// ��¼����
		int thisScore = (int) gameTime;
		int highScore = GameUtil.getHighScore(activity);
		if (thisScore > highScore) {
			highScore = thisScore;
			GameUtil.setHighScore(activity, thisScore);
		}
		// ��ʾ���
		if (showDialog) {
			AlertGame alertGame = new AlertGame(context);
			alertGame.setScore(thisScore);
			alertGame.setHighScore(highScore);
			alertGame.setBtnCallback(new AlertGame.BtnCallback(){
				@Override
				public void onRestart() {
					newGame();
				}
				@Override
				public void onBack() {
					GameUtil.forward(activity, SceneMenu.class);
				}
			});
			alertGame.show();
		}
	}
}