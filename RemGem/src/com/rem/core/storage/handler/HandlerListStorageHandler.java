package com.rem.core.storage.handler;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.StorageHandler;

public class HandlerListStorageHandler implements StorageHandler{
	private StorageHandler[] list;

	public HandlerListStorageHandler(StorageHandler... toLoad){
		this.list = toLoad;
	}

	@Override
	public void load(DataPresenter data) {
		for(int i=0;i<list.length;++i){
			list[i].load(data);
		}
	}

	@Override
	public void save(DataCollector toSave) {
		for(int i=0;i<list.length;++i){
			list[i].save(toSave);
		}
	}
}
