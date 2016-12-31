package com.rem.wfs.environment;

import java.io.IOException;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.StorageHandler;

public class IdentityStorageHandler implements StorageHandler{

	private Identifiable ider;

	public IdentityStorageHandler(Identifiable ider){
		this.ider = ider;
	}
	
	@Override
	public void collect(DataCollector data) throws IOException {
		this.ider.setId(data.collect(this.ider.getId()));
	}

}
