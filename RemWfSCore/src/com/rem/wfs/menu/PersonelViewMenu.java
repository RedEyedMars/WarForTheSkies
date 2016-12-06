package com.rem.wfs.menu;

import com.rem.core.Hub;
import com.rem.core.gui.graphics.GraphicElement;
import com.rem.core.gui.graphics.GraphicEntity;
import com.rem.core.gui.graphics.GraphicText;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.wfs.Game;
import com.rem.wfs.environment.resource.personel.Personel;
import com.rem.wfs.environment.resource.personel.PortraitIcon;
import com.rem.wfs.graphics.Background;
import com.rem.wfs.graphics.R;

public class PersonelViewMenu extends GraphicEntity{
	private GraphicEntity previousMenu;
	private Background background;
	private PortraitIcon icon;
	private GraphicText nameLabel;
	private GraphicText firstName;
	private GraphicText lastName;
	private GraphicEntity close;
	public PersonelViewMenu(Personel personToView, GraphicEntity previousView){		
		super();
		this.previousMenu = previousView;
		final PersonelViewMenu self = this;
		background = new Background(R.background_1,Hub.MID_LAYER);
		background.resize(0.7f, 0.7f);
		addChild(background);
		icon = personToView.getPortaitIcon(0);
		icon.setLayer(Hub.MID_LAYER);
		icon.resize(0.16f, 0.16f);
		addChild(icon);
		
		nameLabel = new GraphicText(R.impact,"First Name:\n\nLast Name:",Hub.MID_LAYER);
		addChild(nameLabel);
		
		firstName = new GraphicText(R.impact,personToView.getName().getFirstName(),Hub.MID_LAYER);
		addChild(firstName);
		lastName = new GraphicText(R.impact,personToView.getName().getLastName(),Hub.MID_LAYER);
		addChild(lastName);
		
		close = new GraphicEntity(new GraphicElement(R.faces_traits,31,0f,0f,0.05f,0.05f,Hub.MID_LAYER)){
			@Override
			public void performOnRelease(ClickEvent event){
				((Game)Hub.view).removeOverlayMenu(self);
				((Game)Hub.view).addOverlayMenu(previousMenu);
			}
		};
		addChild(close);
		reposition(0.15f,0.15f);
	}

	@Override
	public float offsetX(int index){
		if(getChild(index)==icon){
			return 0.1f;
		}
		else if(getChild(index)==nameLabel){
			return 0.1f+icon.getWidth()+0.02f;
		}
		else if(getChild(index)==firstName||getChild(index)==lastName){
			return 0.1f+icon.getWidth()+0.2f;
		}
		else if(getChild(index)==close){
			return background.getWidth()+close.getWidth()/4f;
		}
		else return super.offsetX(index);
	}
	@Override
	public float offsetY(int index){
		if(getChild(index)==icon){
			return background.getHeight()-icon.getHeight()-0.1f;
		}
		else if(getChild(index)==nameLabel||getChild(index)==firstName){
			return background.getHeight()-icon.getHeight();
		}
		else if(getChild(index)==lastName){
			return background.getHeight()-icon.getHeight()-.05f;
		}
		else if(getChild(index)==close){
			return background.getHeight()+close.getHeight()/4f;
		}
		else return super.offsetY(index);
	}
}
