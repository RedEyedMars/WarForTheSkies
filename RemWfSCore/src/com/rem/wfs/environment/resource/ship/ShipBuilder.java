package com.rem.wfs.environment.resource.ship;

import com.rem.core.environment.Range;
import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.material.Material;

public class ShipBuilder {

	private Range hp;
	private Range fuel;
	protected int metalIncrementAmount = 1;
	protected int fuelIncrementAmount = 1;
	

	public ShipBuilder(
			Range hp,
			Range fuel){
		this.hp = hp;
		this.fuel = fuel;
	}
	
	public void setup(SpaceShip ship, ResourceContainer container){
		ship.getHp().setCapacity(hp.getRandomElement());
		ship.getFuel().setCapacity(fuel.getRandomElement());
	}
	
	public void build(SpaceShip ship, ResourceContainer container){
		Material metal = (Material) container.getResources().get(0);
		if(metal.getValue()>metalIncrementAmount){			
			metal.setValue(metal.getValue()-(metalIncrementAmount-ship.getHp().add(metalIncrementAmount)));
		}
		Material fuel = (Material) container.getResources().get(1);
		if(fuel.getValue()>fuelIncrementAmount){
			fuel.setValue(fuel.getValue()-(fuelIncrementAmount-ship.getFuel().add(fuelIncrementAmount)));
		}
	}
}
