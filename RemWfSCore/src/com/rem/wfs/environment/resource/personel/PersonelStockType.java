package com.rem.wfs.environment.resource.personel;

import com.rem.core.Hub;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.wfs.Game;
import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.ResourceIcon;
import com.rem.wfs.environment.resource.StockList;
import com.rem.wfs.environment.resource.StockType;
import com.rem.wfs.graphics.Icon;
import com.rem.wfs.graphics.R;
import com.rem.wfs.menu.PersonelListViewMenu;

public class PersonelStockType extends StockType<Personel> {

	private static final int TEXTURE_FRAME_POPULATION = 27;	
	private static final int TEXTURE_FRAME_POPULATION_BACKGROUND = 31;
	
	public PersonelStockType() {
		super("Personel",
				"Ships have varying Crew requirements.",
				R.space_objects,TEXTURE_FRAME_POPULATION,
				R.space_objects,TEXTURE_FRAME_POPULATION_BACKGROUND);
	}

	@Override
	public Personel createObjectPlaceHolder() {
		return new Personel();
	}
	@Override
	public Icon createIcon(final StockList<Personel> spaceResource) {
		return new ResourceIcon<StockList<Personel>>(spaceResource,this,ResourceIcon.LEFT_JUSTIFIED){
			@Override
			public void performOnRelease(ClickEvent event){
				((Game)Hub.view).addOverlayMenu(new PersonelListViewMenu(
						(StockList<Personel>)spaceResource
						));
			}
		};
	}


	@Override
	public float generateInitialValue(ResourceContainer container) { return 2f; }
	@Override
	public int generateInitialLimit(ResourceContainer container) { return (int) (Math.random()*16)+2; }
	@Override
	public float generateInitialGrowth(ResourceContainer container) { return (float) (Math.random()*0.04+0.03); }

}
