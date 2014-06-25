package com.man.view;

import com.man.controller.GameController;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.View;

/**
 * ��Ϸ������
 */
public class GameView extends View {

	/**
	 * ������
	 */
	private GameController controller;
	
	/**
	 * ��������µ��߳�
	 */
	private Runnable updateThread;
	
	/**
	 * Handler
	 */
	private Handler handler;
	
	/**
	 * ���췽��
	 * 
	 * @param context
	 */
	public GameView(Context context) {
		super(context);
		handler = new Handler();
		updateThread = new Runnable() {
			@Override
			public void run() {
				// ���»���
				postInvalidate();
				handler.removeCallbacks(updateThread);
			}
		};
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// �ػ��߼�
		controller.drawAll(canvas);
		super.onDraw(canvas);
	}

	public GameController getController() {
		return controller;
	}

	public void setController(GameController controller) {
		this.controller = controller;
	}
	
	/**
	 * ˢ�����
	 */
	public void redraw(){
		handler.post(updateThread);
	}
	
}
