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
	
	public void build(SpaceShip ship, ResourceContainer container, float seconds){
		Material metal = (Material) container.getResource(ResourceContainer.MATERIAL_ID, Material.ID_METAL);
		float mtlIcr = metalIncrementAmount*seconds;
		if(metal.getValue()>metalIncrementAmount){			
			metal.setValue(metal.getValue()-(mtlIcr-ship.getHp().add(mtlIcr)));
		}
		Material fuel = (Material) container.getResource(ResourceContainer.MATERIAL_ID, Material.ID_FUEL);
		float fuelIcr = fuelIncrementAmount*seconds;
		if(fuel.getValue()>fuelIcr){
			fuel.setValue(fuel.getValue()-(fuelIcr-ship.getFuel().add(fuelIcr)));
		}
	}
}
