package com.rem.wfs.environment.resource.ship.stations;

import com.rem.wfs.environment.resource.ResourceContainer;

public class RepairSpaceStation extends SpaceStation{

	private float repairPerSecond = 1f;
	public RepairSpaceStation(ResourceContainer container){
		super(SpaceStation.ID_REPAIR,0,container);
	}
	
	public void update(double secondsSinceLastUpdate){
		float increment = (float) (repairPerSecond*secondsSinceLastUpdate);
		for(int i=0;i<substations.size();++i){
			if(substations.get(i).getShip()!=null){
				if(!repair(increment,substations.get(i).getShip())){
					substations.get(i).removeShip();
				}
			}
		}
	}
}
