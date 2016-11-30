package com.rem.wfs.environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rem.core.Action;
import com.rem.core.environment.Environment;
import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.StorageHandler;
import com.rem.core.storage.handler.HandlerListStorageHandler;
import com.rem.wfs.environment.hexagon.SpaceHexagon;
import com.rem.wfs.environment.hexagon.SpaceHexagonListStorageHandler;
import com.rem.wfs.environment.location.Locatable;
import com.rem.wfs.environment.location.Location;
import com.rem.wfs.environment.location.LocationStorageHandler;
import com.rem.wfs.environment.resource.material.Material;
import com.rem.wfs.environment.resource.personel.Personel;
import com.rem.wfs.environment.resource.ship.SpaceShip;

public class SpaceSector extends Environment implements Locatable, Identifiable{

	public static final int NORMAL_SECTOR = 0;
	private Long lastUpdatedTime;
	private Integer id;
	private Location location ;
	private Map<Integer,Map<Integer,SpaceHexagon>> hexMap = new HashMap<Integer,Map<Integer,SpaceHexagon>>();

	private List<SpaceHexagon> spaceHexagons = new ArrayList<SpaceHexagon>(){
		private static final long serialVersionUID = -2027650915428834003L;

		@Override
		public boolean add(SpaceHexagon arg){
			if(!hexMap.containsKey(arg.getLocation().getY())){
				hexMap.put(arg.getLocation().getY(), new HashMap<Integer,SpaceHexagon>());
				return addHexagon(arg);
			}
			else if(!hexMap.get(arg.getLocation().getY()).containsKey(arg.getLocation().getX())){
				return addHexagon(arg);
			}
			else return false;
		}
		private boolean addHexagon(SpaceHexagon arg){
			hexMap.get(arg.getLocation().getY()).put(arg.getLocation().getX(), arg);

			resize(0.16f, 0.18f);
			addChild(arg);
			return super.add(arg);
		}
	};

	public SpaceSector() {
		super();
		new Material(new SpaceHexagon(),0);
		new Personel();
		new SpaceShip();
	}

	@Override
	public StorageHandler getStorageHandler() {
		return new HandlerListStorageHandler(
				new IdentityStorageHandler(this),
				new LocationStorageHandler(this),
				new SpaceHexagonListStorageHandler(this),
				new StorageHandler(){
					@Override
					public void load(DataPresenter data) {	
						lastUpdatedTime = data.nextLong();					
						reposition(getX()-0.3f,getY()-0.4f);
					}

					@Override
					public void save(DataCollector toSave) {
						toSave.collect(lastUpdatedTime);
					}					
				});
	}

	@Override
	public void addEntity(int arg0, Object arg1) {

	}

	@Override
	public Object getEntity(int arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		for(int y=1;y<8;++y){
			for(int x=0;x<y+5;++x){
				createHexagon(x+2-y/2,y);
			}
		}
		for(int y=8;y<14;++y){
			for(int x=0;x<14-y+5;++x){
				createHexagon(x+2-(14-y)/2,y);
			}
		}
		reposition(getX()-0.3f,getY()-0.4f);
		
		getHexagon(4,6).setId(SpaceHexagon.createId(SpaceHexagon.HOST_PLAYER_ID));
		getHexagon(5,6).setId(SpaceHexagon.createId(SpaceHexagon.HOST_PLAYER_ID));
		getHexagon(3,6).setId(SpaceHexagon.createId(SpaceHexagon.HOST_PLAYER_ID));
		getHexagon(4,7).setId(SpaceHexagon.createId(SpaceHexagon.HOST_PLAYER_ID));
		getHexagon(5,7).setId(SpaceHexagon.createId(SpaceHexagon.HOST_PLAYER_ID));
		getHexagon(4,5).setId(SpaceHexagon.createId(SpaceHexagon.HOST_PLAYER_ID));
		getHexagon(5,5).setId(SpaceHexagon.createId(SpaceHexagon.HOST_PLAYER_ID));
		
		location = new Location(0,0);
		id = NORMAL_SECTOR;
		this.lastUpdatedTime = System.currentTimeMillis();
	}
	
	public SpaceHexagon getHexagon(int x, int y){
		return hexMap.get(y).get(x);
	}

	private void createHexagon(int x, int y){
		SpaceHexagon spaceHexagon = new SpaceHexagon();
		spaceHexagon.onCreate(SpaceHexagon.createId(SpaceHexagon.UNCLAIMED_ID));
		spaceHexagon.setLocation(new Location(x,y));
		spaceHexagons.add(spaceHexagon);
	}

	@Override
	public void restart(Action<Object> arg0) {

	}

	public Location getLocation(){
		return location;
	}

	public void setLocation(Location location){
		this.location = location;
	}

	public List<SpaceHexagon> getHexagons() {
		return spaceHexagons;
	}

	public void setId(Integer id) {
		this.id = id; 
	}

	public Integer getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}




}
