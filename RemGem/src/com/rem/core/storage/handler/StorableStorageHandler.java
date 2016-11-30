package com.rem.core.storage.handler;


import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.Storable;
import com.rem.core.storage.StorageHandler;

public class StorableStorageHandler<T extends Storable> implements StorageHandler{

	private T toHandle;

	public StorableStorageHandler(T toHandle) {
		this.toHandle = toHandle;
	}

	@Override
	public void save(DataCollector toSave) {
		toHandle.getStorageHandler().save(toSave);
	}

	@Override
	public void load(DataPresenter data) {
		toHandle.getStorageHandler().load(data);
	}


}
