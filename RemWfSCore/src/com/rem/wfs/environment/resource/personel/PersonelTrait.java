package com.rem.wfs.environment.resource.personel;

import com.rem.core.environment.Range;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.Storable;
import com.rem.core.storage.StorageHandler;
import com.rem.wfs.graphics.Icon;
import com.rem.wfs.graphics.R;

public class PersonelTrait implements Storable{

	private static final Range colourRange = new Range(25,29);
	private static final Range typeRange = new Range(17,22);
	
	private Integer colourId;
	private Integer typeId;
	private Icon icon;

	public void onCreate(int id){
		this.colourId = colourRange.getRandomIndex();
		this.typeId = typeRange.getRandomIndex();
		
		this.icon = new Icon(
				R.faces_traits,colourRange.get(colourId),R.MID_LAYER,getName(typeId),id){
			{
				tree.addChild(new GraphicElement(R.faces_traits,typeRange.get(typeId),R.MID_LAYER));
			}
		};
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


	public Icon getIcon(){
		return icon;
	}
	
	public void swap(PersonelTrait otherTrait){
		int colour = colourId;
		int type = typeId;
		this.colourId = otherTrait.colourId;
		this.typeId = otherTrait.typeId;
		otherTrait.colourId = colour;
		otherTrait.typeId = type;
		
		icon.setFrame(colourRange.get(colourId));
		icon.tree.getChild(0).setFrame(typeRange.get(typeId));
		otherTrait.icon.setFrame(colourRange.get(otherTrait.colourId));
		otherTrait.icon.tree.getChild(0).setFrame(typeRange.get(otherTrait.typeId));
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
