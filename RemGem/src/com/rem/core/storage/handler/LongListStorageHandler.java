package com.rem.core.storage.handler;

import java.util.List;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;

public class LongListStorageHandler extends ListStorageHandler<Long>{

	public LongListStorageHandler(List<Long> toHandle) {
		super(toHandle);
	}
	public LongListStorageHandler(List<Long> toHandle, int maxSize) {
		super(toHandle, maxSize);
	}
	public LongListStorageHandler(List<Long> toHandle, boolean handleSize) {
		super(toHandle, handleSize);
	}
	@Override
	public Long loadObject(DataPresenter data) {
		return data.nextLong();
	}

	@Override
	public void save(DataCollector toSave) {
		if(handleSize){
			toSave.collect(list.size());
		}
		for(Long element:list){
			toSave.collect(element);
		}
	}

}
