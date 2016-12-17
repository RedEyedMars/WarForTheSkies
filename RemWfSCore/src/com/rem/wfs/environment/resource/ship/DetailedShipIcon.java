package com.rem.wfs.environment.resource.ship;

import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.wfs.graphics.Icon;
import com.rem.wfs.graphics.R;

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
}
