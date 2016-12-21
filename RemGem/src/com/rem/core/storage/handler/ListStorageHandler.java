package com.rem.core.storage.handler;

import java.io.IOException;
import java.util.List;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.StorageHandler;

public abstract class ListStorageHandler <T extends Object> implements StorageHandler{
	protected final List<T> list;
	private final int maxSize;

	public ListStorageHandler(List<T> toLoad, int maxSize){
		this.list = toLoad;
		this.maxSize = maxSize;
	}

	@Override
	public void load(DataPresenter data) throws IOException {
		if(maxSize==-1){
			int size = data.nextInteger();
			for(int loaded = 0;hasNext(loaded,size);++loaded){
				list.add(loadObject(data));
			}
		}
		else {
			for(int loaded = 0;hasNext(loaded,maxSize);++loaded){
				list.add(loadObject(data));
			}
		}
		
	}
	
	@Override
	public void save(DataCollector toSave) throws IOException{
		if(maxSize==-1){
			toSave.collect(list.size());
		}
		for(T element:list){
			saveObject(toSave, element);
		}
	}

	public boolean hasNext(int loaded, int size){
		return loaded<size;
	}
	public abstract T loadObject(DataPresenter data) throws IOException;

	public abstract void saveObject(DataCollector toSave, T element) throws IOException;
}
