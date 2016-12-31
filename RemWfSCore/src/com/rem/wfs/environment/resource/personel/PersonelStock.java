package com.rem.wfs.environment.resource.personel;

import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.stock.StockList;
import com.rem.wfs.environment.resource.stock.StockType;
import com.rem.wfs.graphics.R;

public abstract class PersonelStock extends StockType<Personel> {

	private static final int TEXTURE_FRAME_POPULATION = 7;	
	private static final int TEXTURE_FRAME_POPULATION_BACKGROUND = 13;

	public PersonelStock() {
		super("Personel",
				"Ships have varying Crew requirements.",
				R.resources,TEXTURE_FRAME_POPULATION,
				R.resource_backs,TEXTURE_FRAME_POPULATION_BACKGROUND);
	}

	@Override
	public Personel createObjectPlaceHolder(StockList<Personel> parent) {
		return new Personel();
	}
	
	public static  class Standard extends PersonelStock{
		@Override
		public float generateInitialValue(ResourceContainer container) { return 2f; }
		@Override
		public int generateInitialLimit(ResourceContainer container) { return (int) (Math.random()*16)+2; }
		@Override
		public float generateInitialGrowth(ResourceContainer container) { return (float) (Math.random()*0.04+0.03); }

	}
}
