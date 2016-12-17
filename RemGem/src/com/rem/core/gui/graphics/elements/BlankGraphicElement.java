package com.rem.core.gui.graphics.elements;

import com.rem.core.gui.graphics.R;
import com.rem.core.gui.graphics.VisualBundle;
import com.rem.core.gui.graphics.elements.dimension.DimensionHandler;
import com.rem.core.gui.graphics.elements.dimension.StretchableDimensions;

public class BlankGraphicElement extends GraphicElement{

	public BlankGraphicElement(float x, float y, float initialWidth, float initialHeight) {
		super(-1,-1,R.BOT_LAYER,
				x,y, initialWidth, initialHeight);
	}
	public BlankGraphicElement(float x, float y) {
		super(-1,-1,R.BOT_LAYER,
				x,y,1f, 1f);
	}
	public BlankGraphicElement() {
		super(-1,-1,R.BOT_LAYER,
				0f,0f,1f, 1f);
	}
	public boolean draw(VisualBundle bundle){
		return false;
	}

	@Override
	protected DimensionHandler createDimensionHandler(int tex,float x, float y, float w, float h) {
		return new StretchableDimensions(tex,x,y,w,h);
	} 
}
