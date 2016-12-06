package com.rem.wfs.environment.resource.personel;

import com.rem.core.gui.graphics.GraphicElement;
import com.rem.core.gui.graphics.GraphicEntity;
import com.rem.wfs.graphics.Icon;

public class PortraitIcon extends Icon {

	private GraphicEntity faceElement;
	private Personel person;
	public PortraitIcon(
			Personel person,
			GraphicElement background, PortraitDescription face, String description, int id) {
		super(background, description, id);
		this.person = person;
		faceElement = face.generateFace();
		addChild(faceElement);
	}
	public Personel getPersonel() {
		return person;
	}
	

}
