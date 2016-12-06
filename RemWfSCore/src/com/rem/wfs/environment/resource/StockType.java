package com.rem.wfs.environment.resource;

import com.rem.wfs.Creatable;

public abstract class StockType<T extends Creatable> extends ResourceType<StockList<T>>{

	public StockType(String name, String description,
			int iconTexture, int iconFrame, 
			int iconBackgroundTexture, int iconBackgroundFrame) {
		super(name, description, iconTexture, iconFrame, iconBackgroundTexture, iconBackgroundFrame);
	}
	@Override
	public StockList<T> createPlaceHolder(ResourceContainer container) {
		return new StockList<T>(container, this);
	}
	public abstract T createObjectPlaceHolder();

}
