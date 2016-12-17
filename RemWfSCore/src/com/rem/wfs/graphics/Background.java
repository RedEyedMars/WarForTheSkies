package com.rem.wfs.graphics;

import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;
import com.rem.core.gui.graphics.elements.StaticHeightGraphicElement;
import com.rem.core.gui.graphics.elements.StaticWidthGraphicElement;
import com.rem.core.gui.graphics.elements.StretchableGraphicElement;
import com.rem.core.gui.graphics.elements.dimension.DimensionHandler;

public class Background extends StretchableGraphicElement{
	public Background(int textureId, int layer){
		super(textureId,5,layer);
		tree.addChild(new GraphicElement(textureId,0,layer));
		tree.addChild(new StaticHeightGraphicElement(textureId,1,layer));
		tree.addChild(new GraphicElement(textureId,2,layer));
		tree.addChild(new StaticWidthGraphicElement(textureId,4,layer));
		tree.addChild(new StaticWidthGraphicElement(textureId,6,layer));
		tree.addChild(new GraphicElement(textureId,8,layer));
		tree.addChild(new StaticHeightGraphicElement(textureId,9,layer));
		tree.addChild(new GraphicElement(textureId,10,layer));		
	}

	@Override
	public void resize(float w, float h){
		super.resize(w, h);
		reposition(dim.getX(),dim.getY());
	}

	@Override
	public OffsetHandler createOffsetHandler(final GraphicElement element){
		return new BackgroundOffsetHandler();
	}
	protected class BackgroundOffsetHandler extends OffsetHandler{
		@Override
		public float getX(int index){
			if(index==0||index==3||index==5){ //East border and corners.
				return -DimensionHandler.getDefaultWidth(getTexture());
			}
			else if(index==1||index==6){//North and South borders
				return 0f;
			}
			else if(index==2||index==4||index==7){//West border and corners.
				return dim.getWidth();
			}
			else return super.getX(index);
		}
		@Override
		public float getY(int index){
			if(index==0||index==1||index==2){//north border and corners
				return dim.getHeight();
			}
			else if(index==3||index==4){//East and west borders
				return 0f;
			}
			else if(index==5||index==6||index==7){//south border and corners;
				return -DimensionHandler.getDefaultHeight(getTexture());
			}
			else return super.getY(index);
		}
	}
}
