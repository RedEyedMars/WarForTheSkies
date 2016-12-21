package com.rem.wfs.graphics;

import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;
import com.rem.core.gui.graphics.elements.StaticGraphicElement;
import com.rem.core.gui.graphics.elements.StretchableGraphicElement;
import com.rem.core.gui.graphics.elements.dimension.DimensionHandler;
import com.rem.core.gui.graphics.elements.dimension.StaticHeightDimensionHandler;

public class LineBackground extends StretchableGraphicElement{
	private StaticGraphicElement left;
	private StaticGraphicElement right;

	public LineBackground(int textureId,int offset, int layer){
		super(textureId,offset,layer);
		left = new StaticGraphicElement(textureId,offset-1,layer);
		right = new StaticGraphicElement(textureId,offset+1,layer);
		tree.addChild(left);
		tree.addChild(right);

		resize(0f, DimensionHandler.getDefaultHeight(textureId));
	}

	@Override
	public DimensionHandler createDimensionHandler(int texture, float x, float y, float w, float h){
		return new StaticHeightDimensionHandler(texture,x,y,w,h);
	}

	@Override
	public void resize(float w, float h){		
		super.resize(w, h);
		reposition(dim.getX(),dim.getY());
	}

	@Override
	public OffsetHandler createOffsetHandler(final GraphicElement element){
		if(element == left){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return -element.dim.getWidth();
				}
			};
		}
		else if(element == right){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return dim.getWidth();
				}
			};			
		}
		else return super.createOffsetHandler(element);
	}
}

