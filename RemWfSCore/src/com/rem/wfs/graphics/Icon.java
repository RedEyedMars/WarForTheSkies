package com.rem.wfs.graphics;

import com.rem.core.gui.graphics.GraphicText;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.gui.inputs.HoverEvent;

public class Icon extends GraphicElement implements Iconic{

	private int id;
	protected boolean parentSelected;
	private IconListener listener;
	private GraphicText description;

	public Icon(int textureId, int frame, int layer, String description, int id){
		super(textureId, frame, layer);
		this.id = id;
		this.description = new GraphicText(R.arial,description,layer);
		this.description.setVisible(false);

	}
	public Icon(int textureId, int frame, int layer, String description, int id,
			float x, float y, float w, float h){
		super(textureId, frame, layer, x,y,w,h);
		this.id = id;
		this.description = new GraphicText(R.arial,description,layer);
		this.description.setVisible(false);

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

	public void setDescription(String name) {
		this.description.change(name);
	}
	
	@Override
	public boolean onHover(HoverEvent event){

		if(listener!=null){
			if(dim.isWithin(event.getX(), event.getY())){
				listener.performOnHoverOn(id, event);
				return super.onHover(event);
			}
			else {
				listener.performOnHoverOff(id, event);
				return false;
			}
		}
		else {
			return super.onHover(event);
		}
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
