package com.rem.wfs.menu;

import java.util.ArrayList;
import java.util.List;

import com.rem.core.Hub;
import com.rem.core.gui.graphics.elements.BlankGraphicElement;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.gui.inputs.HoverEvent;
import com.rem.wfs.Game;
import com.rem.wfs.environment.resource.ship.DetailedShipIcon;
import com.rem.wfs.environment.resource.ship.SpaceShip;
import com.rem.wfs.graphics.Background;
import com.rem.wfs.graphics.IconListener;
import com.rem.wfs.graphics.R;

public class SpaceShipListView extends BlankGraphicElement {

	private static final int MOUSE_DOWN = 0;
	private static final int MOUSE_UP = 1;
	private static final int MOUSE_DRAG = 2;

	private Background background;
	private GraphicElement close;
	private List<ShipIconClickListener> icons = new ArrayList<ShipIconClickListener>();

	private int clickState = MOUSE_UP;
	private float clickX;
	private float clickY;
	public SpaceShipListView(
			List<SpaceShip>... spaceShipList){
		super();
		final SpaceShipListView self = this;

		background = new Background(R.background_2,R.MID_LAYER);
		background.resize(0.8f, 0.05f+0.1f*spaceShipList.length);
		tree.addChild(background);	

		for(int j=0;j<spaceShipList.length;++j){
			ShipIconClickListener listener = new ShipIconClickListener(this);
			icons.add(listener);
			for(int i=0;i<spaceShipList[j].size();++i){
				DetailedShipIcon icon = spaceShipList[j].get(i).getDetailedIcon(i);
				icon.setIconListener(listener);
				icon.setLayer(R.MID_LAYER);
				listener.add(icon);
				tree.addChild(icon);
			}
		}


		close = new GraphicElement(R.faces,0,R.MID_LAYER){
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
		this.reposition(0.1f,(1f-background.dim.getHeight())/2f);
	}
	@Override
	public OffsetHandler createOffsetHandler(final GraphicElement element){
		if(element instanceof DetailedShipIcon){
			final DetailedShipIcon icon = (DetailedShipIcon)element;
			final int depth = icon.getShip().getSpaceShipClassificationId();
			return new OffsetHandler(){
				@Override
				public float getX(){
					return icon.getId()*icon.dim.getWidth()+0.003f+icons.get(depth).scroll;
				}
				@Override
				public float getY(){
					return background.dim.getHeight()/2f-element.dim.getHeight()*(depth-1f);
				}
			};
		}
		else if(element == close){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return background.dim.getWidth();
				}
				@Override
				public float getY(){
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
			for(int j=0;j<icons.get(i).size();++j)
				icons.get(i).get(j).setVisible(
						icons.get(i).get(j).dim.getX()>=x&&
						icons.get(i).get(j).dim.getX()+icons.get(i).get(j).dim.getWidth()<=
						x+background.dim.getWidth()+0.005);
		}
	}

	private class ShipIconClickListener extends ArrayList<DetailedShipIcon>  implements IconListener {
		private static final long serialVersionUID = -1831917177718362055L;
		private SpaceShipListView view;
		private float scroll = 0f;
		public ShipIconClickListener(SpaceShipListView view){
			super();
			this.view = view;
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
					if(scroll>(background.dim.getWidth()-get(id).dim.getWidth()))
						scroll = background.dim.getWidth()-get(id).dim.getWidth();
					else if(scroll<-get(id).dim.getWidth()*(size()-1)){
						scroll = -get(id).dim.getWidth()*(size()-1);
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
				((Game)Hub.view).removeOverlayMenu(view);

				((Game)Hub.view).addOverlayMenu(new SpaceShipView(get(id).getShip(),view));
			}
			clickState = MOUSE_UP;
		}
		@Override
		public void performOnHoverOn(int id, HoverEvent event) {		
		}
		@Override
		public void performOnHoverOff(int id, HoverEvent event) {		
		}
	}
}
