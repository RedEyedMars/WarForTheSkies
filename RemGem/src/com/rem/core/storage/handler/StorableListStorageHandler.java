package com.rem.core.storage.handler;

import java.io.IOException;
import java.util.List;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.Storable;

public abstract class StorableListStorageHandler<T extends Storable> extends ListStorageHandler<T>{
	public StorableListStorageHandler(List<T> toHandle, int maxSize) {
		super(toHandle, maxSize);
	}
	
	@Override
	public T collectObject(DataCollector data, T element) throws IOException {
		element.getStorageHandler().collect(data);
		return element;
	}
	

}
