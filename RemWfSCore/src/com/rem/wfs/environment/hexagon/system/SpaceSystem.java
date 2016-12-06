package com.rem.wfs.environment.hexagon.system;

import java.util.ArrayList;
import java.util.List;

import com.rem.core.gui.graphics.GraphicElement;
import com.rem.core.gui.graphics.GraphicEntity;
import com.rem.core.storage.Storable;
import com.rem.core.storage.StorageHandler;
import com.rem.core.storage.handler.HandlerListStorageHandler;
import com.rem.wfs.environment.Identifiable;
import com.rem.wfs.environment.IdentityStorageHandler;
import com.rem.wfs.graphics.R;

public class SpaceSystem extends GraphicEntity implements Storable, Identifiable{

	private static final int DEBRIS = 0;
	private static final int BROWN_DWARF = 1;
	private static final int RED_DWARF = 2;
	private static final int SMALL_STAR = 3;
	private static final int MEDIUM_STAR = 4;
	private static final int LARGE_STAR = 5;
	private static final int BLUE_GIANT = 6;
	private static final int RED_GIANT = 7;

	private static final int DEBRIS_FRAME = 0;
	private static final int BROWN_DWARF_FRAME = 1;
	private static final int RED_DWARF_FRAME = 2;
	private static final int SMALL_STAR_FRAME = 3;
	private static final int MEDIUM_STAR_FRAME = 4;
	private static final int LARGE_STAR_FRAME = 5;
	private static final int BLUE_GIANT_FRAME = 6;
	private static final int RED_GIANT_FRAME = 7;
	
	private static final int NUMBER_OF_STATES = 8;
	
	private int id;
	private List<Planet> planets = new ArrayList<Planet>(){
		private static final long serialVersionUID = 241080939028853923L;
		@Override
		public boolean add(Planet planet){
			addChild(planet);
			return super.add(planet);
		}
		@Override
		public boolean remove(Object planet){
			if(planet instanceof Planet){
				removeChild((Planet)planet);
			}
			return super.remove(planet);
		}
	};

	public SpaceSystem() {
		super(new GraphicElement(R.space_objects));
	}

	@Override
	public StorageHandler getStorageHandler() {
		return new HandlerListStorageHandler(
				new IdentityStorageHandler(this),
				new PlanetListStorageHandler(this,planets,true));
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
		this.setFrame(convertIdToFrame(id));
	}
	
	@Override
	public float offsetX(int index){
		if(getChild(index) instanceof Planet){
			return getWidth()*2/3f+((Planet)getChild(index)).getDistance()*getWidth()/2f/planets.size();
		}
		else return super.offsetX(index);
	}
	@Override
	public float offsetY(int index){
		if(getChild(index) instanceof Planet){
			return getHeight()/2f;
		}
		else return super.offsetY(index);
	}
	
	@Override
	public void resize(float x, float y){
		super.resize(x, y);
		for(Planet planet:planets){
			planet.resize(x*0.3f, y*0.3f);
		}
	}
	
	public static int convertIdToFrame(int id){

		switch(id){
		case DEBRIS:return DEBRIS_FRAME;
		case BROWN_DWARF:return BROWN_DWARF_FRAME;
		case RED_DWARF:return RED_DWARF_FRAME;
		case SMALL_STAR:return SMALL_STAR_FRAME;
		case MEDIUM_STAR:return MEDIUM_STAR_FRAME;
		case LARGE_STAR:return LARGE_STAR_FRAME;
		case BLUE_GIANT:return BLUE_GIANT_FRAME;
		case RED_GIANT:return RED_GIANT_FRAME;
		default: throw new RuntimeException("SpaceSystem.convertIdToFrame could not convert the id:"+id+" to a frame index");
		}
	}

	public void onCreate() {
		setId((int) (NUMBER_OF_STATES*Math.random()));
		int numberOfPlanets = (int) (Math.random()*4);
		for(int i=0;i<numberOfPlanets;++i){
			Planet planet = new Planet(this);
			planet.onCreate();
			planet.setDistance(i);
			this.planets.add(planet);
			
		}
	}

}
