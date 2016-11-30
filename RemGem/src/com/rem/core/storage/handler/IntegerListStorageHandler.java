package com.rem.core.storage.handler;

import java.util.List;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;

public class IntegerListStorageHandler extends ListStorageHandler<Integer>{

	public IntegerListStorageHandler(List<Integer> toHandle) {
		super(toHandle);
	}
	public IntegerListStorageHandler(List<Integer> toHandle, int maxSize) {
		super(toHandle, maxSize);
	}
	@Override
	public Integer loadObject(DataPresenter data) {
		return data.nextInteger();
	}

	@Override
	public void save(DataCollector toSave) {
		if(handleSize){
			toSave.collect(list.size());
		}
		for(Integer element:list){
			toSave.collect(element);
		}
	}

}
