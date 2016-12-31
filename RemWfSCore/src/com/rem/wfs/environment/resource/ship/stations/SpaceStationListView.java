package com.rem.wfs.environment.resource.ship.stations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rem.core.Hub;
import com.rem.core.environment.Range;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.gui.inputs.HoverEvent;
import com.rem.wfs.Game;
import com.rem.wfs.environment.resource.ship.SpaceShip;
import com.rem.wfs.graphics.R;
import com.rem.wfs.graphics.icons.Icon;
import com.rem.wfs.graphics.icons.IconListener;
import com.rem.wfs.graphics.icons.Iconic;
import com.rem.wfs.menu.GraphicIconContainer;
import com.rem.wfs.menu.OverlayView;

public class SpaceStationListView extends OverlayView implements IconListener {

	private static final Range frames = new Range(0,4);
	private List<Iconic> icons = new ArrayList<Iconic>();
	private Iconic hoverIcon;
	
	public SpaceStationListView(final List<SpaceStation> spaceStations,final List<SpaceShip>... spaceShipList) {
		super("Stations", 0.6f,0.8f);
		
		StationOptionIcon icon = new StationOptionIcon("Repair Station",0){
			@Override
			protected GraphicIconContainer getChildView() {
				return new RepairStationView((RepairSpaceStation)spaceStations.get(0),spaceShipList);
			}};
		icons.add(icon);
		tree.addChild(icon);
		
		
		this.reposition(0.2f, 0.1f);
	}
	
	@Override
	public OffsetHandler createOffsetHandler(final GraphicElement element){
		if(element instanceof StationOptionIcon){
			final StationOptionIcon icon = (StationOptionIcon)element;
			return new OffsetHandler(){
				@Override
				public float getX(){
					return background.dim.getWidth()*0.1f;
				}
				@Override
				public float getY(){
					return background.dim.getHeight()*0.9f*icon.getId()/icons.size()+background.dim.getHeight()*0.05f;
				}
			};
		}
		else return super.createOffsetHandler(element);
	}

	@Override
	public Iterator<Iconic> iterator() {
		return icons.iterator();
	}

	@Override
	public IconListener getIconListener(Iconic icon) {
		return this;
	}

	@Override
	public void performOnHover(int id, HoverEvent event) {
		if(hoverIcon!=null){
			hoverIcon.setParentSelectedStatus(false);
		}
		hoverIcon = icons.get(id);
		hoverIcon.setParentSelectedStatus(true);
		
	}

	@Override
	public void performOnClick(int id, ClickEvent event) {
		
	}

	@Override
	public void performOnRelease(int id, ClickEvent event) {
		((StationOptionIcon)icons.get(id)).open();
	}

	
	private abstract static class StationOptionIcon extends Icon {

		public StationOptionIcon(String description, int id) {
			super(R.station_icons, frames.get(id), R.MID_LAYER, description, id);
		}

		public void open() {
			GraphicIconContainer child = getChildView();
			if(child!=null){
				((Game)Hub.view).buildOverlay(child);
			}
		}
		
		protected abstract GraphicIconContainer getChildView();
	}

	@Override
	public void hoverNoIcon(HoverEvent event) {
		if(hoverIcon!=null){
			hoverIcon.setParentSelectedStatus(false);
			hoverIcon=null;
		}
	}
}
