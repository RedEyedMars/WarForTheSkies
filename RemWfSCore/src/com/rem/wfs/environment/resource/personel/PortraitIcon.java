package com.rem.wfs.environment.resource.personel;

import com.rem.core.gui.graphics.R;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.wfs.graphics.Icon;

public class PortraitIcon extends Icon {

	private GraphicElement faceElement;
	private Personel person;
	public PortraitIcon(
			Personel person,
			int textureId, int frame, PortraitDescription face, String description, int id) {
		super(textureId, frame, R.MID_LAYER, description, id);
		this.person = person;
		faceElement = face.generateFace();
		tree.addChild(faceElement);
	}
	public Personel getPersonel() {
		return person;
	}
	@Override
	public void setParentSelectedStatus(boolean status){
		if(status != this.parentSelected){
			super.setParentSelectedStatus(status);
			if(status){
				this.setFrame(this.getFrame()+1);
			}
			else {
				this.setFrame(this.getFrame()-1);
			}
		}
	}	

}
