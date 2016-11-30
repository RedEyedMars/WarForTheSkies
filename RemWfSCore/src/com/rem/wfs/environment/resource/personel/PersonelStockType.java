package com.rem.wfs.environment.resource.personel;

import com.rem.wfs.Icon;
import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.ResourceIcon;
import com.rem.wfs.environment.resource.SpaceResource;
import com.rem.wfs.environment.resource.StockType;

public class PersonelStockType extends StockType<Personel> {

	private static final int TEXTURE_FRAME_POPULATION = 27;	
	private static final int TEXTURE_FRAME_POPULATION_BACKGROUND = 31;
	
	public PersonelStockType() {
		super("Personel",
				"Ships have varying Crew requirements.",
				"space_objects",TEXTURE_FRAME_POPULATION,
				"space_objects",TEXTURE_FRAME_POPULATION_BACKGROUND);
	}

	@Override
	public Personel createObjectPlaceHolder() {
		return new Personel();
	}
	@Override
	public Icon createIcon(SpaceResource spaceResource) {
		return new ResourceIcon(spaceResource,this,ResourceIcon.LEFT_JUSTIFIED);
	}


	@Override
	public float generateInitialValue(ResourceContainer container) { return 0f; }
	@Override
	public int generateInitialLimit(ResourceContainer container) { return (int) (Math.random()*16); }
	@Override
	public float generateInitialGrowth(ResourceContainer container) { return (float) (Math.random()*0.04+0.03); }

}
