package com.rem.wfs.environment.resource;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.StorageHandler;

public class ResourceStorageHandler<T extends SpaceResource<T>> implements StorageHandler{
	private SpaceResource<T> resource;

	public ResourceStorageHandler(SpaceResource<T> resource){
		this.resource = resource;
	}

	@Override
	public void load(DataPresenter data) {
		resource.setValue(data.nextFloat());
		resource.setLimit(data.nextInteger());
		resource.setGrowth(data.nextFloat());
	}

	@Override
	public void save(DataCollector toSave) {
		toSave.collect(resource.getValue());
		toSave.collect(resource.getLimit());
		toSave.collect(resource.getGrowth());
	}
}
