package com.rem.wfs.environment.resource.ship;

import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.wfs.graphics.R;
import com.rem.wfs.graphics.icons.Icon;

public class DetailedShipIcon extends Icon {
	private GraphicElement faceElement;
	private SpaceShip ship;
	public DetailedShipIcon(
			SpaceShip ship,
			int textureId, int frame, 
			ShipDetails details,
			String description, int id) {
		super(textureId, frame, R.MID_LAYER, description, id);
		this.ship = ship;
		faceElement = details.generateIcon();
		tree.addChild(faceElement);
	}
	public SpaceShip getShip() {
		return ship;
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
