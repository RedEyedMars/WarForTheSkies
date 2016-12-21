package com.rem.core.gui.graphics.elements.tree;

import java.util.Iterator;

import com.rem.core.Hub;
import com.rem.core.gui.graphics.elements.GraphicBundle;
import com.rem.core.gui.graphics.elements.GraphicElement;
public class NodeTreeHandler implements TreeHandler{

	protected GraphicElement self;
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

	@Override
	public Iterable<GraphicBundle> getBundles() {
		return new Iterable<GraphicBundle>(){

			@Override
			public Iterator<GraphicBundle> iterator() {
				return new Iterator<GraphicBundle>(){

					@Override
					public boolean hasNext() {
						return false;
					}

					@Override
					public GraphicBundle next() {
						return null;
					}

					@Override
					public void remove() {
						
					}};
			}};
	}


}
