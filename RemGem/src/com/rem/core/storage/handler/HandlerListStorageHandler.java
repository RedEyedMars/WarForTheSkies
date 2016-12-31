package com.rem.core.storage.handler;

import java.io.IOException;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.StorageHandler;

public class HandlerListStorageHandler implements StorageHandler{
	private StorageHandler[] list;

	public HandlerListStorageHandler(StorageHandler... toLoad){
		this.list = toLoad;
	}

	@Override
	public void collect(DataCollector data) throws IOException {
		for(int i=0;i<list.length;++i){
			list[i].collect(data);
		}
	}
}
