package com.rem.wfs.environment.location;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.StorageHandler;

public class LocationStorageHandler implements StorageHandler{
	private Locatable locator;

	public LocationStorageHandler(Locatable locator){
		this.locator = locator;
	}

	@Override
	public void load(DataPresenter data) {
		locator.setLocation(new Location(data.nextInteger(), data.nextInteger()));
	}

	@Override
	public void save(DataCollector data) {
		locator.getLocation().save(data);
	}
}
