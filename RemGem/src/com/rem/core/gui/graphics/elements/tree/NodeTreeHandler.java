package com.rem.core.gui.graphics.elements.tree;

import java.util.Iterator;

import com.rem.core.Hub;
import com.rem.core.gui.graphics.elements.GraphicElement;

public class NodeTreeHandler implements TreeHandler{

	private GraphicElement self;
	public NodeTreeHandler(GraphicElement self){
		this.self = self;
	}	

	@Override
	public void addChild(GraphicElement element) {
		if(self.getRoot()==null){
			if(self==Hub.view){
				element.addToDrawable();
			}
			element.setRoot(self);
		}
		else {
			if(self.getRoot()==Hub.view){
				element.addToDrawable();
			}
			element.setRoot(self.getRoot());
		}
		element.setOffset(self.createOffsetHandler(element));
	}

	@Override
	public void removeChild(GraphicElement element) {
		if(element.getRoot()==Hub.view){
			element.removeFromDrawable();
		}
	}
	
	@Override
	public GraphicElement getChild(int index) {
		return null;
	}
	

	@Override
	public Iterator<GraphicElement> iterator() {
		return new Iterator<GraphicElement>(){

			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public GraphicElement next() {
				return null;
			}

			@Override
			public void remove() {
				
			}};
	}


	@Override
	public int size() {
		return 0;
	}


}
