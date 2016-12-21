package com.rem.core.storage.handler;

import java.io.IOException;
import java.util.List;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;

public class BooleanListLoader extends ListStorageHandler<Boolean>{

	public BooleanListLoader(List<Boolean> toHandle, int maxSize) {
		super(toHandle, maxSize);
	}
	@Override
	public Boolean loadObject(DataPresenter data) throws IOException {
		return data.nextBoolean();
	}
	
	@Override
	public void saveObject(DataCollector toSave, Boolean element) throws IOException {
		toSave.collect(element);
	}

}
