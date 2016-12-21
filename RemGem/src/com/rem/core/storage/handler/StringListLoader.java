package com.rem.core.storage.handler;

import java.io.IOException;
import java.util.List;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;

public class StringListLoader extends ListStorageHandler<String>{

	public StringListLoader(List<String> toHandle, int maxSize) {
		super(toHandle, maxSize);
	}
	@Override
	public String loadObject(DataPresenter data) throws IOException {
		return data.nextString();
	}
	@Override
	public void saveObject(DataCollector toSave, String element) throws IOException {
		toSave.collect(element);
	}
}
