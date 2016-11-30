package com.rem.wfs.environment.hexagon;

import com.rem.core.gui.graphics.GraphicElement;
import com.rem.core.gui.graphics.GraphicEntity;

public class Hexagon extends GraphicEntity{


	public Hexagon(GraphicElement graphicElement) {
		super(graphicElement);
		this.setShape(GraphicElement.SHAPE_HEXAGON);
	}

}
