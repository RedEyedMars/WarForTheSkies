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
	public T collectObject(DataCollector data, T element) throws IOException {
		element.collect(data);
		return element;
	}

}
