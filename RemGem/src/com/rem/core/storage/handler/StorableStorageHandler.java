package com.rem.core.storage.handler;


import java.io.IOException;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.Storable;
import com.rem.core.storage.StorageHandler;

public class StorableStorageHandler<T extends Storable> implements StorageHandler{

	private T toHandle;

	public StorableStorageHandler(T toHandle) {
		this.toHandle = toHandle;
	}

	@Override
	public void collect(DataCollector data) throws IOException {
		toHandle.getStorageHandler().collect(data);
	}


}
