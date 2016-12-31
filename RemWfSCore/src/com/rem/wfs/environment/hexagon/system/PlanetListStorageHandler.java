package com.rem.wfs.environment.hexagon.system;

import java.util.List;

import com.rem.core.storage.handler.StorableListStorageHandler;

public class PlanetListStorageHandler extends StorableListStorageHandler<Planet> {

	private SpaceSystem parentSystem;
	public PlanetListStorageHandler(SpaceSystem system, List<Planet> toHandle) {
		super(toHandle,-1);
		this.parentSystem = system;
	}
	@Override
	public Planet createObject() {
		return new Planet(parentSystem);
	}
	
	

}
