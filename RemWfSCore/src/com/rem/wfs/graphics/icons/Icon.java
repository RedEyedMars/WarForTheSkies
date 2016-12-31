package com.rem.wfs.graphics.icons;

import com.rem.core.gui.graphics.GraphicText;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.tree.TreeHandler;
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
	public IconListener getIconListener(){
		return this.handler.getListener();
	}

	@Override
	public void addToTree(TreeHandler addSelfToThisTree) {
		addSelfToThisTree.addChild(this);		
	}
	

}
