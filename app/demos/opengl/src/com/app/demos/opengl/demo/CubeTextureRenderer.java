package com.app.demos.opengl.demo;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLSurfaceView.Renderer;

public class CubeTextureRenderer implements Renderer {
	
	private boolean mTranslucentBackground;
	private CubeTexture mCubeTexture;
	private float mAngle;

	public CubeTextureRenderer(boolean useTranslucentBackground, Bitmap texture) {
		mTranslucentBackground = useTranslucentBackground;
		mCubeTexture = new CubeTexture();
	}

	public void onDrawFrame(GL10 gl) {
		// �����Ļ����Ȼ���
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// ���õ�ǰ����ģʽ���Ӿ�����
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// ���õ�ǰ�۲����
		gl.glLoadIdentity();
		// ��������λ��
		gl.glTranslatef(0, 0, -3.0f);
		// ������ת��ʽ
		gl.glRotatef(mAngle, 0, 1, 0);
		gl.glRotatef(mAngle * 0.25f, 1, 0, 0);
		// �������ö������ɫ
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		// ����������
		mCubeTexture.draw(gl);
		// �Ƕ�����
		mAngle += 1.2f;
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		// ����ͶӰ����
		float ratio = (float) width / height;
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
		// ���ò��Ų����
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// ȫ������
		gl.glDisable(GL10.GL_DITHER);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// ���ñ���
		if (mTranslucentBackground) {
			gl.glClearColor(0, 0, 0, 0);
		} else {
			gl.glClearColor(1, 1, 1, 1);
		}
		// ��ʼ������
		mCubeTexture.init(gl);
	}

}
