package com.rem.wfs.environment.resource.ship;

import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.storage.StorageHandler;
import com.rem.core.storage.handler.HandlerListStorageHandler;
import com.rem.wfs.Creatable;
import com.rem.wfs.environment.Identifiable;
import com.rem.wfs.environment.IdentityStorageHandler;
import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.ResourceType;
import com.rem.wfs.environment.resource.StockList;
import com.rem.wfs.environment.resource.StockType;
import com.rem.wfs.graphics.R;

public class SpaceShip extends GraphicElement implements Creatable, Identifiable{

	private static final int HARBINGER_FRAME = 4;
	private static final int HARBINGER_BACKGROUND_FRAME = 9;
	private static final int LANCER_FRAME = 5;
	private static final int LANCER_BACKGROUND_FRAME = 1;
	private static final int FIGHTER_FRAME = 6;
	private static final int FIGHTER_BACKGROUND_FRAME = 5;
	private static final int MINER_FRAME = 7;
	private static final int MINER_BACKGROUND_FRAME = 13;
	private static final SpaceShipStock HARBINGER = new SpaceShipStock(
			"Flag Ship", "Used in defending large areas and is the only unit able of capturing other hexagons.",
			R.spaceships,HARBINGER_FRAME,
			R.resource_backs,HARBINGER_BACKGROUND_FRAME
			){
		@Override
		public int generateInitialLimit(ResourceContainer container)
		{ return (int) (Math.random()*2); }

		@Override
		public float generateInitialValue(ResourceContainer container) { return 3f; }
	};
	private static final SpaceShipStock LANCER = new SpaceShipStock(
			"Lancer", "Long ranged bulky ship that can defend flag ships well.",
			R.spaceships,LANCER_FRAME,
			R.resource_backs,LANCER_BACKGROUND_FRAME
			){
		@Override
		public int generateInitialLimit(ResourceContainer container)
		{ return (int) (Math.random()*5)+2; }
	};
	private static final SpaceShipStock FIGHTER = new SpaceShipStock(
			"Fighter", "Quick sleek ship, designed for quick manuevers.",
			R.spaceships,FIGHTER_FRAME,
			R.resource_backs,FIGHTER_BACKGROUND_FRAME
			){
		@Override
		public int generateInitialLimit(ResourceContainer container)
		{ return (int) (Math.random()*16)+8; }
	};
	private static final SpaceShipStock MINER = new SpaceShipStock(
			"Miner", "Ship that cannot fight but does contibute to resource collection.",
			R.spaceships,MINER_FRAME,
			R.resource_backs,MINER_BACKGROUND_FRAME
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
	private ShipDetails details;
	private ShipName name;

	public SpaceShip(StockType<SpaceShip> resource) {
		super(R.space_objects,0,R.BOT_LAYER);
		this.resource = resource;
		details = new ShipDetails(this);
		name = new ShipName();
	}

	@Override
	public StorageHandler getStorageHandler() {
		return new HandlerListStorageHandler(
				new IdentityStorageHandler(this),
				details,
				name);
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

	public ResourceType<StockList<SpaceShip>> getResource() {
		return resource;
	}

	@Override
	public void onCreate() {
		details.onCreate();
		name.onCreate();
	}

	public int getSpaceShipClassificationId() {
		return resource.getId()-SpaceShip.ID_HARBINGER;
	}

	public DetailedShipIcon getDetailedIcon(int id) {
		return new DetailedShipIcon(this,
				R.background_1,15,
				details,name.getFullName(),id);
	}
}
