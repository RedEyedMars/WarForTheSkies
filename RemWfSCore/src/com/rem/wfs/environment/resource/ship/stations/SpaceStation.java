package com.rem.wfs.environment.resource.ship.stations;

import java.util.ArrayList;
import java.util.List;
import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.SpaceResource;
import com.rem.wfs.environment.resource.material.Material;
import com.rem.wfs.environment.resource.ship.ShipStatistic;
import com.rem.wfs.environment.resource.ship.SpaceShip;

public class SpaceStation {
	
	public static final int ID_REPAIR = 0;
	public static final int ID_REFUEL = 1;
	public static final int ID_RESQUAD = 2;
	public static final int ID_REDESTINE = 3;
	
	private ResourceContainer container;
	private int id;
	private int size;
	protected List<SpaceStationPosition> substations = new ArrayList<SpaceStationPosition>();
	public SpaceStation(int id, int size, ResourceContainer container){
		this.id = id;
		this.size = size;
		this.container = container;
		this.createPosition(0);
	}

	public SpaceStationPosition createPosition(int position) {
		while(position>=substations.size()){
			substations.add(new SpaceStationPosition(id,substations.size()));
		}
		return substations.get(position);
	}
	
	public boolean addSpaceShip(int position, SpaceShip ship){
		if(position>size){
			return false;
		}
		else {
			SpaceStationPosition station = createPosition(position);
			station.associateSpaceShip(ship);
			return true;
		}
	}
	
	/**
	 * 
	 * @param increment
	 * @param source
	 * @param sink
	 * @return false if the sink has been filled (sink's value == sink's limit), true otherwise.
	 */
	protected boolean fill(float increment, SpaceResource<?> source, ShipStatistic sink){
		if(sink.getValue()>=sink.getLimit())return false;
		if(source.getValue()>increment){			
			source.setValue(source.getValue()-(increment-sink.add(increment)));
			return sink.getValue()<sink.getLimit();
		}
		else return true;
	}
	protected boolean repair(float increment, SpaceShip spaceShip){
		return fill(increment, container.getResource(ResourceContainer.MATERIAL_ID, Material.ID_METAL),spaceShip.getHp());
	}
	protected boolean refuel(float increment, SpaceShip spaceShip){
		return fill(increment, container.getResource(ResourceContainer.MATERIAL_ID, Material.ID_FUEL),spaceShip.getFuel());
	}
}
