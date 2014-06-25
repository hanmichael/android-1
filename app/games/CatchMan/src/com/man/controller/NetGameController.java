package com.man.controller;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.man.SceneHall;
import com.man.cfg.CFG;
import com.man.module.Woman;
import com.man.net.GameClient;
import com.man.net.GameClientListener;
import com.man.plug.AlertNetGame;
import com.man.plug.MsgNetScore;
import com.man.util.GameUtil;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * ���̿�����
 */
public class NetGameController extends GameController {

	private String TAG = this.getClass().getSimpleName();
	
	private GameClient client = null;
	
	private String userId = null;
//	private String roomId = null;
	private String roleId = null;
	private String winner = null;
	
	private int winTime = 30; // win after these seconds
	
	float xRange1;
	float xRange2;
	float yRange1;
	float yRange2;
	
	private Paint mPaint = new Paint();
	
	public NetGameController(Activity activity, Bundle args) {
		super(activity);

		userId = args.getString("userId");
//		roomId = args.getString("roomId");
		roleId = args.getString("roleId");
		
		client = new GameClient();
		client.setListener(new NetGameControllerListener());
		
		xRange1 = CFG.getRealX(CFG.SCREEN_MARGIN);
		yRange1 = CFG.getRealY(CFG.SCREEN_MARGIN);
		xRange2 = CFG.getRealX(CFG.SCREEN_WIDTH - CFG.SCREEN_MARGIN);
		yRange2 = CFG.getRealY(CFG.SCREEN_HEIGHT - CFG.SCREEN_MARGIN);
	}
	
	/**
	 * �ػ泡��
	 */
	@Override
	public void drawAll(Canvas canvas) {
		// ��������
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.YELLOW);
		mPaint.setStyle(Paint.Style.STROKE);
		CornerPathEffect e1 = new CornerPathEffect(5);
		DashPathEffect e2 = new DashPathEffect(new float[]{3,3}, 1);
		ComposePathEffect effect = new ComposePathEffect(e1, e2);
		mPaint.setPathEffect(effect); // ��������
		canvas.drawRect(xRange1, yRange1, xRange2, yRange2, mPaint);
		// ��������
		super.drawAll(canvas);
	}
	
	/**
	 * ������Ϸ���߼�
	 */
	@Override
	protected void runGame() {
		// ��ʱִ��
		handler.postDelayed(this, CFG.SCREEN_DELAY);

		// ��Ϸ��ʱ
		gameTime += CFG.SCREEN_STIME / 1000.0;
		msgNetScore.setTime((int) gameTime);
		if (gameTime >= winTime) {
			// ���ñ�־
			setLive(false);
			// ֹͣ����
			handler.removeCallbacks(this);
			// ��ģʽ������ʤ��
			if (roleId.equalsIgnoreCase("0")) {
				winner = "User " + userId;
				String msg = "[2,\"" + winner + "\"," + winTime + "]";
				client.sendRoomMsg(msg);
			}
		}
		
		// ��Ϸִ��
		if (isLive()) {
			// �ƶ�����
			man.move();
			// �Ƴ�����Ů��
			removeWomen();
			// �ػ滭��
			// GameView.redraw -> GameView.onDraw -> Controller.drawAll
			gameView.redraw();
			// �����ײ
			checkCollide();
		}
	}
	
	/**
	 * �û��ٿ��߼�
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (this.isLive()) {
			float x = event.getX();
			float y = event.getY();
			// �������
			if (roleId.equalsIgnoreCase("0")) {
				x = x - CFG.SCREEN_OFFSET_X;
				y = y - CFG.SCREEN_OFFSET_Y;
				client.sendRoomMsg("[1,0," + x + "," + y + "]");
			}
			// Ů�����
			if (roleId.equalsIgnoreCase("1")) {
				if (checkAddWoman(x, y)) {
					x = x - CFG.SCREEN_OFFSET_X;
					y = y - CFG.SCREEN_OFFSET_Y;
					client.sendRoomMsg("[1,1," + x + "," + y + "]");
				}
			}
		}
		return false;
	}
	
	/**
	 * ����Ϸ
	 */
	@Override
	public void newGame() {
		// ����һ������
		man = newMan();
		// ����һ��Ů��
		women = new ArrayList<Woman>();
		// ��ʱ
		gameTime = 0;
		// ����һ��ʱ����ʾ��
		msgNetScore = new MsgNetScore();
		msgNetScore.setUser(userId);
		msgNetScore.setRole(roleId);
		msgNetScore.setWinTime(winTime);
		msgNetScore.setTime((int) gameTime);
		// ��ʼ
		startGame();
	}
	
	@Override
	public void endGame(boolean showDialog) {
		// ���ñ�־
		setLive(false);
		// ֹͣ����
		handler.removeCallbacks(this);
		// ��ģʽ��Ů��ʤ��
		if (roleId.equalsIgnoreCase("1")) {
			winner = "User " + userId;
			String msg = "[2,\"" + winner + "\"," + winTime + "]";
			client.sendRoomMsg(msg);
		}
	}
	
	private void showAlert(String winner, int wscore) {
		// ���ñ�־
		setLive(false);
		// ������ʾ��
		AlertNetGame alertNetGame = new AlertNetGame(context);
		alertNetGame.setWinner(winner);
		alertNetGame.setWinScore(wscore);
		alertNetGame.setBtnCallback(new AlertNetGame.BtnCallback(){
			@Override
			public void onQuit() {
				client.leaveRoom(); // �뿪����
				GameUtil.forward(activity, SceneHall.class);
			}
		});
		alertNetGame.show();
	}
	
	private class NetGameControllerListener extends GameClientListener {
		
		@Override
		public void onSendRoomMsg(String event, JSONArray arguments) {
			Log.w(TAG, "onSendRoomMsg:" + arguments.toString());
			try {
				JSONArray msg = new JSONArray(arguments.getString(2));
				int action = msg.getInt(0);
				// ��Ҳ���
				if (action == 1) {
					// ��ȡ��ɫ
					int roleId = msg.getInt(1);
					float x = CFG.getRealX((float) msg.getDouble(2));
					float y = CFG.getRealY((float) msg.getDouble(3));
					// �������
					if (roleId == 0) {
						PointF direction = new PointF(x, y);
						man.forwardTo(direction);
					}
					// Ů�����
					if (roleId == 1) {
						addWoman(newWoman(x, y));
					}
				}
				// ��ʤ��Ϣ
				if (action == 2) {
					String winner = msg.getString(1);
					int wscore = msg.getInt(2);
					showAlert(winner, wscore);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean checkAddWoman(float x, float y) {
		// ��Ļ�м�ķ��β��ֲ��������Ů��
		if (x > xRange1 && x < xRange2 && y > yRange1 && y < yRange2) {
			return false;
		}
		// ֻ����ͬʱ����һ��Ů��
		if (women.size() > 0) {
			return false;
		}
		// ���ܲſ������
		return true;
	}
}
