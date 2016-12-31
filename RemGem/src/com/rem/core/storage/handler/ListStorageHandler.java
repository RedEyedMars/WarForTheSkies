package com.rem.core.storage.handler;

import java.io.IOException;
import java.util.List;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.StorageHandler;

public abstract class ListStorageHandler <T extends Object> implements StorageHandler{
	protected final List<T> list;
	private final int maxSize;

	public ListStorageHandler(List<T> toLoad, int maxSize){
		this.list = toLoad;
		this.maxSize = maxSize;
	}

	@Override
	public void collect(DataCollector data) throws IOException {
		int size = maxSize;
		if(maxSize<=-1){
			size = data.collect(list.size());			
		}
		int loaded = 0;
		for(;loaded<list.size()&&hasNext(loaded,size);++loaded){
			list.set(loaded, collectObject(data, list.get(loaded)));
		}
		while(hasNext(loaded,size)){
			T element = createObject();
			element = collectObject(data,element);
			list.add(element);
			++loaded;
		}
		
		
	}
	
	public boolean hasNext(int loaded, int size){
		return loaded<size;
	}
	public abstract T createObject() throws IOException;

	public abstract T collectObject(DataCollector toSave, T element) throws IOException;
}
