package com.rem.core.storage.handler;

import java.io.IOException;
import java.util.List;

import com.rem.core.storage.DataCollector;

public class BooleanListLoader extends ListStorageHandler<Boolean>{

	public BooleanListLoader(List<Boolean> toHandle, int maxSize) {
		super(toHandle, maxSize);
	}
	@Override
	public Boolean createObject() throws IOException {
		return false;
	}
	
	@Override
	public Boolean collectObject(DataCollector data, Boolean element) throws IOException {
		return data.collect(element);
	}

}
