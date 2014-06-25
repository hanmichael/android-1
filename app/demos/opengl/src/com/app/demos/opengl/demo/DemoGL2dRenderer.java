package com.app.demos.opengl.demo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView.Renderer;

public class DemoGL2dRenderer implements Renderer {
	
	static int one = 0x10000;
	
	private IntBuffer triangleBuffer;
	private IntBuffer quaterBuffer;
	private IntBuffer color1Buffer;
	private IntBuffer color2Buffer;
	
	private int[] vertices = new int[] {
		0,one,0,
		-one,-one,0,
		one,-one,0
	};
	private int[] quater = new int[] {
		one,one,0,
		-one,one,0,
		one,-one,0,
		-one,-one,0
	};
	private int[] color1 = new int[] {
		one,0,0,one,
		0,one,0,one,
		0,0,one,one
	};
	private int[] color2 = new int[] {
		one,0,0,0,
		one,one,0,0,
		one,one,one,0,
		0,one,one,0
	};

	@Override
	public void onDrawFrame(GL10 gl) {
		
		// �����Ļ����Ȼ���
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// ���õ�ǰ�۲����
		gl.glLoadIdentity();
		
		// �����ε�ByteBuffer
		ByteBuffer vbb1 = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb1.order(ByteOrder.nativeOrder());
		triangleBuffer = vbb1.asIntBuffer();
		triangleBuffer.put(vertices);
		triangleBuffer.position(0);
		// �ķ��ε�ByteBuffer
		ByteBuffer vbb2 = ByteBuffer.allocateDirect(quater.length * 4);
		vbb2.order(ByteOrder.nativeOrder());
		quaterBuffer = vbb2.asIntBuffer();
		quaterBuffer.put(quater);
		quaterBuffer.position(0);
		// ��ɫ1��ByteBuffer
		ByteBuffer cbb1 = ByteBuffer.allocateDirect(color1.length * 4);
		cbb1.order(ByteOrder.nativeOrder());
		color1Buffer = cbb1.asIntBuffer();
		color1Buffer.put(color1);
		color1Buffer.position(0);
		// ��ɫ2��ByteBuffer
		ByteBuffer cbb2 = ByteBuffer.allocateDirect(color2.length * 4);
		cbb2.order(ByteOrder.nativeOrder());
		color2Buffer = cbb2.asIntBuffer();
		color2Buffer.put(color2);
		color2Buffer.position(0);
		
		// �����Ļ����Ȼ���
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// ���õ�ǰ��ģ�͹۲����
		gl.glLoadIdentity();
		// �������ö���
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// ����������ɫ
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		// ����1.5��λ����������Ļ6.0
		gl.glTranslatef(-1.5f, 0.0f, -6.0f);
		// ����������
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, triangleBuffer);
		// ������������ɫ
		gl.glColorPointer(4, GL10.GL_FIXED, 0, color1Buffer);
		// ����������
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		
		// ���õ�ǰģ�͵Ĺ۲����
		gl.glLoadIdentity();
		// ����1.5��λ����������Ļ6.0
		gl.glTranslatef(1.5f, 0.0f, -6.0f);
		// ����������
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, quaterBuffer);
		// ������������ɫ
		gl.glColorPointer(4, GL10.GL_FIXED, 0, color2Buffer);
		// ����������
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		
		// ȡ����ɫ����
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		// ȡ����������
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		float ratio = (float) width / height;
		// ����OpenGL�����Ĵ�С
		gl.glViewport(0, 0, width, height);
		// ����ͶӰ����
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// ����ͶӰ����
		gl.glLoadIdentity();
		// �����ӿڵĴ�С
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// ���õ�ǰģ�͹۲����
		gl.glLoadIdentity();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// ����ϵͳ��͸�ӽ�������
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		// ��ɫ����
		gl.glClearColor(0, 0, 0, 0);
		// ������Ӱƽ��
		gl.glShadeModel(GL10.GL_SMOOTH);
		// ������Ȼ���
		gl.glClearDepthf(1.0f);
		// ������Ȳ���
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// ������Ȳ��Ե�����
		gl.glDepthFunc(GL10.GL_LEQUAL);
	}
}