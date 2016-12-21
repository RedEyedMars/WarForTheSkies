package com.rem.core.gui.graphics.elements;

public class GraphicBundle {

	public final GraphicElement element;
	public final OffsetHandler offset;
	public GraphicBundle(GraphicElement element, OffsetHandler offset){
		this.element = element;
		this.offset = offset;
	}
}
