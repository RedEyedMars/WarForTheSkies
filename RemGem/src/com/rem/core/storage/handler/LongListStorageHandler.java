package com.rem.core.storage.handler;

import java.io.IOException;
import java.util.List;

import com.rem.core.storage.DataCollector;

public class LongListStorageHandler extends ListStorageHandler<Long>{

	public LongListStorageHandler(List<Long> toHandle, int maxSize) {
		super(toHandle, maxSize);
	}
	@Override
	public Long createObject() throws IOException {
		return 0L;
	}

	@Override
	public Long collectObject(DataCollector data, Long element) throws IOException {
		return data.collect(element);
	}

}
