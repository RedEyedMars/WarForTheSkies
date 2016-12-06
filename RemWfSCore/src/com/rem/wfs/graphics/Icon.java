package com.rem.wfs.graphics;

import com.rem.core.gui.graphics.GraphicElement;
import com.rem.core.gui.graphics.GraphicEntity;
import com.rem.core.gui.inputs.ClickEvent;

public class Icon extends GraphicEntity {

	private int id;
	protected boolean parentSelected;
	private IconListener listener;

	public Icon(GraphicElement graphicElement, String description, int id){
		super(graphicElement);
		this.id = id;
		
	}

	public int getId() {
		return id;
	}

	public void setParentSelectedStatus(boolean parentSelected) {
		this.parentSelected = parentSelected;
	}
	
	public void setIconListener(IconListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void performOnClick(ClickEvent event){
		if(listener!=null){
			listener.performOnClick(id, event);
		}
		super.performOnClick(event);
	}
	@Override
	public void performOnRelease(ClickEvent event){
		if(listener!=null){
			listener.performOnRelease(id, event);
		}
		super.performOnRelease(event);
	}

}
