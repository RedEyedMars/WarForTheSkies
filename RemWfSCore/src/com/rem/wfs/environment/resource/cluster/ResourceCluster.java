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
import com.rem.wfs.environment.resource.personel.Personel;
import com.rem.wfs.environment.resource.ship.SpaceShip;
import com.rem.wfs.graphics.Icon;
import com.rem.wfs.graphics.Iconic;
import com.rem.wfs.graphics.R;
import com.rem.wfs.menu.MaterialsView;
import com.rem.wfs.menu.PersonelListView;
import com.rem.wfs.menu.SpaceShipListView;

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
	private static final int MATERIAL_ID = 0;
	private static final int PERSONEL_ID = 1;
	private static final int SPACESHIP_ID = 2;
	private static void addTexture(int texture, int frame, Range range){
		textures.add(texture);
		frames.add(frame);
		ranges.add(range);
	}
	public static void setup(){
		addTexture(MATERIAL_TEXTURE,MATERIAL_FRAME,new Range(0,2));
		addTexture(PERSONEL_TEXTURE,PERSONEL_FRAME,new Range(3,3));
		addTexture(SPACESHIP_TEXTURE,SPACESHIP_FRAME,new Range(4,7));
		numberOfClusters = textures.size();
	}
	public static int numberOfClusters;

	private List<SpaceResource> resources = new ArrayList<SpaceResource>();
	private Icon icon;
	private int id;

	public ResourceCluster(final int id){
		this.id = id;
		for(Integer type:ranges.get(id)){
			resources.add(ResourceType.types.get(type).createPlaceHolder(this));
		}
		this.icon = new ResourceClusterIcon(
				textures.get(id), frames.get(id), R.BOT_LAYER, "Resources", id){
			@SuppressWarnings("unchecked")
			@Override
			public boolean onClick(ClickEvent event){
				if(dim.isWithin(event.getX(), event.getY())){
					if(event.getAction()==ClickEvent.ACTION_UP){
						if(id == MATERIAL_ID){						
							((Game)Hub.view).addOverlayMenu(new MaterialsView(
									(Material)resources.get(0),
									(Material)resources.get(1),
									(Material)resources.get(2)

									));
						}
						else if(id == PERSONEL_ID){						
							((Game)Hub.view).addOverlayMenu(new PersonelListView(
									(List<Personel>)resources.get(0)										
									));							
						}
						else if(id == SPACESHIP_ID){						
							((Game)Hub.view).addOverlayMenu(new SpaceShipListView(
									(List<SpaceShip>)resources.get(0)
									));							
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
	public Iconic getIcon() {
		return icon;
	}
	public void onCreate() {
		for(SpaceResource<?> resource:resources){
			resource.onCreate();
		}
	}
	public int getId(){
		return id;
	}

}
