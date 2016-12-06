package com.rem.wfs.pc.gui.graphics;

import org.lwjgl.opengl.GL11;

import com.rem.core.gui.graphics.GraphicElement;



public class PCGraphicLineElement {

	protected static void draw(GraphicElement element){

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		// OpenGL 1.1 drawing routine
		GL11.glLineWidth(2);
		GL11.glColor3f(1f, 0f, 0f);
		GL11.glColor3f(0.5f,0.5f,1.0f);
		GL11.glBegin(GL11.GL_LINES);

		float[] p0 = new float[]{element.getX(),element.getY()};
		float[] p1 = new float[]{element.getX(),element.getY()+element.getHeight()*3f/4f};
		float[] p2 = new float[]{element.getX()+element.getWidth()*3f/4f,element.getY()+element.getHeight()};
		float[] p3 = new float[]{element.getX()+element.getWidth(),element.getY()+element.getHeight()};
		float[] q0 = calculateBezierPoint(0, p0, p1, p2, p3);

		for(int i = 1; i <= GraphicElement.SEGMENT_COUNT; i++)
		{
			float t = i / (float) GraphicElement.SEGMENT_COUNT;
			float[] q1 = calculateBezierPoint(t, p0, p1, p2, p3);

			GL11.glVertex3f(q0[0],  q0[1], 0.0f);
			GL11.glVertex3f(q1[0],  q1[1],  0.0f);
			q0 = q1;
		}
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	private static float[] calculateBezierPoint(float t,
			float[] p0, float[] p1, float[] p2, float[] p3)
	{
		float u = 1 - t;
		float tt = t*t;
		float uu = u*u;
		float uuu = uu * u;
		float ttt = tt * t;

		//first term
		float[] p = new float[]{uuu * p0[0],uuu * p0[1]};
		//second term
		p[0] += 3 * uu * t * p1[0];
		p[1] += 3 * uu * t * p1[1];
		//third term
		p[0] += 3 * u * tt * p2[0];
		p[1] += 3 * u * tt * p2[1];
		//fourth term
		p[0] += ttt * p3[0];
		p[1] += ttt * p3[1];

		return p;
	}
}
