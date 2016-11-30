package com.rem.core.storage.handler;

import java.util.List;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.Storable;

public abstract class StorableListStorageHandler<T extends Storable> extends ListStorageHandler<T>{
	public StorableListStorageHandler(List<T> toHandle) {
		super(toHandle);
	}
	public StorableListStorageHandler(List<T> toHandle, int maxSize) {
		super(toHandle,maxSize);
	}
	public StorableListStorageHandler(List<T> toHandle, boolean handleMax) {
		super(toHandle,handleMax);
	}
	@Override
	public void save(DataCollector toSave) {
		if(handleSize){
			toSave.collect(list.size());
		}
		for(T element:list){
			element.getStorageHandler().save(toSave);
		}
	}

	@Override
	public T loadObject(DataPresenter data) {
		T newT = createPlaceHolder();
		newT.getStorageHandler().load(data);
		return newT;
	}
	
	public abstract T createPlaceHolder();

}
