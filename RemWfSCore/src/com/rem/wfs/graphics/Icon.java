package com.rem.wfs.graphics;

import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.inputs.ClickEvent;

public class Icon extends GraphicElement implements Iconic{

	private int id;
	protected boolean parentSelected;
	private IconListener listener;

	public Icon(int textureId, int frame, int layer, String description, int id){
		super(textureId, frame, layer);
		this.id = id;

	}
	public Icon(int textureId, int frame, int layer, String description, int id,
			float x, float y, float w, float h){
		super(textureId, frame, layer, x,y,w,h);
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
	public boolean onClick(ClickEvent event){

		if(dim.isWithin(event.getX(), event.getY())){
			if(listener!=null){
				if(event.getAction()==ClickEvent.ACTION_DOWN){
					listener.performOnClick(id, event);
				}
				else if(event.getAction()==ClickEvent.ACTION_UP){
					listener.performOnRelease(id, event);
				}
			}
			return super.onClick(event);
		}
		else return false;
	}
}
