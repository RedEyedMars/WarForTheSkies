package com.rem.wfs.environment.resource;

import com.rem.core.storage.handler.StorableListStorageHandler;

@SuppressWarnings("rawtypes")
public class ResourceListStorageHandler extends StorableListStorageHandler<SpaceResource>{
	private ResourceContainer container;

	public ResourceListStorageHandler(ResourceContainer container){
		super(container.getResources(),ResourceType.types.size());
		this.container = container;
	}

	@Override
	public SpaceResource createPlaceHolder() {
		return ResourceType.types.get(list.size()).createPlaceHolder(container);
	}
}
