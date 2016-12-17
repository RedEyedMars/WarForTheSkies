package com.rem.wfs.environment.hexagon.system;


import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.Storable;
import com.rem.core.storage.StorageHandler;
import com.rem.core.storage.handler.HandlerListStorageHandler;
import com.rem.wfs.environment.Identifiable;
import com.rem.wfs.environment.IdentityStorageHandler;
import com.rem.wfs.graphics.R;

public class Planet extends GraphicElement implements Storable, Identifiable {
	private static final int ASTEROID = 0;
	private static final int HABITABLE = 1;
	private static final int GAS_GIANT = 2;
	private static final int FROZEN = 3;
	private static final int SULFUROUS = 4;

	private static final int ASTEROID_FRAME = 8;
	private static final int HABITABLE_FRAME = 9;
	private static final int GAS_GIANT_FRAME = 10;
	private static final int FROZEN_FRAME = 12;
	private static final int SULFUROUS_FRAME = 13;

	private static final float ASTEROID_SPEED = 1.5f;
	private static final float HABITABLE_SPEED = 1f;
	private static final float GAS_GIANT_SPEED = 0.25f;
	private static final float FROZEN_SPEED = 0.5f;
	private static final float SULFUROUS_SPEED = 1.25f;

	private static final int NUMBER_OF_STATES = 5;
	private Integer id;
	private Integer distanceFromStar;
	private float speed = 1f;
	private SpaceSystem system;

	private double angleToCenter = Math.random()*Math.PI/2.0;

	public Planet(SpaceSystem parentSystem){
		super(R.space_objects,0,R.BOT_LAYER);
		this.system = parentSystem;
	}

	@Override
	public StorageHandler getStorageHandler() {
		return new HandlerListStorageHandler(
				new IdentityStorageHandler(this),
				new StorageHandler(){
					@Override
					public void load(DataPresenter data){
						distanceFromStar = data.nextInteger();
						angleToCenter = data.nextFloat();
					}
					@Override
					public void save(DataCollector toSave){
						toSave.collect(distanceFromStar);
						toSave.collect((float)angleToCenter);
					}					
				});
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
		this.setFrame(convertIdToFrame(id));
		this.setSpeed(convertIdToSpeed(id));
	}
	public int getDistance(){
		return distanceFromStar;
	}
	public void setDistance(int distanceFromStar) {
		this.distanceFromStar = distanceFromStar;

	}
	public void onCreate() {
		this.setId((int) (NUMBER_OF_STATES*Math.random()));
	}

	public void setSpeed(float speed){
		this.speed = speed;
	}

	@Override
	public void reposition(float x, float y){
		double radius = Math.sqrt(Math.pow(x-(system.dim.getX()+system.dim.getWidth()/3f),2)
				+Math.pow(y-(system.dim.getY()+system.dim.getHeight()/3f),2));
		x=(float) (system.dim.getX()+system.dim.getWidth()/3f+Math.cos(angleToCenter)*radius);
		y=(float) (system.dim.getY()+system.dim.getHeight()/3f+Math.sin(angleToCenter)*radius);
		super.reposition(x, y);
	}
	
	@Override
	public void update(double secondsSinceLastFrame){
		angleToCenter+=secondsSinceLastFrame*speed;
		reposition(dim.getX(),dim.getY());
	}

	public static int convertIdToFrame(int id){
		switch(id){
		case ASTEROID:return ASTEROID_FRAME;
		case HABITABLE:return HABITABLE_FRAME;
		case GAS_GIANT:return GAS_GIANT_FRAME;
		case FROZEN:return FROZEN_FRAME;
		case SULFUROUS:return SULFUROUS_FRAME;
		default: throw new RuntimeException("Planet.convertIdToFrame could not convert id:"+id+" to a frame value");
		}
	}
	public static float convertIdToSpeed(int id){
		switch(id){
		case ASTEROID:return ASTEROID_SPEED;
		case HABITABLE:return HABITABLE_SPEED;
		case GAS_GIANT:return GAS_GIANT_SPEED;
		case FROZEN:return FROZEN_SPEED;
		case SULFUROUS:return SULFUROUS_SPEED;
		default: throw new RuntimeException("Planet.convertIdToFrame could not convert id:"+id+" to a frame value");
		}
	}


}
