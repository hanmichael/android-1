package com.man.plug;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * ʱ����ʾ
 */
public class MsgNetScore {
	
	/**
	 * ��ɫ
	 */
	private String user;
	
	/**
	 * ��ɫ
	 */
	private String role;
	
	/**
	 * ʱ��
	 */
	private int time = 0;
	
	/**
	 * Ӯʱ��
	 */
	private int winTime = 30;
	
	/**
	 * ���Լ�
	 * 
	 * @param canvas
	 */
	public void drawMe(Canvas canvas){
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setARGB(255, 247, 211, 66);
		paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		paint.setTextSize(16);
		// ��ǰ�÷�
		canvas.drawText("�û���" + user, 10, 20, paint);
		// ��ߵ÷�
		String timeStr = "ʱ�䣺" + time + " (" + winTime + ")";
		int textWidth = (int) paint.measureText(timeStr);
		int leftPos = canvas.getWidth() - textWidth - 10;
		canvas.drawText(timeStr, leftPos, 20, paint);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public int getWinTime() {
		return winTime;
	}

	public void setWinTime(int winTime) {
		this.winTime = winTime;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}
