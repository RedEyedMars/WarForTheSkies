package com.rem.core.gui.graphics.elements;

import com.rem.core.gui.graphics.elements.dimension.DimensionHandler;
import com.rem.core.gui.graphics.elements.dimension.StaticHeightDimensionHandler;

public class StaticHeightGraphicElement extends GraphicElement {
	public StaticHeightGraphicElement(int textureId, int initialFrame, int layer) {
		super(textureId, initialFrame, layer);
	}

	public StaticHeightGraphicElement(int textureId, int initialFrame, int layer, 
			float x, float y, float w, float h) {
		super(textureId, initialFrame, layer,
				x,y,w,h);
	}
	
	@Override
	protected DimensionHandler createDimensionHandler(int tex, float x, float y, float w, float h) {
		return new StaticHeightDimensionHandler(tex,x,y,w,h);
	} 

}
