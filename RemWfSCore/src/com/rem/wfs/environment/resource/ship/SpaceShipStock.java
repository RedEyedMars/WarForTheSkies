package com.rem.wfs.environment.resource.ship;

import java.util.ArrayList;
import java.util.List;

import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.ResourceIcon;
import com.rem.wfs.environment.resource.StockList;
import com.rem.wfs.environment.resource.StockType;
import com.rem.wfs.graphics.Icon;

public abstract class SpaceShipStock extends StockType<SpaceShip>{
	public static final List<StockType<SpaceShip>> types = new ArrayList<StockType<SpaceShip>>();
	public SpaceShipStock(String name, String description, int iconTexture, int iconFrame,
			int iconBackgroundTexture, int iconBackgroundFrame) {
		super(name, description, iconTexture, iconFrame, iconBackgroundTexture, iconBackgroundFrame);
		types.add(this);
	}

	@Override
	public SpaceShip createObjectPlaceHolder() {
		return new SpaceShip();
	}
	@Override
	public Icon createIcon(StockList<SpaceShip> spaceResource) {
		ResourceIcon<StockList<SpaceShip>> icon =  
				new ResourceIcon<StockList<SpaceShip>>(spaceResource,this,ResourceIcon.RIGHT_JUSTIFIED);
		icon.setSizeFactor(ResourceIcon.LARGE_SIZE);
		return icon;
	}
	@Override
	public float generateInitialValue(ResourceContainer container) { return 0; }
	@Override
	public float generateInitialGrowth(ResourceContainer container) { return 0; }
}
