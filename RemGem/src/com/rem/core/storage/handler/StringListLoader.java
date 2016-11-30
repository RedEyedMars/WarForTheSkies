package com.rem.core.storage.handler;

import java.util.List;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;

public class StringListLoader extends ListStorageHandler<String>{

	public StringListLoader(List<String> toHandle) {
		super(toHandle);
	}
	public StringListLoader(List<String> toHandle, int maxSize) {
		super(toHandle, maxSize);
	}
	@Override
	public String loadObject(DataPresenter data) {
		return data.nextString();
	}
	@Override
	public void save(DataCollector toSave) {
		if(handleSize){
			toSave.collect(list.size());
		}
		for(String element:list){
			toSave.collect(element);
		}
	}
}
