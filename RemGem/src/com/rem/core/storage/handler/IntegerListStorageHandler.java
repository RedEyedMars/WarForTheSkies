package com.rem.core.storage.handler;

import java.io.IOException;
import java.util.List;

import com.rem.core.storage.DataCollector;

public class IntegerListStorageHandler extends ListStorageHandler<Integer>{

	public IntegerListStorageHandler(List<Integer> toHandle, int maxSize) {
		super(toHandle, maxSize);
	}
	@Override
	public Integer createObject() throws IOException {
		return 0;
	}

	@Override
	public Integer collectObject(DataCollector data, Integer element) throws IOException {
		return data.collect(element);
	}

}
