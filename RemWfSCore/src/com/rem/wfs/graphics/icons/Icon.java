package com.rem.wfs.graphics.icons;

import com.rem.core.gui.graphics.GraphicText;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.tree.TreeHandler;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.gui.inputs.HoverEvent;
import com.rem.wfs.graphics.R;

public class Icon extends GraphicElement implements Iconic{

	private int id;
	protected boolean parentSelected;
	private GraphicText description;
	private IconHandler handler = new IconHandler(null);

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

	@Override
	public void addIconListener(IconListener listener) {
		this.handler = new IconHandler(this.handler);
		this.handler.setListener(listener);
	}
	@Override
	public void removeIconListener() {
		this.handler = this.handler.getPrevious();
	}

	public void setDescription(String name) {
		this.description.change(name);
	}
	
	@Override
	public boolean onHover(HoverEvent event){
		if(handler.getListener()!=null){
			if(dim.isWithin(event.getX(), event.getY())){
				handler.getListener().performOnHoverOn(id, event);
				return super.onHover(event);
			}
			else {
				handler.getListener().performOnHoverOff(id, event);
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
			if(handler.getListener()!=null){
				if(event.getAction()==ClickEvent.ACTION_DOWN){
					handler.getListener().performOnClick(id, event);
				}
				else if(event.getAction()==ClickEvent.ACTION_UP){
					handler.getListener().performOnRelease(id, event);
				}
			}
			return super.onClick(event);
		}
		else return false;
	}
	@Override
	public void addToTree(TreeHandler addSelfToThisTree) {
		addSelfToThisTree.addChild(this);		
	}
	

}
