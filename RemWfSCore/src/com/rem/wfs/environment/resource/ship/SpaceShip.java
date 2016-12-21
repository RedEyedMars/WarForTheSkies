package com.rem.wfs.environment.resource.ship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.storage.StorageHandler;
import com.rem.core.storage.handler.HandlerListStorageHandler;
import com.rem.wfs.Creatable;
import com.rem.wfs.environment.Identifiable;
import com.rem.wfs.environment.IdentityStorageHandler;
import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.ResourceType;
import com.rem.wfs.environment.resource.SpaceResource;
import com.rem.wfs.environment.resource.StockList;
import com.rem.wfs.environment.resource.StockType;
import com.rem.wfs.environment.resource.personel.Personel;
import com.rem.wfs.environment.resource.personel.PersonelStock;
import com.rem.wfs.graphics.R;

public class SpaceShip extends GraphicElement implements Creatable, Identifiable, ResourceContainer{

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

		@Override
		public StockType<Personel> getPersonelStock() {
			return new PersonelStock(){
				@Override
				public float generateInitialValue(ResourceContainer container) {
					return 13;
				}

				@Override
				public int generateInitialLimit(ResourceContainer container) {
					return 13;
				}

				@Override
				public float generateInitialGrowth(ResourceContainer container) {
					return 0;
				}
				
			};
		}
	};
	private static final SpaceShipStock LANCER = new SpaceShipStock(
			"Lancer", "Long ranged bulky ship that can defend flag ships well.",
			R.spaceships,LANCER_FRAME,
			R.resource_backs,LANCER_BACKGROUND_FRAME
			){
		@Override
		public int generateInitialLimit(ResourceContainer container)
		{ return (int) (Math.random()*5)+2; }

		@Override
		public float generateInitialValue(ResourceContainer container) { return 4f; }

		@Override
		public StockType<Personel> getPersonelStock() {
			return new PersonelStock(){
				@Override
				public float generateInitialValue(ResourceContainer container) {
					return 8;
				}

				@Override
				public int generateInitialLimit(ResourceContainer container) {
					return 8;
				}

				@Override
				public float generateInitialGrowth(ResourceContainer container) {
					return 0;
				}
				
			};
		}
	};
	private static final SpaceShipStock FIGHTER = new SpaceShipStock(
			"Fighter", "Quick sleek ship, designed for quick manuevers.",
			R.spaceships,FIGHTER_FRAME,
			R.resource_backs,FIGHTER_BACKGROUND_FRAME
			){
		@Override
		public int generateInitialLimit(ResourceContainer container)
		{ return (int) (Math.random()*16)+8; }

		@Override
		public float generateInitialValue(ResourceContainer container) { return 5f; }
		
		@Override
		public StockType<Personel> getPersonelStock() {
			return new PersonelStock(){
				@Override
				public float generateInitialValue(ResourceContainer container) {
					return 2;
				}

				@Override
				public int generateInitialLimit(ResourceContainer container) {
					return 2;
				}

				@Override
				public float generateInitialGrowth(ResourceContainer container) {
					return 0;
				}
				
			};
		}
	};
	private static final SpaceShipStock MINER = new SpaceShipStock(
			"Miner", "Ship that cannot fight but does contibute to resource collection.",
			R.spaceships,MINER_FRAME,
			R.resource_backs,MINER_BACKGROUND_FRAME
			){
		@Override
		public int generateInitialLimit(ResourceContainer container)
		{ return (int) (Math.random()*16)+8; }


		@Override
		public float generateInitialValue(ResourceContainer container) { return 1f; }

		@Override
		public StockType<Personel> getPersonelStock() {
			return new PersonelStock(){
				@Override
				public float generateInitialValue(ResourceContainer container) {
					return 5;
				}

				@Override
				public int generateInitialLimit(ResourceContainer container) {
					return 5;
				}

				@Override
				public float generateInitialGrowth(ResourceContainer container) {
					return 0;
				}
				
			};
		}
	};
	
	public static final int ID_HARBINGER = HARBINGER.getId();
	public static final int ID_LANCER = LANCER.getId();
	public static final int ID_FIGHTER = FIGHTER.getId();
	public static final int ID_MINER = MINER.getId();

	private int id = 0;
	private SpaceShipStock resource;
	private ShipDetails details;
	private ShipName name;
	private StockList<Personel> crew;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<SpaceResource> crewContainer = new ArrayList<SpaceResource>(0){
		private static final long serialVersionUID = -5001521348598400403L;

		@Override
		public boolean add(SpaceResource newCrew){
			crew = (StockList<Personel>)newCrew;
			return true;
		}
		@Override
		public StockList<Personel> get(int index){
			return crew;
		}
		@Override
		public int size(){
			return 1;
		}
		@Override
		public Iterator<SpaceResource>  iterator(){
			return new Iterator<SpaceResource> (){
				private boolean has = true;

				@Override
				public boolean hasNext() {
					return has;
				}

				@Override
				public SpaceResource next() {
					has = false;
					return crew;
				}

				@Override
				public void remove() {					
				}
				
			};
		}
	};

	public SpaceShip(SpaceShipStock resource) {
		super(R.space_objects,0,R.BOT_LAYER);
		this.resource = resource;
		details = new ShipDetails(this);
		name = new ShipName();
		if(resource!=null){
			crewContainer.add(resource.getPersonelStock().createPlaceHolder(this));
		}
	}

	@Override
	public StorageHandler getStorageHandler() {
		return new HandlerListStorageHandler(
				new IdentityStorageHandler(this),
				details,
				name,
				crew.getStorageHandler());
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public ResourceType<StockList<SpaceShip>> getResource() {
		return resource;
	}

	@Override
	public void onCreate() {
		details.onCreate();
		name.onCreate();
		crew.onCreate();
	}
	
	public ShipName getName(){
		return name;
	}

	public int getSpaceShipClassificationId() {
		return resource.getId()-SpaceShip.ID_HARBINGER;
	}

	public DetailedShipIcon getDetailedIcon(int id) {
		return new DetailedShipIcon(this,
				R.single_background_2,0,
				details,name.getFullName(),id);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<SpaceResource> getResources() {
		return crewContainer;
	}
	
	public List<Personel> getCrew(){
		return crew;
	}
}
