package com.rem.wfs.graphics;

import com.rem.core.gui.graphics.GraphicElement;
import com.rem.core.gui.graphics.GraphicEntity;

public class Background extends GraphicEntity{
	private static final float staticSize = 0.1f;
	public Background(int textureId, int layer){
		super(new GraphicElement(textureId,5,layer));
		addChild(new GraphicEntity(new GraphicElement(textureId,0,0f,0f,staticSize,staticSize,layer)));
		addChild(new GraphicEntity(new GraphicElement(textureId,1,0f,0f,staticSize,staticSize,layer)));
		addChild(new GraphicEntity(new GraphicElement(textureId,2,0f,0f,staticSize,staticSize,layer)));
		addChild(new GraphicEntity(new GraphicElement(textureId,4,0f,0f,staticSize,staticSize,layer)));
		addChild(new GraphicEntity(new GraphicElement(textureId,6,0f,0f,staticSize,staticSize,layer)));
		addChild(new GraphicEntity(new GraphicElement(textureId,8,0f,0f,staticSize,staticSize,layer)));
		addChild(new GraphicEntity(new GraphicElement(textureId,9,0f,0f,staticSize,staticSize,layer)));
		addChild(new GraphicEntity(new GraphicElement(textureId,10,0f,0f,staticSize,staticSize,layer)));		
	}
	
	@Override
	public void resize(float w, float h){
		super.resize(w, h);
		getChild(0).resize(staticSize, staticSize);
		getChild(1).resize(w, staticSize);
		getChild(2).resize(staticSize, staticSize);
		getChild(3).resize(staticSize, h);
		getChild(4).resize(staticSize, h);
		getChild(5).resize(staticSize, staticSize);
		getChild(6).resize(w, staticSize);
		getChild(7).resize(staticSize, staticSize);
		
		reposition(getX(),getY());
	}
	
	@Override
	public float offsetX(int index){
		switch(index){
		case 0:return -staticSize;
		case 1:return 0f;
		case 2:return getWidth();
		case 3:return -staticSize;
		case 4:return getWidth();
		case 5:return -staticSize;
		case 6:return 0f;
		case 7:return getWidth();
		}
		return super.offsetX(index);
	}
	
	@Override
	public float offsetY(int index){
		switch(index){
		case 0:return getHeight();
		case 1:return getHeight();
		case 2:return getHeight();
		case 3:return 0;
		case 4:return 0;
		case 5:return -staticSize;
		case 6:return -staticSize;
		case 7:return -staticSize;
		}
		return super.offsetY(index);
	}
}
