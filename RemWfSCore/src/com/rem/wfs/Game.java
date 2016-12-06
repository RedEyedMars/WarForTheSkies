package com.rem.wfs;

import java.util.Stack;

import com.rem.core.Hub;
import com.rem.core.gui.graphics.GraphicEntity;
import com.rem.core.gui.graphics.GraphicView;

public class Game extends GraphicView{
	private Stack<GraphicEntity> overlays = new Stack<GraphicEntity>();

	public Game(){
		super();
		addChild(Hub.map);
	}

	public void addOverlayMenu(GraphicEntity menu) {
		overlays.push(menu);
		Hub.handler.giveOnClick(menu);
		addChild(menu);
	}
	public void removeOverlayMenu(GraphicEntity menu){
		while(!overlays.isEmpty()&&
				overlays.peek()!=menu){
			
			Hub.handler.removeOnClick(overlays.pop());
		}
		Hub.handler.removeOnClick(menu);
		removeChild(menu);
	}
}
