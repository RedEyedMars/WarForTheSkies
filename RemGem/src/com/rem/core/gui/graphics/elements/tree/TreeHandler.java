package com.rem.core.gui.graphics.elements.tree;

import com.rem.core.gui.graphics.elements.GraphicBundle;
import com.rem.core.gui.graphics.elements.GraphicElement;

public interface TreeHandler extends Iterable<GraphicElement>{
	public void addChild(GraphicElement element);
	public void removeChild(GraphicElement element);
	public GraphicElement getChild(int index);
	public int size();
	public Iterable<GraphicBundle> getBundles();
}
