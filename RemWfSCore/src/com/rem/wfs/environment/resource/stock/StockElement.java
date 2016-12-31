package com.rem.wfs.environment.resource.stock;

import com.rem.wfs.Creatable;
import com.rem.wfs.environment.resource.ResourceContainer;

public interface StockElement extends Creatable{
	public void grow(ResourceContainer container, float seconds);

}
