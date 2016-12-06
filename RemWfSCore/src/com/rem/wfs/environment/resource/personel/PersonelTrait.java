package com.rem.wfs.environment.resource.personel;

import com.rem.core.environment.Range;
import com.rem.core.gui.graphics.GraphicElement;
import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.Storable;
import com.rem.core.storage.StorageHandler;
import com.rem.wfs.graphics.Icon;
import com.rem.wfs.graphics.R;

public class PersonelTrait implements Storable{

	private static final Range colourRange = new Range(17,23);
	private static final Range typeRange = new Range(25,30);
	
	private Integer colourId;
	private Integer typeId;

	public void onCreate(){
		this.colourId = colourRange.getRandomIndex();
		this.typeId = typeRange.getRandomIndex();
	}

	@Override
	public StorageHandler getStorageHandler(){
		return new StorageHandler(){
			@Override
			public void load(DataPresenter data) {
				colourId = data.nextInteger();
				typeId = data.nextInteger();
			}

			@Override
			public void save(DataCollector toSave) {
				toSave.collect(colourId);
				toSave.collect(typeId);
			}
		};
	}


	public Icon getIcon(int id){
		return new Icon(
				new GraphicElement(R.faces_traits,colourRange.get(colourId)),getName(typeId),id);
	}

	private static String getName(Integer typeId) {
		switch(typeId){
		case 0: return "Accurate";
		case 1: return "Compassate";
		case 2: return "Problem Solver";
		case 3: return "Mobile";
		case 4: return "Knowledgable";
		case 5: return "Energetic";
		}
		return null;
	}

}
