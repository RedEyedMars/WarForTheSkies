package com.rem.core.gui.graphics;

import java.nio.FloatBuffer;

public class VisualBundle {
	private float x;
	private float y;
	private float w;
	private float h;
	

	protected int numberOfVertices;
	protected FloatBuffer vertexBuffer;
	private float angle;

	public VisualBundle(){
		
	}
	public void setDimensions(float x, float y, float w, float h, float angle){

		this.x = x;
		this.w = w;			
		this.y = y;
		this.h = h;
		this.angle = angle;
	}

	public void setShapeInfo(int numberOfVertices, FloatBuffer buffer) {
		this.numberOfVertices = numberOfVertices;
		this.vertexBuffer = buffer;
	}
	
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public float getWidth(){
		return w;
	}
	public float getHeight(){
		return h;
	}
	public float getAngle(){
		return angle;
	}
	public int getNumberOfVertices(){
		return numberOfVertices;
	}
	public FloatBuffer getVertexBuffer(){
		return vertexBuffer;
	}
}
