package com.rem.core.gui.graphics.elements.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rem.core.gui.graphics.elements.GraphicElement;

public class BranchTreeHandler extends NodeTreeHandler{

	private List<GraphicElement> children = new ArrayList<GraphicElement>();

	public BranchTreeHandler(GraphicElement self){
		super(self);
	}

	@Override
	public int size(){
		return children.size();
	}

	@Override
	public void addChild(GraphicElement element) {
		super.addChild(element);
		this.children.add(element);
	}
	
	@Override
	public void removeChild(GraphicElement element){
		super.removeChild(element);
		this.children.remove(element);
	}

	@Override
	public GraphicElement getChild(int index) {
		return children.get(index);
	}

	@Override
	public Iterator<GraphicElement> iterator() {
		return new Iterator<GraphicElement>(){
			private int index = 0;
			@Override
			public boolean hasNext() {
				return index<children.size();
			}

			@Override
			public GraphicElement next() {
				return children.get(index++);
			}

			@Override
			public void remove() {
				children.remove(index);
			}
			
		};
	}

}
