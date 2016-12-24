package com.rem.wfs;

import com.rem.core.storage.Storable;
import com.rem.wfs.environment.resource.ResourceContainer;

public interface Creatable extends Storable{

	public void onCreate(ResourceContainer container);
}
