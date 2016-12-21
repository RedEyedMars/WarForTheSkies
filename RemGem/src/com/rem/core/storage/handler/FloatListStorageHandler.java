package com.rem.core.storage.handler;

import java.io.IOException;
import java.util.List;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;

public class FloatListStorageHandler extends ListStorageHandler<Float>{

	public FloatListStorageHandler(List<Float> toHandle, int maxSize) {
		super(toHandle, maxSize);
	}
	@Override
	public Float loadObject(DataPresenter data) throws IOException {
		return data.nextFloat();
	}

	@Override
	public void saveObject(DataCollector toSave,Float element) throws IOException {
		toSave.collect(element);
	}
}
