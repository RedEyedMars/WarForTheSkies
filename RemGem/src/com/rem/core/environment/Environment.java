package com.rem.core.environment;


import java.io.IOException;

import com.rem.core.Action;
import com.rem.core.gui.Updatable;
import com.rem.core.gui.graphics.elements.BlankGraphicElement;
import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.StorageHandler;

public abstract class Environment extends BlankGraphicElement implements StorageHandler, Updatable {

	
	protected String name;
	protected String fileName;

	public abstract StorageHandler getStorageHandler();
	public abstract Object getEntity(int id);
	public abstract void addEntity(int id, Object object);
	public abstract void onCreate();
	public abstract void restart(Action<Object> onComplete);
	public Environment() {
		super();
	}
	public String getName(){
		return name;
	}
	public String getFileName() {
		return fileName;
	}
	public void setNameAndFileName(String name, String filename) {
		this.name = name;
		this.fileName = filename;
	}
	@Override
	public void load(DataPresenter data) throws IOException {
		getStorageHandler().load(data);
	}
	@Override
	public void save(DataCollector toSave) throws IOException {
		getStorageHandler().save(toSave);
	}
	

}
