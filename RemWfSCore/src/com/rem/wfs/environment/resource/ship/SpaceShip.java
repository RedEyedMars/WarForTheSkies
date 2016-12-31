package com.rem.wfs.environment.resource.ship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rem.core.environment.Range;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.storage.StorageHandler;
import com.rem.core.storage.handler.HandlerListStorageHandler;
import com.rem.wfs.environment.Identifiable;
import com.rem.wfs.environment.IdentityStorageHandler;
import com.rem.wfs.environment.hexagon.SpaceHexagon;
import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.ResourceType;
import com.rem.wfs.environment.resource.SpaceResource;
import com.rem.wfs.environment.resource.personel.Personel;
import com.rem.wfs.environment.resource.personel.PersonelStock;
import com.rem.wfs.environment.resource.ship.stations.SpaceStation;
import com.rem.wfs.environment.resource.ship.stations.SpaceStationPosition;
import com.rem.wfs.environment.resource.stock.StockElement;
import com.rem.wfs.environment.resource.stock.StockList;
import com.rem.wfs.environment.resource.stock.StockType;
import com.rem.wfs.graphics.Meter;
import com.rem.wfs.graphics.R;

public class SpaceShip extends GraphicElement implements StockElement, Identifiable, ResourceContainer{

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
			new ShipBuilder(new Range(100,150),new Range(1000,1500)),
			R.spaceships,HARBINGER_FRAME,
			R.resource_backs,HARBINGER_BACKGROUND_FRAME
			){
		@Override
		public int generateInitialLimit(ResourceContainer container)
		{ return (int) (Math.random()*2); }

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
			new ShipBuilder(new Range(50,75),new Range(200,300)),
			R.spaceships,LANCER_FRAME,
			R.resource_backs,LANCER_BACKGROUND_FRAME
			){
		@Override
		public int generateInitialLimit(ResourceContainer container)
		{ return (int) (Math.random()*5)+2; }

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
			new ShipBuilder(new Range(20,50),new Range(100,200)),
			R.spaceships,FIGHTER_FRAME,
			R.resource_backs,FIGHTER_BACKGROUND_FRAME
			){
		@Override
		public int generateInitialLimit(ResourceContainer container)
		{ return (int) (Math.random()*16)+8; }
		
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
			new ShipBuilder(new Range(10,20),new Range(200,500)),
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
	private ShipStatistic hp = new ShipStatistic(){
		@Override
		protected Meter createMeter() {
			Meter meter = new Meter(fuel,"Fuel",
					R.solid_colour,R.COLOUR_RED,
					R.spaceships,details.generateIcon().getFrame()+7,
					R.spaceships,47,
					R.MID_LAYER,null);
			final float height = meter.getForeground().dim.getHeight()/2;
			meter.getAnimater().resize(meter.getForeground().dim.getWidth(), height);
			meter.setAnimation(new Meter.Animation(){
				@Override
				public void animate(GraphicElement element, float percentFull) {
					element.resize(element.dim.getWidth(), percentFull*height);
					if(percentFull<0.25f){
						element.setFrame(R.COLOUR_RED);
					}
					else if(percentFull<0.5f){
						element.setFrame(R.COLOUR_YELLOW);
					}
					else if(percentFull<0.75f){
						element.setFrame(R.COLOUR_GREEN);
					}
					else {
						element.setFrame(R.COLOUR_BLUE);
					}
				}				
			});
			return meter;
		}
		
	};
	private ShipStatistic fuel = new ShipStatistic(){
		@Override
		protected Meter createMeter() {
			return new Meter(fuel,"Fuel",R.spaceships,56,R.MID_LAYER,new Meter.Animation(){
				@Override
				public void animate(GraphicElement element, float percentFull) {
					element.setFrame((int) (55+(1f-percentFull)*8));
				}				
			});
		}		
	};
	private StockList<SpaceShip> parent;
	private SpaceStationPosition spaceStationPosition;

	public SpaceShip(StockList<SpaceShip> parent, SpaceShipStock resource) {
		super(R.space_objects,0,R.BOT_LAYER);
		this.parent = parent;
		this.resource = resource;
		details = new ShipDetails(this);
		name = new ShipName();
		if(resource!=null){
			crewContainer.add(
					resource.getPersonelStock().createPlaceHolder(this));
		}
	}

	@Override
	public StorageHandler getStorageHandler() {
		return new HandlerListStorageHandler(
				new IdentityStorageHandler(this),
				details,
				name,				
				crew.getStorageHandler(),
				hp,
				fuel,
				SpaceStationPosition.getStorageHandler(this));
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
	public void onCreate(ResourceContainer container) {
		details.onCreate();
		name.onCreate();
		crew.onCreate(container);
		resource.getBuilder().setup(this, container);
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

	@Override
	public SpaceResource<?> getResource(int catagory, int id) {
		if(catagory == ResourceContainer.PERSONEL_ID&&id==0){
			return crew;
		}
		else return this.parent.getContainer().getResource(catagory, id);
	}
	
	public List<SpaceStation> getAvailibleStations(){
		if(this.parent.getContainer() instanceof SpaceHexagon){
			return ((SpaceHexagon)this.parent.getContainer()).getAvailibleStations();
		}
		else return null;
	}
	
	public List<Personel> getCrew(){
		return crew;
	}

	public ShipStatistic getHp(){
		return hp;
	}
	
	public ShipStatistic getFuel(){
		return fuel;
	}

	@Override
	public void grow(ResourceContainer container, float seconds) {
		this.resource.getBuilder().build(this, container, seconds);
	}

	public SpaceStationPosition getSpaceStationPosition() {
		return spaceStationPosition;
	}

	public void setSpaceStationPosition(SpaceStationPosition spaceStationPosition) {
		this.spaceStationPosition = spaceStationPosition;
	}

	
}
