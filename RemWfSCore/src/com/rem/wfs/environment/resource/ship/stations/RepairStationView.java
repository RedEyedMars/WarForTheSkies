package com.rem.wfs.environment.resource.ship.stations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.gui.inputs.HoverEvent;
import com.rem.wfs.environment.resource.ship.DetailedShipIcon;
import com.rem.wfs.environment.resource.ship.SpaceShip;
import com.rem.wfs.environment.resource.ship.menu.SpaceShipListView;
import com.rem.wfs.graphics.icons.IconListener;
import com.rem.wfs.graphics.icons.Iconic;
import com.rem.wfs.menu.OverlayView;

public class RepairStationView extends OverlayView {

	private RepairSpaceStation station;
	private SpaceShipListView freeShipView;
	private SpaceShipListView assignedShipView;
	private List<List<SpaceShip>> freeShips;
	private List<SpaceShip> assignedShips;

	@SuppressWarnings("unchecked")
	public RepairStationView(
			RepairSpaceStation station,
			List<SpaceShip>... spaceShipList) {
		super("Repair Station",0.7f,0.7f);
		this.station = station;
		this.freeShips = new ArrayList<List<SpaceShip>>();
		this.assignedShips = new ArrayList<SpaceShip>();
		for(int i=0;i<spaceShipList.length;++i){
			this.freeShips.add(new ArrayList<SpaceShip>());
			for(int j=0;j<spaceShipList[i].size();++j){
				if(spaceShipList[i].get(j).getSpaceStationPosition()==null){
					this.freeShips.get(i).add(spaceShipList[i].get(j));
				}
				else {
					this.assignedShips.add(spaceShipList[i].get(j));
				}
			}
		}
		this.freeShipView = new SpaceShipListView("Unassigned",freeShips);		
		this.assignedShipView = new SpaceShipListView("Repairing",assignedShips);
		this.freeShipView.getBackground().resize(0.7f, this.freeShipView.getBackground().dim.getHeight());
		this.assignedShipView.getBackground().resize(0.7f, this.assignedShipView.getBackground().dim.getHeight());
		this.tree.addChild(freeShipView);
		this.tree.addChild(assignedShipView);
		
		this.reposition(0.15f, 0.15f);
	}

	@Override
	public OffsetHandler createOffsetHandler(final GraphicElement element){
		if(element==this.freeShipView){
			return new OffsetHandler(){
				@Override
				public float getY(){
					return assignedShipView.getBackground().dim.getHeight()+0.025f;
				}
			};
		}
		else return super.createOffsetHandler(element);

	}

	@Override
	public Iterator<Iconic> iterator() {
		final Iterator<Iconic> freeItr = freeShipView.iterator();
		final Iterator<Iconic> assignedItr = assignedShipView.iterator();
		return new Iterator<Iconic>(){

			@Override
			public boolean hasNext() {
				return freeItr.hasNext()||assignedItr.hasNext();
			}

			@Override
			public Iconic next() {
				if(freeItr.hasNext()){
					return freeItr.next();
				}
				else return assignedItr.next();
			}

			@Override
			public void remove() {				
			}			
		};
	}

	@Override
	public IconListener getIconListener(Iconic icon) {
		if(icon instanceof DetailedShipIcon){
			final SpaceShip ship = ((DetailedShipIcon)icon).getShip();
			final int assignedIndex = assignedShips.indexOf(ship);
			final IconListener slider;
			if(assignedIndex==-1){
				slider = freeShipView.getIconListener(icon);
			}
			else {
				slider = assignedShipView.getIconListener(icon);
			}

			return new IconListener(){
				@Override
				public void performOnHover(int id, HoverEvent event) {
					slider.performOnHover(id, event);
				}

				@Override
				public void performOnClick(int id, ClickEvent event) {
					if(event.getButton()==ClickEvent.MOUSE_RIGHT){
						slider.performOnClick(id, event);
					}
				}

				@Override
				public void performOnRelease(int id, ClickEvent event) {
					if(event.getButton()==ClickEvent.MOUSE_RIGHT){
						slider.performOnRelease(id, event);
					}
					else if(event.getButton()==ClickEvent.MOUSE_LEFT){
						if(assignedIndex==-1){
							
						}
						else {
							
						}
					}
				}

			};
		}
		else return null;
	}

	@Override
	public void clickNoIcon(ClickEvent event) {
		freeShipView.clickNoIcon(event);
		assignedShipView.clickNoIcon(event);
		super.clickNoIcon(event);
	}
	
	@Override
	public void hoverNoIcon(HoverEvent event) {
		freeShipView.hoverNoIcon(event);
		assignedShipView.hoverNoIcon(event);
	}

	//TODO on update make sure all the ships in the lists are still stationed on this hexagon./add ones that have arrived
}
