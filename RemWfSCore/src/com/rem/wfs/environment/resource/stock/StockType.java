package com.rem.wfs.environment.resource.stock;

import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.ResourceType;
public abstract class StockType<T extends StockElement> extends ResourceType<StockList<T>>{

	public StockType(String name, String description,
			int iconTexture, int iconFrame, 
			int iconBackgroundTexture, int iconBackgroundFrame) {
		super(name, description, iconTexture, iconFrame, iconBackgroundTexture, iconBackgroundFrame);
	}
	@Override
	public StockList<T> createPlaceHolder(ResourceContainer container) {
		return new StockList<T>(container, this);
	}
	public abstract T createObjectPlaceHolder(StockList<T> parent);

}
