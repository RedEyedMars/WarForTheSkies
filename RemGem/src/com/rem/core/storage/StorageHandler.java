package com.rem.core.storage;

import java.io.IOException;

public interface StorageHandler {
	public void load(DataPresenter data) throws IOException;
	public void save(DataCollector toSave) throws IOException;
}
