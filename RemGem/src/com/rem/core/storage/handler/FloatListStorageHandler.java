package com.rem.core.storage.handler;

import java.io.IOException;
import java.util.List;

import com.rem.core.storage.DataCollector;

public class FloatListStorageHandler extends ListStorageHandler<Float>{

	public FloatListStorageHandler(List<Float> toHandle, int maxSize) {
		super(toHandle, maxSize);
	}
	@Override
	public Float createObject() throws IOException {
		return 0f;
	}

	@Override
	public Float collectObject(DataCollector data,Float element) throws IOException {
		return data.collect(element);
	}
}
