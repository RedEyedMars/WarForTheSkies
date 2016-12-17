package com.rem.wfs.menu;

import java.util.ArrayList;
import java.util.List;

import com.rem.core.Hub;
import com.rem.core.gui.graphics.elements.BlankGraphicElement;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.wfs.Game;
import com.rem.wfs.environment.resource.ship.DetailedShipIcon;
import com.rem.wfs.environment.resource.ship.SpaceShip;
import com.rem.wfs.graphics.Background;
import com.rem.wfs.graphics.IconListener;
import com.rem.wfs.graphics.R;

public class SpaceShipListView extends BlankGraphicElement implements IconListener {

	private static final int MOUSE_DOWN = 0;
	private static final int MOUSE_UP = 1;
	private static final int MOUSE_DRAG = 2;
	
	private Background background;
	private GraphicElement close;
	private List<DetailedShipIcon> icons = new ArrayList<DetailedShipIcon>();
	
	private int clickState = MOUSE_UP;
	private float clickX;
	private float clickY;
	private float scroll = 0f; 
	public SpaceShipListView(List<SpaceShip> spaceShipList){
		super();
		final SpaceShipListView self = this;

		background = new Background(R.background_1,R.MID_LAYER);
		background.resize(0.8f, 0.15f);
		tree.addChild(background);	
		
		for(int i=0;i<spaceShipList.size();++i){
			DetailedShipIcon icon = spaceShipList.get(i).getDetailedIcon(i);
			icon.setIconListener(this);
			icon.setLayer(R.MID_LAYER);
			icons.add(icon);
			tree.addChild(icon);
		}

		
		close = new GraphicElement(R.faces_traits,31,R.MID_LAYER){
			@Override
			public boolean onClick(ClickEvent event){
				if(dim.isWithin(event.getX(), event.getY())){
					if(event.getAction()==ClickEvent.ACTION_UP){
						((Game)Hub.view).removeOverlayMenu(self);
					}
					return super.onClick(event);
				}
				else return false;
			}
		};
		tree.addChild(close);
		this.reposition(0.1f,0.4f);
	}
	@Override
	public OffsetHandler createOffsetHandler(final GraphicElement element){
		if(element instanceof DetailedShipIcon){
			final DetailedShipIcon icon = (DetailedShipIcon)element;
			return new OffsetHandler(){
				@Override
				public float getX(int index){
					return icon.getId()*icon.dim.getWidth()+0.003f+scroll;
				}
				@Override
				public float getY(int index){
					return background.dim.getHeight()/2f-element.dim.getHeight()/2f;
				}
			};
		}
		else if(element == close){
			return new OffsetHandler(){
				@Override
				public float getX(int index){
					return background.dim.getWidth();
				}
				@Override
				public float getY(int index){
					return background.dim.getHeight();
				}
			};
		}
		else return super.createOffsetHandler(element);
	}
	
	@Override
	public void reposition(float x, float y){
		super.reposition(x, y);
		for(int i=0;i<icons.size();++i){
			icons.get(i).setVisible(
					icons.get(i).dim.getX()>=x&&
					icons.get(i).dim.getX()+icons.get(i).dim.getWidth()<=x+background.dim.getWidth()+0.003);			
		}
	}

	@Override
	public void performOnClick(int id, ClickEvent event) {
		if(clickState==MOUSE_UP){
			clickState = MOUSE_DOWN;
			clickX = event.getX();
			clickY = event.getY();
		}
		else if(clickState==MOUSE_DOWN||clickState==MOUSE_DRAG){
			double distanceFromStart = Math.sqrt(Math.pow(event.getX()-clickX, 2)+Math.pow(event.getY()-clickY, 2));
			if(distanceFromStart>0.01f){
				clickState = MOUSE_DRAG;
				scroll += event.getX()-clickX;
				if(scroll>(background.dim.getWidth()-icons.get(id).dim.getWidth()))
					scroll = background.dim.getWidth()-icons.get(id).dim.getWidth();
				else if(scroll<-icons.get(id).dim.getWidth()*(icons.size()-1)){
					scroll = -icons.get(id).dim.getWidth()*(icons.size()-1);
				}
				reposition(dim.getX(),dim.getY());		
				clickX = event.getX();
				clickY = event.getY();
			}
		}
	}

	@Override
	public void performOnRelease(int id, ClickEvent event) {
		if(clickState == MOUSE_DOWN){
			((Game)Hub.view).removeOverlayMenu(this);
			//((Game)Hub.view).addOverlayMenu(new PersonelView(icons.get(id).getShip(),this));
		}
		clickState = MOUSE_UP;
	}
}
