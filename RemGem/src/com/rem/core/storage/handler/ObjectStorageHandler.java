package com.rem.core.storage.handler;


import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.StorageHandler;

public class ObjectStorageHandler<T extends StorageHandler> implements StorageHandler{

	private T toHandle;

	public ObjectStorageHandler(T toHandle) {
		this.toHandle = toHandle;
	}

	@Override
	public void save(DataCollector toSave) {
		toHandle.save(toSave);
	}

	@Override
	public void load(DataPresenter data) {
		toHandle.load(data);
	}


}
