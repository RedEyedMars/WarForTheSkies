package com.rem.wfs.graphics.icons;

import com.rem.core.gui.graphics.elements.tree.TreeHandler;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.wfs.graphics.LineBackground;

public class IconLineBackground extends LineBackground implements Iconic{



	private int id;
	protected boolean parentSelected;
	private IconListener listener;

	public IconLineBackground(int textureId, int offset, int layer, int id) {
		super(textureId, offset, layer);
		this.id = id;
	}
	

	public int getId() {
		return id;
	}

	public void setParentSelectedStatus(boolean parentSelected) {
		this.parentSelected = parentSelected;
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
	
	@Override
	public IconListener getIconListener(){
		return this.listener;
	}


	@Override
	public void addIconListener(IconListener iconListener) {
		this.listener = iconListener;
	}

	@Override
	public void removeIconListener() {
		this.listener = null;
	}

	@Override
	public void addToTree(TreeHandler addSelfToThisTree) {
		addSelfToThisTree.addChild(this);		
	}
}
