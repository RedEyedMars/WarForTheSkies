package com.rem.wfs.environment.hexagon;

import com.rem.core.storage.handler.StorableListStorageHandler;
import com.rem.wfs.environment.SpaceSector;

public class SpaceHexagonListStorageHandler extends StorableListStorageHandler<SpaceHexagon>{
	
	public SpaceHexagonListStorageHandler(SpaceSector spaceSector){
		super(spaceSector.getHexagons(),-1);
	}

	@Override
	public SpaceHexagon createObject() {
		return SpaceHexagon.createPlaceHolder();
	}

	

	
}
