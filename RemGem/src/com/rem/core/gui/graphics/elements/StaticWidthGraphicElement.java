package com.rem.core.gui.graphics.elements;

import com.rem.core.gui.graphics.elements.dimension.DimensionHandler;
import com.rem.core.gui.graphics.elements.dimension.StaticWidthDimensionHandler;

public class StaticWidthGraphicElement extends GraphicElement {
	public StaticWidthGraphicElement(int textureId, int initialFrame, int layer) {
		super(textureId, initialFrame, layer);
	}

	public StaticWidthGraphicElement(int textureId, int initialFrame, int layer, 
			float x, float y, float w, float h) {
		super(textureId, initialFrame, layer,
				x,y,w,h);
	}
	
	@Override
	protected DimensionHandler createDimensionHandler(int tex, float x, float y, float w, float h) {
		return new StaticWidthDimensionHandler(tex,x,y,w,h);
	} 

}
