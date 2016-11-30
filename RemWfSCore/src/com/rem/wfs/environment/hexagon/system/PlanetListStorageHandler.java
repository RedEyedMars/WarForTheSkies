package com.rem.wfs.environment.hexagon.system;

import java.util.List;

import com.rem.core.storage.handler.StorableListStorageHandler;

public class PlanetListStorageHandler extends StorableListStorageHandler<Planet> {

	private SpaceSystem parentSystem;
	public PlanetListStorageHandler(SpaceSystem system, List<Planet> toHandle) {
		super(toHandle);
		this.parentSystem = system;
	}
	public PlanetListStorageHandler(SpaceSystem system, List<Planet> toHandle, int maxSize) {
		super(toHandle, maxSize);
		this.parentSystem = system;
	}
	public PlanetListStorageHandler(SpaceSystem system, List<Planet> toHandle, boolean handleMax) {
		super(toHandle, handleMax);
		this.parentSystem = system;
	}
	@Override
	public Planet createPlaceHolder() {
		return new Planet(parentSystem);
	}
	
	

}
