package com.rem.wfs.environment.resource.personel;

import java.util.ArrayList;
import java.util.List;

import com.rem.core.storage.StorageHandler;
import com.rem.core.storage.handler.HandlerListStorageHandler;
import com.rem.core.storage.handler.StorableListStorageHandler;
import com.rem.wfs.Creatable;
import com.rem.wfs.graphics.R;

public class Personel implements Creatable {

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
	public void onCreate(){
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
				new StorableListStorageHandler<PersonelTrait>(traits, Personel.numberOfTraitsPerPerson){

					@Override
					public PersonelTrait createPlaceHolder() {
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
}
