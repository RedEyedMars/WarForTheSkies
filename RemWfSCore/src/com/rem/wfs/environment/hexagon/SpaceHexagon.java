package com.rem.wfs.environment.hexagon;

import java.util.ArrayList;
import java.util.List;

import com.rem.core.gui.graphics.GraphicElement;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.gui.inputs.HoverEvent;
import com.rem.core.storage.Storable;
import com.rem.core.storage.StorageHandler;
import com.rem.core.storage.handler.HandlerListStorageHandler;
import com.rem.core.storage.handler.StorableStorageHandler;
import com.rem.wfs.Icon;
import com.rem.wfs.environment.Identifiable;
import com.rem.wfs.environment.IdentityStorageHandler;
import com.rem.wfs.environment.hexagon.system.SpaceSystem;
import com.rem.wfs.environment.location.Locatable;
import com.rem.wfs.environment.location.Location;
import com.rem.wfs.environment.location.LocationStorageHandler;
import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.ResourceListStorageHandler;
import com.rem.wfs.environment.resource.ResourceType;
import com.rem.wfs.environment.resource.SpaceResource;

public class SpaceHexagon extends Hexagon implements ResourceContainer, Storable, Locatable, Identifiable{

	private static final int OVER_GON_INDEX = 0;
	private static final int SPACE_SYSTEM_INDEX = 1;

	public static final int HOST_PLAYER_ID = 0;
	public static final int FRIENDLY_HUMAN_PLAYER_ID = 2;
	public static final int ENEMY_HUMAN_PLAYER_ID = 3;
	public static final int FIENDLY_NPC_ID = 5;
	public static final int ENEMY_NPC_ID = 7;
	public static final int UNCLAIMED_ID = 11;

	private static SpaceHexagon currentlySelected = null;

	private Hexagon overGon;
	private int ownerId;
	private Location location;
	private SpaceSystem spaceSystem;
	private List<SpaceResource> resourceList = new ArrayList<SpaceResource>(){
		private static final long serialVersionUID = 6887318772910988733L;
		@Override
		public boolean add(SpaceResource arg){
			addChild(arg.getIcon());
			arg.getIcon().setVisible(isFriendlyState(ownerId));
			return super.add(arg);
		}
		@Override
		public boolean remove(Object arg){
			int index = indexOf(arg);
			if(index!=-1){
				removeChild(index);
				super.remove(index);
				return true;
			}
			else return false;
		}
	};

	public SpaceHexagon() {
		super(new GraphicElement("solid_colour",GraphicElement.COLOUR_BLUE));

		overGon = new Hexagon(new GraphicElement("space_background",(int) (Math.random()*8)));
		addChild(overGon);

		spaceSystem = new SpaceSystem();
		addChild(spaceSystem);	

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
		for(SpaceResource resource:this.resourceList){
			resource.getIcon().setParentSelectedStatus(status);
			resource.getIcon().onHover(event);
		}
		return status;
	}

	public List<SpaceResource> getResources() {
		return resourceList ;
	}

	public static SpaceHexagon createPlaceHolder() {
		return new SpaceHexagon();
	}

	public StorageHandler getStorageHandler(){
		return new HandlerListStorageHandler(
				new IdentityStorageHandler(this),
				new LocationStorageHandler(this),
				new StorableStorageHandler<SpaceSystem>(spaceSystem),
				new ResourceListStorageHandler(this));
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
		x+=location.getX()*getWidth()+(location.getY()%2==0?getWidth()/2f:0f);
		y+=location.getY()*getHeight()*0.8f;
		super.reposition(x, y);
	}
	@Override
	public float offsetX(int index){
		if(index==OVER_GON_INDEX){
			return getWidth()*0.05f;
		}
		else if(index==SPACE_SYSTEM_INDEX){
			return getWidth()*0.25f;
		}

		else if(getChild(index) instanceof Icon){
			Icon icon = ((Icon)getChild(index));
			if(icon.getId()<resourceList.size()/2){
				return getWidth()*0.05f;
			}
			else return getWidth()*0.7f;
		}
		else return super.offsetX(index);
	}
	@Override
	public float offsetY(int index){
		if(index==OVER_GON_INDEX){
			return getHeight()*0.05f;
		}
		else if(index==SPACE_SYSTEM_INDEX){
			return getHeight()*0.25f;
		}
		else if(getChild(index) instanceof Icon){
			Icon icon = ((Icon)getChild(index));
			if(icon.getId()<resourceList.size()/2){
				return getHeight()*0.65f-icon.getId()*getHeight()*0.6f/(resourceList.size()/2);
			}
			else {
				return getHeight()*0.65f-(icon.getId()-4)*getHeight()*0.6f/(resourceList.size()/2);
			}
		}
		else return super.offsetY(index);
	}
	@Override
	public void resize(float x, float y){
		super.resize(x, y);
		overGon.resize(x*0.9f, y*0.9f);
		spaceSystem.resize(x*0.5f, y*0.5f);

		for(int i=0;i<resourceList.size();++i){
			resourceList.get(i).getIcon().resize(x*0.15f, y*0.15f);
		}
	}


	public void onCreate(int owner) {
		this.setId(owner);
		this.spaceSystem.onCreate();
		for(int i=0;i<ResourceType.types.size();++i){
			SpaceResource resource = new SpaceResource(this,i);
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
			this.setFrame(GraphicElement.COLOUR_BLUE);
		}
		else if(ownerId%FRIENDLY_HUMAN_PLAYER_ID==0){
			this.setFrame(GraphicElement.COLOUR_GREEN);
		}
		else if(ownerId%ENEMY_HUMAN_PLAYER_ID==0){
			this.setFrame(GraphicElement.COLOUR_RED);
		}
		else if(ownerId%FIENDLY_NPC_ID==0){
			this.setFrame(GraphicElement.COLOUR_CYAN);
		}
		else if(ownerId%ENEMY_NPC_ID==0){
			this.setFrame(GraphicElement.COLOUR_RED);
		}
		else if(ownerId%UNCLAIMED_ID==0){
			this.setFrame(GraphicElement.COLOUR_WHITE);
		}

		for(SpaceResource resource:resourceList){
			resource.getIcon().setVisible(
					isFriendlyState(ownerId));
		}
	}

	@Override
	public void performOnRelease(ClickEvent e){
		if(currentlySelected!=null||currentlySelected==this){
			for(SpaceResource resource:currentlySelected.resourceList){
				resource.getIcon().setVisible(
						isFriendlyState(currentlySelected.ownerId));
			}
		}
		if(currentlySelected!=this){
			currentlySelected = this;
			for(SpaceResource resource:resourceList){
				resource.getIcon().setVisible(true);
			}
		}
		else {
			currentlySelected = null;
		}
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
	}

	public static boolean isFriendlyState(int ownerId) {
		return ownerId==HOST_PLAYER_ID||
				ownerId%FRIENDLY_HUMAN_PLAYER_ID==0||
				ownerId%FIENDLY_NPC_ID==0;
	}
}
