package com.rem.core.storage.handler;


import java.io.IOException;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.StorageHandler;

public class ObjectStorageHandler<T extends StorageHandler> implements StorageHandler{

	private T toHandle;

	public ObjectStorageHandler(T toHandle) {
		this.toHandle = toHandle;
	}

	@Override
	public void save(DataCollector toSave) throws IOException {
		toHandle.save(toSave);
	}

	@Override
	public void load(DataPresenter data) throws IOException {
		toHandle.load(data);
	}


}
