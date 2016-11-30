package com.rem.core.storage.handler;

import java.util.List;

import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.StorageHandler;

public abstract class ListStorageHandler <T extends Object> implements StorageHandler{
	protected List<T> list;
	private int maxSize = Integer.MAX_VALUE;
	private int loaded = 0;
	protected boolean handleSize = false;

	public ListStorageHandler(List<T> toLoad){
		this(toLoad,true);
	}
	public ListStorageHandler(List<T> toHandle, int maxSize){
		this.list = toHandle;
		this.maxSize = maxSize;
		this.handleSize = false;
		this.loaded = 0;
	}
	public ListStorageHandler(List<T> toLoad, boolean handleSize){
		this.list = toLoad;
		this.handleSize = handleSize;
		this.loaded = 0;
	}

	@Override
	public void load(DataPresenter data) {
		if(handleSize){
			maxSize=data.nextInteger();
		}
		for(this.loaded = 0;hasNext();++loaded){
			list.add(loadObject(data));
		}
	}

	public boolean hasNext(){
		return loaded<maxSize;
	}
	public abstract T loadObject(DataPresenter data);
}
