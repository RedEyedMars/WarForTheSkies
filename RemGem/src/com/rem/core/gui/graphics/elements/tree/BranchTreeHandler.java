package com.rem.core.gui.graphics.elements.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rem.core.gui.graphics.elements.GraphicBundle;
import com.rem.core.gui.graphics.elements.GraphicElement;

public class BranchTreeHandler extends NodeTreeHandler{

	private List<GraphicElement> children = new ArrayList<GraphicElement>();
	private List<GraphicBundle> offsetHandlers = new ArrayList<GraphicBundle>();

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
		this.offsetHandlers.add(new GraphicBundle(element,self.createOffsetHandler(element)));
		this.children.add(element);
	}
	
	@Override
	public void removeChild(GraphicElement element){
		super.removeChild(element);
		int indexOf = children.indexOf(element);
		if(indexOf!=-1){
			this.offsetHandlers.remove(indexOf);
			this.children.remove(indexOf);
		}
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

	@Override
	public Iterable<GraphicBundle> getBundles() {
		return new Iterable<GraphicBundle>(){
			@Override
			public Iterator<GraphicBundle> iterator() {
				return new Iterator<GraphicBundle>(){
					private int index = 0;
					@Override
					public boolean hasNext() {
						return index<offsetHandlers.size();
					}

					@Override
					public GraphicBundle next() {
						return offsetHandlers.get(index++);
					}

					@Override
					public void remove() {
						offsetHandlers.remove(index);
					}};
			}};
	}
}
