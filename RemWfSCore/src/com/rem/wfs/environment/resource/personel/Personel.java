package com.rem.wfs.environment.resource.personel;

import java.util.ArrayList;
import java.util.List;

import com.rem.core.storage.StorageHandler;
import com.rem.core.storage.handler.HandlerListStorageHandler;
import com.rem.core.storage.handler.StorableListStorageHandler;
import com.rem.wfs.Creatable;
import com.rem.wfs.graphics.R;

public class Personel implements Creatable {

	private static final PersonelStockType PERSONEL = new PersonelStockType();
	private static final int numberOfTraitsPerPerson = 3;

	public static final int ID_POPULATION = PERSONEL.getId();

	private PortraitDescription description;
	private PersonelName name;
	private List<PersonelTrait> traits = new ArrayList<PersonelTrait>(numberOfTraitsPerPerson);
	public Personel(){
		this.description = new PortraitDescription();
		this.name = new PersonelName();
	}
	public void onCreate(){
		this.description.onCreate();
		this.name.onCreate();
		for(int i=0;i<numberOfTraitsPerPerson;++i){
			PersonelTrait trait = new PersonelTrait();
			trait.onCreate(i);
			this.traits.add(trait);
		}
	}
	@Override
	public StorageHandler getStorageHandler() {
		return new HandlerListStorageHandler(
				description,
				name,
				new StorableListStorageHandler<PersonelTrait>(traits,numberOfTraitsPerPerson){

					@Override
					public PersonelTrait createPlaceHolder() {
						return new PersonelTrait();
					}
				});
	}
	public PortraitIcon getPortaitIcon(int id) {
		return new PortraitIcon(this,
				R.background_1,15,
				description,name.getFullName(),id);
	}
	public PersonelName getName() {
		return name;
	}
	public List<PersonelTrait> getTraits() {
		return traits;
	}

}
