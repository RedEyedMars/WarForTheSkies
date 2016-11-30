package com.rem.core.storage;


public interface StorageHandler {
	public void load(DataPresenter data);
	public void save(DataCollector toSave);
}
