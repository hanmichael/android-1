package com.man.plug;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * ʱ����ʾ
 */
public class MsgScore {
	
	/**
	 * ʱ��
	 */
	private int time;
	
	/**
	 * �÷�
	 */
	private int score;
	
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
		canvas.drawText("�÷֣�" + time, 10, 20, paint);
		// ��ߵ÷�
		String highScoreStr = "��߷֣�" + score;
		int textWidth = (int) paint.measureText(highScoreStr);
		int leftPos = canvas.getWidth() - textWidth - 10;
		canvas.drawText(highScoreStr, leftPos, 20, paint);
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
