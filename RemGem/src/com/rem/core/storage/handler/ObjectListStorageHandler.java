package com.rem.core.storage.handler;


import java.util.List;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.StorageHandler;

public abstract class ObjectListStorageHandler<T extends StorageHandler> extends ListStorageHandler<T>{

	public ObjectListStorageHandler(List<T> toHandle) {
		super(toHandle);
	}
	public ObjectListStorageHandler(List<T> toHandle, boolean handleSize) {
		super(toHandle, handleSize);
	}
	public ObjectListStorageHandler(List<T> toHandle, int maxSize) {
		super(toHandle, maxSize);
	}

	@Override
	public void save(DataCollector toSave) {
		if(handleSize){
			toSave.collect(list.size());
		}
		for(T element:list){
			element.save(toSave);
		}
	}


}
