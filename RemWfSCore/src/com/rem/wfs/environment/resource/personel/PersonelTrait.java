package com.rem.wfs.environment.resource.personel;

import java.io.IOException;

import com.rem.core.environment.Range;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;
import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.Storable;
import com.rem.core.storage.StorageHandler;
import com.rem.wfs.graphics.Icon;
import com.rem.wfs.graphics.R;

public class PersonelTrait implements Storable{

	private static final Range colourDownRange = new Range(17,21);
	private static final Range colourUpRange = new Range(9,13);
	private static final Range typeRange = new Range(1,6);
	
	private Integer colourId;
	private Integer typeId;
	private Icon icon;
	private boolean selected;
	private int traitId;
	
	public PersonelTrait(int id){
		this.traitId = id;
	}

	public void onCreate(){
		this.colourId = colourUpRange.getRandomIndex();
		this.typeId = typeRange.getRandomIndex();
		
		updateIcon();
	}


	private void updateIcon() {
		if(icon==null)return;
		if(selected){
			this.icon.setFrame(colourDownRange.get(colourId));
		}
		else {
			this.icon.setFrame(colourUpRange.get(colourId));
		}
		this.icon.tree.getChild(0).setFrame(typeRange.get(typeId));
		this.icon.setDescription(getName(typeId));
	}

	@Override
	public StorageHandler getStorageHandler(){
		return new StorageHandler(){
			@Override
			public void load(DataPresenter data) throws IOException {
				colourId = data.nextInteger();
				typeId = data.nextInteger();
				
				updateIcon();
			}

			@Override
			public void save(DataCollector toSave) throws IOException {
				toSave.collect(colourId);
				toSave.collect(typeId);
			}
		};
	}


	public Icon getIcon(){
		if(icon==null){
			this.icon = new Icon(
					R.traits,0,R.MID_LAYER,"DESCRIPTION_NOT_YET_SET",traitId){
				{
					tree.addChild(new GraphicElement(R.traits,0,R.MID_LAYER));
				}
				@Override
				public void setLayer(int layer){
					this.layer = layer;
				}
				@Override
				public void setParentSelectedStatus(boolean status){
					selected = status;
					updateIcon();
					reset();
				}
				@Override
				public OffsetHandler createOffsetHandler(final GraphicElement element){
					return new OffsetHandler(){
						@Override
						public float getY(){
							return selected?-0.01f:0f;
						}
					};
				}
			};
			updateIcon();
		}
		return icon;
	}
	
	public void swap(PersonelTrait otherTrait){
		int colour = colourId;
		int type = typeId;
		this.colourId = otherTrait.colourId;
		this.typeId = otherTrait.typeId;
		otherTrait.colourId = colour;
		otherTrait.typeId = type;
		
		updateIcon();
		otherTrait.updateIcon();
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
