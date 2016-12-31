package com.rem.wfs.environment.resource;

import java.io.IOException;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.StorageHandler;

public class ResourceStorageHandler<T extends SpaceResource<T>> implements StorageHandler{
	private SpaceResource<T> resource;

	public ResourceStorageHandler(SpaceResource<T> resource){
		this.resource = resource;
	}

	@Override
	public void collect(DataCollector data) throws IOException {
		resource.setValue(data.collect(resource.getValue()));
		resource.setLimit(data.collect(resource.getLimit()));
		resource.setGrowth(data.collect(resource.getGrowth()));
	}
}
