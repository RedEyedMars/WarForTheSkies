package com.rem.wfs;

import java.util.Stack;

import com.rem.core.Hub;
import com.rem.core.gui.graphics.elements.BlankGraphicElement;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.wfs.graphics.icons.Iconic;
import com.rem.wfs.menu.GraphicIconContainer;

public class Game extends BlankGraphicElement{
	private Stack<GraphicIconContainer> overlays = new Stack<GraphicIconContainer>();

	public Game(){
		super();
		tree.addChild(Hub.map);
	}

	public void buildOverlay(GraphicIconContainer menu) {
		removeOverlay(overlays.isEmpty()?null:overlays.peek());
		overlays.push(menu);
		addOverlay(menu);
		for(Iconic icon:menu){
			icon.addIconListener(menu.getIconListener(icon));
		}
	}
	public void collapseOverlay(GraphicIconContainer menu){
		while(!overlays.isEmpty()&&
				overlays.peek()!=menu){

			Hub.handler.removeOnClick(overlays.pop());
		}
		GraphicElement toRemove = overlays.pop();
		removeOverlay(toRemove);
		addOverlay(overlays.isEmpty()?null:overlays.peek());
		for(Iconic icon:menu){
			icon.removeIconListener();
		}
	}
	private void addOverlay(GraphicIconContainer menu){
		if(menu!=null){
			Hub.handler.giveOnClick(menu);
			tree.addChild(menu);
			menu.reposition(menu.dim.getX(), menu.dim.getY());

		}
	}
	private void removeOverlay(GraphicElement toRemove){
		if(toRemove!=null){
			Hub.handler.removeOnClick(toRemove);
			tree.removeChild(toRemove);
		}
	}
}
