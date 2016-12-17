package com.rem.core.gui.graphics.elements.dimension;

import com.rem.core.gui.graphics.VisualBundle;

public class StretchableDimensions extends DimensionHandler{

	public StretchableDimensions(int tex,float x, float y, float w, float h) {
		super(tex, x, y, w, h);
	}

	@Override
	public void setVisual(VisualBundle bundle){
		bundle.setDimensions(x, y, w, h, 0f);
	}
}
