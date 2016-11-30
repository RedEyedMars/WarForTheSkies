package com.rem.wfs;

import com.rem.core.Hub;
import com.rem.core.gui.graphics.GraphicView;

public class Game extends GraphicView{
	public Game(){
		super();
		addChild(Hub.map);
	}
}
