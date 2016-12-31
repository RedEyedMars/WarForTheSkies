package com.rem.core.storage;

import java.io.IOException;

public interface StorageHandler {
	public void collect(DataCollector toSave) throws IOException;
}
