package com.rem.wfs.environment.resource.personel;

import com.rem.core.storage.Storable;
import com.rem.core.storage.StorageHandler;
import com.rem.core.storage.handler.HandlerListStorageHandler;

public class Personel implements Storable {

	private static final PersonelStockType PERSONEL = new PersonelStockType();

	public static final int ID_POPULATION = PERSONEL.getId();	
	@Override
	public StorageHandler getStorageHandler() {
		return new HandlerListStorageHandler();
	}

}
