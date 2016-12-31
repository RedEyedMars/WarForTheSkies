package com.rem.wfs.environment.resource.ship;

import java.util.ArrayList;
import java.util.List;

import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.personel.Personel;
import com.rem.wfs.environment.resource.stock.StockList;
import com.rem.wfs.environment.resource.stock.StockType;

public abstract class SpaceShipStock extends StockType<SpaceShip>{
	public static final List<SpaceShipStock> types = new ArrayList<SpaceShipStock>();
	private ShipBuilder shipBuilder;
	public SpaceShipStock(String name, String description,
			ShipBuilder builder,
			int iconTexture, int iconFrame,
			int iconBackgroundTexture, int iconBackgroundFrame) {
		super(name, description, iconTexture, iconFrame, iconBackgroundTexture, iconBackgroundFrame);
		types.add(this);
		this.shipBuilder = builder;
	}

	@Override
	public SpaceShip createObjectPlaceHolder(StockList<SpaceShip> parent) {
		return new SpaceShip(parent,this);
	}
	@Override
	public float generateInitialValue(ResourceContainer container) { return 1f; }
	@Override
	public float generateInitialGrowth(ResourceContainer container) { return 0; }
	
	public abstract StockType<Personel> getPersonelStock();	

	public ShipBuilder getBuilder(){
		return shipBuilder;
	}
}

