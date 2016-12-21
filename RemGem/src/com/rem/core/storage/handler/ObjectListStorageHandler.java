package com.rem.core.storage.handler;


import java.io.IOException;
import java.util.List;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.StorageHandler;

public abstract class ObjectListStorageHandler<T extends StorageHandler> extends ListStorageHandler<T>{

	public ObjectListStorageHandler(List<T> toHandle, int maxSize) {
		super(toHandle, maxSize);
	}

	@Override
	public void saveObject(DataCollector toSave, T element) throws IOException {
		element.save(toSave);
	}

}
