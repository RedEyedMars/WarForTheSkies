package com.rem.wfs.environment.resource;

import com.rem.core.storage.Storable;

public abstract class StockType<T extends Storable> extends ResourceType{

	public StockType(String name, String description,
			String iconTexture, int iconFrame, 
			String iconBackgroundTexture, int iconBackgroundFrame) {
		super(name, description, iconTexture, iconFrame, iconBackgroundTexture, iconBackgroundFrame);
	}
	@Override
	public SpaceResource createPlaceHolder(ResourceContainer container) {
		return new StockList<T>(container, this);
	}
	public abstract T createObjectPlaceHolder();

}
