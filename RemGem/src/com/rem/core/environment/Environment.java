package com.rem.core.environment;


import com.rem.core.Action;
import com.rem.core.gui.Updatable;
import com.rem.core.gui.graphics.GraphicElement;
import com.rem.core.gui.graphics.GraphicEntity;
import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.StorageHandler;

public abstract class Environment extends GraphicEntity implements StorageHandler, Updatable {

	
	protected String name;
	protected String fileName;

	public abstract StorageHandler getStorageHandler();
	public abstract Object getEntity(int id);
	public abstract void addEntity(int id, Object object);
	public abstract void onCreate();
	public abstract void restart(Action<Object> onComplete);
	public Environment() {
		this(new GraphicElement());
	}
	public Environment(GraphicElement element) {
		super(element);
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
	public void load(DataPresenter data) {
		getStorageHandler().load(data);
	}
	@Override
	public void save(DataCollector toSave) {
		getStorageHandler().save(toSave);
	}
	

}
