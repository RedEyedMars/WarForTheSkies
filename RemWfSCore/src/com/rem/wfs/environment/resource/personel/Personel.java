package com.rem.wfs.environment.resource.personel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rem.core.storage.StorageHandler;
import com.rem.core.storage.handler.HandlerListStorageHandler;
import com.rem.core.storage.handler.ObjectListStorageHandler;
import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.stock.StockElement;
import com.rem.wfs.graphics.R;

public class Personel implements StockElement {

	private static final PersonelStock PERSONEL = new PersonelStock.Standard();
	public static final int numberOfTraitsPerPerson = 3;

	public static final int ID_POPULATION = PERSONEL.getId();

	private PortraitDescription description;
	private PersonelName name;
	private List<PersonelTrait> traits = new ArrayList<PersonelTrait>(numberOfTraitsPerPerson);
	public Personel(){
		this.description = new PortraitDescription();
		this.name = new PersonelName();
	}
	public void onCreate(ResourceContainer container){
		this.description.onCreate();
		this.name.onCreate();
		for(int i=0;i<numberOfTraitsPerPerson;++i){
			PersonelTrait trait = new PersonelTrait(i);
			trait.onCreate();
			this.traits.add(trait);
		}
	}
	@Override
	public StorageHandler getStorageHandler() {
		return new HandlerListStorageHandler(
				description,
				name,
				new ObjectListStorageHandler<PersonelTrait>(traits, Personel.numberOfTraitsPerPerson){

					@Override
					public PersonelTrait createObject() throws IOException {
						return new PersonelTrait(list.size());
					}
				});
	}
	public PortraitIcon getPortaitIcon(int id) {
		return new PortraitIcon(this,
				R.single_background_2,0,
				description,name.getFullName(),id);
	}
	public PersonelName getName() {
		return name;
	}
	public List<PersonelTrait> getTraits() {
		return traits;
	}

	public void rotateTraitsClockwise(){
		traits.get(0).swap(traits.get(2));
		traits.get(0).swap(traits.get(1));
	}
	
	public void rotateTraitsCounterClockwise(){
		traits.get(0).swap(traits.get(1));
		traits.get(0).swap(traits.get(2));
	}
	@Override
	public void grow(ResourceContainer container, float seconds) {
		
	}
}
