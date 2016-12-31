package com.rem.wfs.environment.resource.cluster;

import java.util.ArrayList;
import java.util.List;

import com.rem.core.Hub;
import com.rem.core.environment.Range;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.storage.Storable;
import com.rem.core.storage.StorageHandler;
import com.rem.core.storage.handler.HandlerListStorageHandler;
import com.rem.wfs.Game;
import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.ResourceType;
import com.rem.wfs.environment.resource.SpaceResource;
import com.rem.wfs.environment.resource.material.Material;
import com.rem.wfs.environment.resource.material.MaterialsView;
import com.rem.wfs.environment.resource.personel.Personel;
import com.rem.wfs.environment.resource.personel.menu.PersonelListView;
import com.rem.wfs.environment.resource.ship.SpaceShip;
import com.rem.wfs.environment.resource.ship.menu.SpaceShipListView;
import com.rem.wfs.environment.resource.ship.stations.RepairSpaceStation;
import com.rem.wfs.environment.resource.ship.stations.SpaceStation;
import com.rem.wfs.environment.resource.ship.stations.SpaceStationListView;
import com.rem.wfs.graphics.R;
import com.rem.wfs.graphics.icons.Icon;
import com.rem.wfs.graphics.icons.Iconic;

@SuppressWarnings("rawtypes")
public class ResourceCluster implements Storable, ResourceContainer{

	private static final List<Integer> textures = new ArrayList<Integer>();
	private static final List<Integer> frames = new ArrayList<Integer>();
	private static final List<Range> ranges = new ArrayList<Range>();
	private static final int MATERIAL_TEXTURE = R.resource_types;
	private static final int PERSONEL_TEXTURE = R.resource_types;
	private static final int SPACESHIP_TEXTURE = R.resource_types;
	private static final int MATERIAL_FRAME = 0;
	private static final int PERSONEL_FRAME = 1;
	private static final int SPACESHIP_FRAME = 2;
	private static void addTexture(int texture, int frame, Range range){
		textures.add(texture);
		frames.add(frame);
		ranges.add(range);
	}
	public static void setup(){
		addTexture(MATERIAL_TEXTURE,MATERIAL_FRAME,new Range(0,2));
		addTexture(PERSONEL_TEXTURE,PERSONEL_FRAME,new Range(3,3));
		addTexture(SPACESHIP_TEXTURE,SPACESHIP_FRAME,new Range(4,7));
	}
	public final static int numberOfClusters = 3;

	private List<? extends ResourceContainer> container;
	private List<SpaceResource> resources = new ArrayList<SpaceResource>();
	private List<SpaceStation> stations = new ArrayList<SpaceStation>();
	private Icon icon;
	private int id;

	public ResourceCluster(List<? extends ResourceContainer> resourceList, final int id){
		this.container = resourceList;
		this.id = id;
		for(Integer type:ranges.get(id)){
			resources.add(ResourceType.types.get(type).createPlaceHolder(this));
		}
		if(id==ResourceContainer.SPACESHIP_ID){
			stations.add(new RepairSpaceStation(this));
		}
		this.icon = new ResourceClusterIcon(
				textures.get(id), frames.get(id), R.BOT_LAYER, "Resources", id){
			@SuppressWarnings("unchecked")
			@Override
			public boolean onClick(ClickEvent event){
				if(dim.isWithin(event.getX(), event.getY())){
					if(event.getAction()==ClickEvent.ACTION_UP){
						if(id == MATERIAL_ID){						
							((Game)Hub.view).buildOverlay(new MaterialsView(
									"Material",
									(Material)resources.get(0),
									(Material)resources.get(1),
									(Material)resources.get(2)

									));
						}
						else if(id == PERSONEL_ID){						
							((Game)Hub.view).buildOverlay(new PersonelListView(
									"Unassigned Peronsel",
									(List<Personel>)resources.get(0)										
									));							
						}
						else if(id == SPACESHIP_ID){
							((Game)Hub.view).buildOverlay(new SpaceStationListView(
									stations,
									(List<SpaceShip>)resources.get(0),
									(List<SpaceShip>)resources.get(1),
									(List<SpaceShip>)resources.get(2),
									(List<SpaceShip>)resources.get(3)));
							/*
							((Game)Hub.view).buildOverlay(new SpaceShipListView(
									"Space-Ship Yards",
									(List<SpaceShip>)resources.get(0),
									(List<SpaceShip>)resources.get(1),
									(List<SpaceShip>)resources.get(2),
									(List<SpaceShip>)resources.get(3)
									));*/
						}
					}
					return true;
				}
				else return false;
			}
		};
		this.icon.setVisible(false);
	}
	@Override
	public StorageHandler getStorageHandler() {
		StorageHandler[] handlers = new StorageHandler[resources.size()];
		for(int i=0;i<resources.size();++i){
			handlers[i] = resources.get(i).getStorageHandler();
		}
		return new HandlerListStorageHandler(handlers);
	}

	@Override
	public List<SpaceResource> getResources() {
		return resources;
	}

	@Override
	public SpaceResource getResource(int catagory, int id) {
		List<? extends ResourceContainer> cont = container;
		if(getId()==catagory){
			int index = ResourceCluster.ranges.get(getId()).indexOf(id);
			if(index>-1){
				return resources.get(index);
			}
			else {
				return null;
			}
		}
		else {
			return cont.get(catagory).getResource(catagory, id);
		}
		
	}
	
	public Iconic getIcon() {
		return icon;
	}
	public void onCreate() {
		for(SpaceResource<?> resource:resources){
			resource.onCreate(this);
		}
	}
	public int getId(){
		return id;
	}

	public void update(float secondsSinceLastUpdate){
		for(SpaceResource<?> resource:resources){
			resource.grow(secondsSinceLastUpdate);
		}
	}
}
