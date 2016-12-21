package com.rem.wfs.environment;

import java.io.IOException;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.StorageHandler;

public class IdentityStorageHandler implements StorageHandler{

	private Identifiable ider;

	public IdentityStorageHandler(Identifiable ider){
		this.ider = ider;
	}
	
	@Override
	public void load(DataPresenter data) throws IOException {
		this.ider.setId(data.nextInteger());
	}

	@Override
	public void save(DataCollector toSave) throws IOException {
		toSave.collect(this.ider.getId());
	}

}
