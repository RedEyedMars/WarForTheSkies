package com.rem.wfs;

import com.rem.core.gui.graphics.GraphicElement;
import com.rem.core.gui.graphics.GraphicEntity;

public class Icon extends GraphicEntity {

	private float id;
	protected boolean parentSelected;

	public Icon(GraphicElement graphicElement, String description, int id){
		super(graphicElement);
		this.id = id;
		
	}

	public float getId() {
		return id;
	}

	public void setParentSelectedStatus(boolean parentSelected) {
		this.parentSelected = parentSelected;
	}

}
