package com.rem.core.gui.graphics;

import java.nio.FloatBuffer;

public class VisualBundle {
	private float x;
	private float y;
	private float w;
	private float h;
	

	protected int vertexNumber = 4;
	protected FloatBuffer vertexBuffer;
	private float angle;

	public VisualBundle(float x, float y, float w, float h, float angle, int vertexNumber, FloatBuffer vertexBuffer){

		if(w<0){
			this.x = x+w;
			this.w = -w;
		}
		else {
			this.x = x;
			this.w = w;			
		}
		if(h<0){
			this.y = y+h;
			this.h = -h;
		}
		else {
			this.y = y;
			this.h = h;
		}
		this.angle = angle;
		this.vertexNumber = vertexNumber;
		this.vertexBuffer = vertexBuffer;
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
		return vertexNumber;
	}
	public FloatBuffer getVertexBuffer(){
		return vertexBuffer;
	}
}
