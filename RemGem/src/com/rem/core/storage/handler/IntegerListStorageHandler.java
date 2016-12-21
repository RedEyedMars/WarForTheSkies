package com.rem.core.storage.handler;

import java.io.IOException;
import java.util.List;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;

public class IntegerListStorageHandler extends ListStorageHandler<Integer>{

	public IntegerListStorageHandler(List<Integer> toHandle, int maxSize) {
		super(toHandle, maxSize);
	}
	@Override
	public Integer loadObject(DataPresenter data) throws IOException {
		return data.nextInteger();
	}

	@Override
	public void saveObject(DataCollector toSave, Integer element) throws IOException {
		toSave.collect(element);
	}

}
