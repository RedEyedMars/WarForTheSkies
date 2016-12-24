package com.rem.wfs.environment.hexagon;

import java.util.ArrayList;
import java.util.List;

import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.gui.inputs.HoverEvent;
import com.rem.core.storage.Storable;
import com.rem.core.storage.StorageHandler;
import com.rem.core.storage.handler.HandlerListStorageHandler;
import com.rem.core.storage.handler.StorableStorageHandler;
import com.rem.wfs.environment.Identifiable;
import com.rem.wfs.environment.IdentityStorageHandler;
import com.rem.wfs.environment.hexagon.system.SpaceSystem;
import com.rem.wfs.environment.location.Locatable;
import com.rem.wfs.environment.location.Location;
import com.rem.wfs.environment.location.LocationStorageHandler;
import com.rem.wfs.environment.resource.cluster.ResourceCluster;
import com.rem.wfs.environment.resource.cluster.ResourceClusterListStorageHandler;
import com.rem.wfs.graphics.R;
import com.rem.wfs.graphics.icons.Iconic;

public class SpaceHexagon extends Hexagon
implements  Storable, Locatable, Identifiable{
	
	public static final int HOST_PLAYER_ID = 0;
	public static final int FRIENDLY_HUMAN_PLAYER_ID = 2;
	public static final int ENEMY_HUMAN_PLAYER_ID = 3;
	public static final int FIENDLY_NPC_ID = 5;
	public static final int ENEMY_NPC_ID = 7;
	public static final int UNCLAIMED_ID = 11;

	//private static SpaceHexagon currentlySelected = null;

	private Hexagon overGon;
	private int ownerId;
	private Location location;
	private SpaceSystem spaceSystem;

	private List<ResourceCluster> resourceList = new ArrayList<ResourceCluster>(){
		private static final long serialVersionUID = 6887318772910988733L;
		@Override
		public boolean add(ResourceCluster arg){
			tree.addChild((GraphicElement)arg.getIcon());
			return super.add(arg);
		}
		@Override
		public boolean remove(Object arg){
			if(arg instanceof ResourceCluster){
				ResourceCluster rc = (ResourceCluster)arg;
				int index = indexOf(rc);
				if(index!=-1){
					tree.removeChild((GraphicElement) rc.getIcon());
					super.remove(index);
					return true;
				}
				else return false;
			}
			else return false;
		}
	};

	public SpaceHexagon() {
		super(R.solid_colour,R.COLOUR_BLUE,R.BOT_LAYER);

		overGon = new Hexagon(R.space_background,(int) (Math.random()*8),R.BOT_LAYER);
		tree.addChild(overGon);

		spaceSystem = new SpaceSystem();
		tree.addChild(spaceSystem);	

	}

	@Override
	public boolean onHover(HoverEvent event){
		boolean status = false;
		if(isWithin(event.getX(),event.getY())){
			status = true;
		}
		else {
			status = false;
		}
		return status;
	}
	
	public void performOnHover(HoverEvent event, boolean hoveringOn){
		for(ResourceCluster resource:this.resourceList){
			resource.getIcon().setParentSelectedStatus(hoveringOn);
		}
	}


	public static SpaceHexagon createPlaceHolder() {
		return new SpaceHexagon();
	}

	public StorageHandler getStorageHandler(){
		return new HandlerListStorageHandler(
				new IdentityStorageHandler(this),
				new LocationStorageHandler(this),
				new StorableStorageHandler<SpaceSystem>(spaceSystem),
				new ResourceClusterListStorageHandler(this));
	}

	@Override
	public Location getLocation() {
		return location;
	}


	@Override
	public void setLocation(Location location) {
		this.location = location;
	}


	@Override
	public void reposition(float x, float y){
		x+=location.getX()*dim.getWidth()+
				(location.getY()%2==0?
						dim.getWidth()/2f:
							0f
						);
		y+=location.getY()*dim.getHeight()*0.8f;
		super.reposition(x, y);
	}
	@Override
	public OffsetHandler createOffsetHandler(final GraphicElement element){
		if(element==overGon){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return dim.getWidth()*0.05f;
				}
				@Override
				public float getY(){
					return dim.getHeight()*0.05f;
				}
				@Override
				public float getWidth(float w){
					return w*0.9f;
				}
				@Override
				public float getHeight(float w){
					return w*0.9f;
				}
			};
		}
		else if(element==spaceSystem){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return dim.getWidth()*0.3f;
				}
				@Override
				public float getY(){
					return dim.getHeight()*0.3f;
				}
				@Override
				public float getWidth(float w){
					return w*0.5f;
				}
				@Override
				public float getHeight(float w){
					return w*0.25f;
				}
			};
		}
		else if(element instanceof Iconic){
			return new OffsetHandler(){
				@Override
				public float getX(){
					Iconic icon = ((Iconic)element);
					if(icon.getId()==2){
						return dim.getWidth()-element.dim.getWidth();
					}
					else if(icon.getId()==1){
						return dim.getWidth()/2f-element.dim.getWidth()/2f;
					}
					return super.getX();
				}
				@Override
				public float getY(){
					Iconic icon = ((Iconic)element);
					if(icon.getId()==0||icon.getId()==2){
						return dim.getHeight()*0.2f;
					}
					else if(icon.getId()==1){
						return dim.getHeight()-element.dim.getHeight();
					}
					return super.getY();
				}
				@Override
				public float getWidth(float w){
					return w*0.15f;
				}
				@Override
				public float getHeight(float h){
					return h*0.15f;
				}
			};
		}
		else return super.createOffsetHandler(element);
	}

	public void onCreate(int owner) {
		this.setId(owner);
		this.spaceSystem.onCreate();
		for(int i=0;i<ResourceCluster.numberOfClusters;++i){
			ResourceCluster resource = new ResourceCluster(i);
			resource.onCreate();
			this.resourceList.add(resource);
		}
	}


	@Override
	public Integer getId() {
		return ownerId;
	}


	@Override
	public void setId(int id) {
		this.ownerId = id;
		if(ownerId==HOST_PLAYER_ID){
			this.setFrame(R.COLOUR_BLUE);
		}
		else if(ownerId%FRIENDLY_HUMAN_PLAYER_ID==0){
			this.setFrame(R.COLOUR_GREEN);
		}
		else if(ownerId%ENEMY_HUMAN_PLAYER_ID==0){
			this.setFrame(R.COLOUR_RED);
		}
		else if(ownerId%FIENDLY_NPC_ID==0){
			this.setFrame(R.COLOUR_CYAN);
		}
		else if(ownerId%ENEMY_NPC_ID==0){
			this.setFrame(R.COLOUR_RED);
		}
		else if(ownerId%UNCLAIMED_ID==0){
			this.setFrame(R.COLOUR_WHITE);
		}
	}

	@Override
	public boolean onClick(ClickEvent event){
		if(dim.isWithin(event.getX(), event.getY())){
			if(event.getAction()==ClickEvent.ACTION_UP){
			}
			return super.onClick(event);
		}
		else return false;
	}

	public static int createId(int base){
		return (int) Math.pow(base, 2);
	}
	public static int createId(int base,int index){
		return (int) Math.pow(base, index+2);
	}
	public static int getIndexFromId(int base, int id){
		if(id%base!=0)return -2;
		return (int) Math.pow(id, 1f/base)-2;
	}

	@Override
	public void update(double secondsSinceLastFrame){
		super.update(secondsSinceLastFrame);
		for(ResourceCluster cluster:resourceList){
			cluster.update((float)secondsSinceLastFrame);
		}
	}

	public static boolean isFriendlyState(int ownerId) {
		return ownerId==HOST_PLAYER_ID||
				ownerId%FRIENDLY_HUMAN_PLAYER_ID==0||
				ownerId%FIENDLY_NPC_ID==0;
	}

	public List<ResourceCluster> getResources() {
		return resourceList;
	}
	
}
