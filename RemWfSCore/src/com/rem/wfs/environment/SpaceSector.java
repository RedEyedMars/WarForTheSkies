package com.rem.wfs.environment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rem.core.Action;
import com.rem.core.Hub;
import com.rem.core.IFileManager;
import com.rem.core.environment.Environment;
import com.rem.core.gui.inputs.HoverEvent;
import com.rem.core.storage.DataCollector;
import com.rem.core.storage.Storage;
import com.rem.core.storage.StorageHandler;
import com.rem.core.storage.handler.HandlerListStorageHandler;
import com.rem.wfs.environment.hexagon.SpaceHexagon;
import com.rem.wfs.environment.hexagon.SpaceHexagonListStorageHandler;
import com.rem.wfs.environment.location.Locatable;
import com.rem.wfs.environment.location.Location;
import com.rem.wfs.environment.resource.cluster.ResourceCluster;
import com.rem.wfs.environment.resource.material.Material;
import com.rem.wfs.environment.resource.personel.Personel;
import com.rem.wfs.environment.resource.ship.SpaceShip;

public class SpaceSector extends Environment implements Locatable, Identifiable{

	public static final int NORMAL_SECTOR = 0;
	private Long lastUpdatedTime;
	private Integer id;
	private Location location = new Location(0,0);
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
			else {
				return false;
			}
		}
		private boolean addHexagon(SpaceHexagon arg){
			hexMap.get(arg.getLocation().getY()).put(arg.getLocation().getX(), arg);
			arg.resize(0.16f, 0.18f);
			tree.addChild(arg);
			return super.add(arg);
		}
	};

	private Thread saveThread = new Thread(){
		@Override
		public void run(){
			try {
				while(Hub.gui.isRunning()){
					Storage.save(
							Hub.manager.createOutputStream(Hub.map.getFileName(), IFileManager.ABSOLUTE),
							Hub.map);
					Thread.sleep(5000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

	public SpaceSector() {
		super();
		new Material(null,null);
		new Personel();
		new SpaceShip(null,null);
		ResourceCluster.setup();
		
	}

	@Override
	public StorageHandler getStorageHandler() {
		return new HandlerListStorageHandler(
				new IdentityStorageHandler(this),
				location,
				new SpaceHexagonListStorageHandler(this),
				new StorageHandler(){
					@Override
					public void collect(DataCollector data) throws IOException {	
						lastUpdatedTime = data.collect(lastUpdatedTime);
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

	@Override
	public boolean onHover(HoverEvent event){
		SpaceHexagon hoverOn = null;
		for(SpaceHexagon hex:spaceHexagons){
			if(hex.onHover(event)){
				hoverOn = hex;
			}
		}
		for(SpaceHexagon hex:spaceHexagons){
			hex.performOnHover(event, hex==hoverOn);
		}
		return true;
	}
	
	public void start(){
		saveThread.start();
		reposition(dim.getX()-0.3f,dim.getY()-0.4f);
	}

	@Override
	public void update(double secondsSinceLastFrame){
		long currentTime = System.currentTimeMillis();
		double seconds = (currentTime-lastUpdatedTime)/1000.0;
		for(SpaceHexagon hex:spaceHexagons){
			hex.update(seconds);
		}
		lastUpdatedTime = currentTime;
	}

}
