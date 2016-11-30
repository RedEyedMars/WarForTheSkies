package com.rem.wfs.environment.hexagon;

import com.rem.core.storage.handler.StorableListStorageHandler;
import com.rem.wfs.environment.SpaceSector;

public class SpaceHexagonListStorageHandler extends StorableListStorageHandler<SpaceHexagon>{
	
	public SpaceHexagonListStorageHandler(SpaceSector spaceSector){
		super(spaceSector.getHexagons(),true);
	}

	@Override
	public SpaceHexagon createPlaceHolder() {
		return SpaceHexagon.createPlaceHolder();
	}

	

	
}
