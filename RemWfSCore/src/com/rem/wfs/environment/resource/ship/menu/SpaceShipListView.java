package com.rem.wfs.environment.resource.ship.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rem.core.Hub;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.gui.inputs.HoverEvent;
import com.rem.wfs.Game;
import com.rem.wfs.environment.resource.ship.DetailedShipIcon;
import com.rem.wfs.environment.resource.ship.SpaceShip;
import com.rem.wfs.graphics.R;
import com.rem.wfs.graphics.icons.IconListener;
import com.rem.wfs.graphics.icons.Iconic;
import com.rem.wfs.menu.OverlayView;

public class SpaceShipListView extends OverlayView {

	private static final int MOUSE_DOWN = 0;
	private static final int MOUSE_UP = 1;
	private static final int MOUSE_DRAG = 2;

	private List<ShipIconClickListener> icons = new ArrayList<ShipIconClickListener>();
	private boolean isEmpty = false;

	private int clickState = MOUSE_UP;
	private float clickX;
	private float clickY;
	private ShipIconClickListener clickLine = null;
	private DetailedShipIcon hoverIcon;
	private int withinId = -1;
	public SpaceShipListView(
			String name,
			final List<SpaceShip>... spaceShipList){
		this(name,spaceShipList.length,new Iterator<List<SpaceShip>>(){
			int i=0;
			@Override
			public boolean hasNext() {
				return i<spaceShipList.length;
			}

			@Override
			public List<SpaceShip> next() {
				return spaceShipList[i++];
			}

			@Override
			public void remove() {
			}});
	}

	public SpaceShipListView(
			String name,
			List<List<SpaceShip>> spaceShipList){
		this(name,spaceShipList.size(),spaceShipList.iterator());
	}

	private SpaceShipListView(
			String name,
			int length,
			Iterator<List<SpaceShip>> ssItr){
		super(name, 0.8f, 0.05f+0.1f*length);
		isEmpty = !ssItr.hasNext();
		while(ssItr.hasNext()){
			List<SpaceShip> list = ssItr.next();
			ShipIconClickListener listener = new ShipIconClickListener();
			icons.add(listener);
			for(int i=0;i<list.size();++i){
				DetailedShipIcon icon = list.get(i).getDetailedIcon(i);
				icon.setLayer(R.MID_LAYER);
				listener.add(icon);
				tree.addChild(icon);
			}
		}
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
		private float scroll = 0f;

		@Override
		public void performOnClick(int id, ClickEvent event) {
			if(clickState==MOUSE_UP){
				withinId = id;
				clickLine = this;
				clickState = MOUSE_DOWN;
				clickX = event.getX();
				clickY = event.getY();
			}
			else if(clickState==MOUSE_DOWN||clickState==MOUSE_DRAG){
				double distanceFromStart = Math.sqrt(Math.pow(event.getX()-clickX, 2)+Math.pow(event.getY()-clickY, 2));
				if(distanceFromStart>0.01f){
					clickState = MOUSE_DRAG;
					scroll += event.getX()-clickX;
					if(scroll>(background.dim.getWidth()-get(withinId).dim.getWidth()))
						scroll = background.dim.getWidth()-get(withinId).dim.getWidth();
					else if(scroll<-get(withinId).dim.getWidth()*(size()-1)){
						scroll = -get(withinId).dim.getWidth()*(size()-1);
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
				((Game)Hub.view).buildOverlay(
						new SpaceShipView(
								get(withinId).getShip().getResource().getName(),
								get(withinId).getShip()));
			}
			clickState = MOUSE_UP;
			withinId = -1;
			clickLine = null;
		}
		@Override
		public void performOnHover(int id, HoverEvent event) {
			if(hoverIcon!=null){
				hoverIcon.setParentSelectedStatus(false);
			}
			hoverIcon = get(id);
			hoverIcon.setParentSelectedStatus(true);
			
		}
	}

	@Override
	public Iterator<Iconic> iterator() {
		if(isEmpty){
			return new Iterator<Iconic>(){
				@Override
				public boolean hasNext() {
					return false;
				}
				@Override
				public Iconic next() {
					return null;
				}
				@Override
				public void remove() {				
				}

			};
		}
		final Iterator<ShipIconClickListener> itr = icons.iterator();
		return new Iterator<Iconic>(){
			private Iterator<DetailedShipIcon> iconItr = itr.next().iterator();

			@Override
			public boolean hasNext() {
				return itr.hasNext()||iconItr.hasNext();
			}

			@Override
			public Iconic next() {
				if(iconItr.hasNext()){
					return iconItr.next();
				}
				else {
					iconItr = itr.next().iterator();
					return next();
				}
			}

			@Override
			public void remove() {				
			}

		};
	}
	@Override
	public IconListener getIconListener(Iconic icon) {
		if(icon instanceof DetailedShipIcon){
			return icons.get(((DetailedShipIcon)icon).getShip().getSpaceShipClassificationId());
		}
		else return null;
	}

	@Override
	public void clickNoIcon(ClickEvent event) {
		if(clickLine!=null){
			if(event.getAction()==ClickEvent.ACTION_DOWN){
				clickLine.performOnClick(withinId, event);
			}
			else if(event.getAction()==ClickEvent.ACTION_UP){
				clickLine.performOnRelease(withinId, event);
			}
		}
		else {
			super.clickNoIcon(event);
		}
	}

	@Override
	public void hoverNoIcon(HoverEvent event) {
		if(hoverIcon!=null){
			hoverIcon.setParentSelectedStatus(false);
			hoverIcon = null;
		}
	}
}
