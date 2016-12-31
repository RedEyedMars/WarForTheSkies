package com.rem.wfs;

import java.util.Iterator;
import java.util.Stack;

import com.rem.core.Hub;
import com.rem.core.gui.graphics.elements.BlankGraphicElement;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.gui.inputs.HoverEvent;
import com.rem.wfs.graphics.icons.Iconic;
import com.rem.wfs.menu.GraphicIconContainer;
import com.rem.wfs.menu.OverlayView;

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
		if(menu instanceof OverlayView){
			((OverlayView)menu).setPrimary();
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
			//Hub.handler.giveOnClick(menu);
			tree.addChild(menu);
			menu.reposition(menu.dim.getX(), menu.dim.getY());

		}
	}
	private void removeOverlay(GraphicElement toRemove){
		if(toRemove!=null){
			//Hub.handler.removeOnClick(toRemove);
			tree.removeChild(toRemove);
		}
	}
	@Override
	public boolean onClick(ClickEvent event){
		if(!overlays.isEmpty()){
			Iconic found = null;
			for(Iterator<Iconic> itr = overlays.peek().iterator();itr.hasNext();){
				Iconic icon = itr.next();
				if(icon.isWithin(event.getX(),event.getY())){
					found = icon;
					break;
				}
			}
			if(found!=null){
				if(found.getIconListener()!=null){
					if(event.getAction()==ClickEvent.ACTION_DOWN){
						found.getIconListener().performOnClick(found.getId(),event);
					}
					else {
						found.getIconListener().performOnRelease(found.getId(),event);
					}
				}
			}
			else {
				overlays.peek().clickNoIcon(event);
			}
			return true;
		}
		else {
			return super.onClick(event);
		}
	}
	
	@Override
	public boolean onHover(HoverEvent event){
		if(!overlays.isEmpty()){
			Iconic found = null;
			for(Iterator<Iconic> itr = overlays.peek().iterator();itr.hasNext();){
				Iconic icon = itr.next();
				if(icon.isWithin(event.getX(),event.getY())){
					found = icon;
					break;
				}
			}
			if(found!=null){
				if(found.getIconListener()!=null){
					found.getIconListener().performOnHover(found.getId(),event);
				}
			}
			else {
				overlays.peek().hoverNoIcon(event);
			}
			return true;
		}
		else {
			return super.onHover(event);
		}
	}
}
