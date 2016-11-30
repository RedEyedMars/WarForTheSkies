package com.rem.core.storage.handler;

import java.util.List;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;

public class FloatListStorageHandler extends ListStorageHandler<Float>{

	public FloatListStorageHandler(List<Float> toHandle) {
		super(toHandle);
	}
	public FloatListStorageHandler(List<Float> toHandle, int maxSize) {
		super(toHandle, maxSize);
	}
	@Override
	public Float loadObject(DataPresenter data) {
		return data.nextFloat();
	}

	@Override
	public void save(DataCollector toSave) {
		if(handleSize){
			toSave.collect(list.size());
		}
		for(Float element:list){
			toSave.collect(element);
		}
	}
}
