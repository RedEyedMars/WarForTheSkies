package com.rem.core.storage.handler;

import java.io.IOException;
import java.util.List;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;

public class LongListStorageHandler extends ListStorageHandler<Long>{

	public LongListStorageHandler(List<Long> toHandle, int maxSize) {
		super(toHandle, maxSize);
	}
	@Override
	public Long loadObject(DataPresenter data) throws IOException {
		return data.nextLong();
	}

	@Override
	public void saveObject(DataCollector toSave, Long element) throws IOException {
		toSave.collect(element);
	}

}
