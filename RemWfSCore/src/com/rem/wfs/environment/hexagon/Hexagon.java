package com.rem.wfs.environment.hexagon;

import com.rem.core.gui.graphics.ShapeHandler;
import com.rem.core.gui.graphics.elements.StretchableGraphicElement;

public class Hexagon extends StretchableGraphicElement{

	public Hexagon(
			int textureId, int initialFrame, int layer) {
		super(textureId, initialFrame, layer);
	}
	public Hexagon(
			int textureId, int initialFrame, int layer,
			float x, float y, float w, float h) {
		super(textureId, initialFrame, layer, x, y, w, h);
	}

	@Override
	protected ShapeHandler createShapeHandler(){
		return ShapeHandler.hexagon;
	}

}
