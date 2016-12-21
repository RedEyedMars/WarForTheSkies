package com.rem.wfs.environment.resource.personel;

import com.rem.core.Hub;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.wfs.Game;
import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.ResourceIcon;
import com.rem.wfs.environment.resource.StockList;
import com.rem.wfs.environment.resource.StockType;
import com.rem.wfs.graphics.Iconic;
import com.rem.wfs.graphics.R;
import com.rem.wfs.menu.PersonelListView;

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
	public Personel createObjectPlaceHolder() {
		return new Personel();
	}
	@Override
	public Iconic createIcon(final StockList<Personel> spaceResource) {
		return new ResourceIcon<StockList<Personel>>(spaceResource,this,ResourceIcon.LEFT_JUSTIFIED){
			@Override
			public boolean onClick(ClickEvent event){

				if(dim.isWithin(event.getX(), event.getY())){
					if(event.getAction()==ClickEvent.ACTION_UP){
					((Game)Hub.view).addOverlayMenu(
							new PersonelListView(
									(StockList<Personel>)spaceResource
									));
					}
					return super.onClick(event);
				}
				else return false;
			}
		};
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
