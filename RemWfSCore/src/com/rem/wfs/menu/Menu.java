package com.rem.wfs.menu;

import java.util.ArrayList;
import java.util.List;

import com.rem.core.gui.graphics.GraphicView;
import com.rem.wfs.environment.hexagon.Hexagon;

public abstract class Menu extends GraphicView {
	protected static List<Hexagon> fallingStars = new ArrayList<Hexagon>();
	public Menu(){
		super();
		
		displayButtons();
		for(Hexagon star:fallingStars){
			addChild(star);
		}
	}
	protected abstract void displayButtons();
}
