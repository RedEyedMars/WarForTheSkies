package com.rem.wfs.environment.resource.ship;

import com.rem.core.gui.graphics.GraphicElement;
import com.rem.core.gui.graphics.GraphicEntity;
import com.rem.core.storage.Storable;
import com.rem.core.storage.StorageHandler;
import com.rem.core.storage.handler.HandlerListStorageHandler;
import com.rem.wfs.environment.Identifiable;
import com.rem.wfs.environment.IdentityStorageHandler;
import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.ResourceType;
import com.rem.wfs.environment.resource.StockType;

public class SpaceShip extends GraphicEntity implements Storable, Identifiable{

	private static final int HARBINGER_FRAME = 16;
	private static final int HARBINGER_BACKGROUND_FRAME = 31;
	private static final int LANCER_FRAME = 17;
	private static final int LANCER_BACKGROUND_FRAME = 28;
	private static final int FIGHTER_FRAME = 18;
	private static final int FIGHTER_BACKGROUND_FRAME = 29;
	private static final int MINER_FRAME = 19;
	private static final int MINER_BACKGROUND_FRAME = 30;
	private static final SpaceShipStock HARBINGER = new SpaceShipStock(
			"Flag Ship", "Used in defending large areas and is the only unit able of capturing other hexagons.",
			"space_objects",HARBINGER_FRAME,
			"space_objects",HARBINGER_BACKGROUND_FRAME
			){
		@Override
		public int generateInitialLimit(ResourceContainer container)
		{ return (int) (Math.random()*2); }
	};
	private static final SpaceShipStock LANCER = new SpaceShipStock(
			"Lancer", "Long ranged bulky ship that can defend flag ships well.",
			"space_objects",LANCER_FRAME,
			"space_objects",LANCER_BACKGROUND_FRAME
			){
		@Override
		public int generateInitialLimit(ResourceContainer container)
		{ return (int) (Math.random()*5)+2; }
	};
	private static final SpaceShipStock FIGHTER = new SpaceShipStock(
			"Fighter", "Quick sleek ship, designed for quick manuevers.",
			"space_objects",FIGHTER_FRAME,
			"space_objects",FIGHTER_BACKGROUND_FRAME
			){
		@Override
		public int generateInitialLimit(ResourceContainer container)
		{ return (int) (Math.random()*16)+8; }
	};
	private static final SpaceShipStock MINER = new SpaceShipStock(
			"Miner", "Ship that cannot fight but does contibute to resource collection.",
			"space_objects",MINER_FRAME,
			"space_objects",MINER_BACKGROUND_FRAME
			){
		@Override
		public int generateInitialLimit(ResourceContainer container)
		{ return (int) (Math.random()*16)+8; }
	};
	
	public static final int ID_HARBINGER = HARBINGER.getId();
	public static final int ID_LANCER = LANCER.getId();
	public static final int ID_FIGHTER = FIGHTER.getId();
	public static final int ID_MINER = MINER.getId();

	private int id;
	private StockType<SpaceShip> resource;

	public SpaceShip() {
		super(new GraphicElement("space_objects"));
	}

	@Override
	public StorageHandler getStorageHandler() {
		return new HandlerListStorageHandler(
				new IdentityStorageHandler(this));
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
		this.resource = SpaceShipStock.types.get(id);
	}

	public ResourceType getResource() {
		return resource;
	}


}
