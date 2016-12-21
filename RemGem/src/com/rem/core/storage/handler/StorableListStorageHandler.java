package com.rem.core.storage.handler;

import java.io.IOException;
import java.util.List;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.Storable;

public abstract class StorableListStorageHandler<T extends Storable> extends ListStorageHandler<T>{
	public StorableListStorageHandler(List<T> toHandle, int maxSize) {
		super(toHandle, maxSize);
	}
	@Override
	public void saveObject(DataCollector toSave, T element) throws IOException {
		element.getStorageHandler().save(toSave);
	}

	@Override
	public T loadObject(DataPresenter data) throws IOException {
		T newT = createPlaceHolder();
		newT.getStorageHandler().load(data);
		return newT;
	}
	
	public abstract T createPlaceHolder();

}
