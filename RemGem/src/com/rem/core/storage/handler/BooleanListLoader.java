package com.rem.core.storage.handler;

import java.util.List;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;

public class BooleanListLoader extends ListStorageHandler<Boolean>{

	public BooleanListLoader(List<Boolean> toHandle) {
		super(toHandle);
	}
	public BooleanListLoader(List<Boolean> toHandle, int maxSize) {
		super(toHandle, maxSize);
	}
	@Override
	public Boolean loadObject(DataPresenter data) {
		return data.nextBoolean();
	}

	@Override
	public void save(DataCollector toSave) {
		if(handleSize){
			toSave.collect(list.size());
		}
		for(Boolean element:list){
			toSave.collect(element);
		}
	}

}
