package com.rem.core.storage.handler;


import java.io.IOException;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.StorageHandler;

public class ObjectStorageHandler<T extends StorageHandler> implements StorageHandler{

	private T toHandle;

	public ObjectStorageHandler(T toHandle) {
		this.toHandle = toHandle;
	}

	@Override
	public void collect(DataCollector data) throws IOException {
		toHandle.collect(data);
	}


}
