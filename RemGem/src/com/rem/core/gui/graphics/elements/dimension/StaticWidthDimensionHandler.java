package com.rem.core.gui.graphics.elements.dimension;

import com.rem.core.gui.graphics.VisualBundle;

public class StaticWidthDimensionHandler extends DimensionHandler{

	public StaticWidthDimensionHandler(int tex,float x, float y, float w, float h) {
		super(tex, x, y, w, h);
	}

	@Override
	public void setVisual(VisualBundle bundle){
		bundle.setDimensions(x, y, dw, h, 0f);
	}
}
