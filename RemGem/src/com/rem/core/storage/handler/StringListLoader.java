package com.rem.core.storage.handler;

import java.io.IOException;
import java.util.List;

import com.rem.core.storage.DataCollector;

public class StringListLoader extends ListStorageHandler<String>{

	public StringListLoader(List<String> toHandle, int maxSize) {
		super(toHandle, maxSize);
	}
	@Override
	public String createObject() throws IOException {
		return "";
	}
	@Override
	public String collectObject(DataCollector data, String element) throws IOException {
		return data.collect(element);
	}
}
