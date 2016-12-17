package com.rem.core.gui.graphics.elements.dimension;

import java.util.HashMap;
import java.util.Map;

import com.rem.core.gui.graphics.VisualBundle;

public class DimensionHandler {

	private static final Map<Integer,Float> defaultWidths = new HashMap<Integer,Float>();
	private static final Map<Integer,Float> defaultHeights = new HashMap<Integer,Float>();
	static {
		defaultWidths.put(-1, 0f);
		defaultHeights.put(-1, 0f);
	}
	public static void addTexture(Integer textureId, int pixelWidth, int pixelHeight){
		defaultWidths.put(textureId, pixelWidth/320.0f);
		defaultHeights.put(textureId, pixelHeight/320.0f);
	}
	public static float getDefaultWidth(Integer textureId){
		return defaultWidths.get(textureId);
	}
	public static float getDefaultHeight(Integer textureId){
		return defaultHeights.get(textureId);
	}
	
	protected float x;
	protected float y;
	protected float w;
	protected float h;
	protected float dw;
	protected float dh;
	public DimensionHandler(int textureId, float x, float y){
		this(textureId,x,y,defaultWidths.get(textureId),defaultHeights.get(textureId));
	}
	public DimensionHandler(int textureId, float x, float y,float w, float h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.dw = defaultWidths.get(textureId);
		this.dh = defaultHeights.get(textureId);
	}
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public void setVisual(VisualBundle bundle){
		bundle.setDimensions(x, y, dw, dh, 0f);
	}
	public float getWidth(){
		return w;
	}
	public float getHeight(){
		return h;
	}
	public boolean isWithin(float dx, float dy) {
		return dx>=x&&dx<x+getWidth()&&
			   dy>=y&&dy<y+getHeight();
	}
	public void resize(float w, float h) {		
		this.w = w;
		this.h = h;
	}

}
