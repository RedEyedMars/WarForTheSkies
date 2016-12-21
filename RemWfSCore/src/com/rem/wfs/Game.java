package com.rem.wfs;

import java.util.Stack;

import com.rem.core.Hub;
import com.rem.core.gui.graphics.elements.BlankGraphicElement;
import com.rem.core.gui.graphics.elements.GraphicElement;

public class Game extends BlankGraphicElement{
	private Stack<GraphicElement> overlays = new Stack<GraphicElement>();

	public Game(){
		super();
		tree.addChild(Hub.map);
	}

	public void addOverlayMenu(GraphicElement menu) {
		overlays.push(menu);
		Hub.handler.giveOnClick(menu);
		tree.addChild(menu);
		menu.reposition(menu.dim.getX(), menu.dim.getY());
	}
	public void removeOverlayMenu(GraphicElement menu){
		while(!overlays.isEmpty()&&
				overlays.peek()!=menu){
			
			Hub.handler.removeOnClick(overlays.pop());
		}
		GraphicElement toRemove = overlays.pop();
		Hub.handler.removeOnClick(toRemove);
		tree.removeChild(toRemove);
	}
}
