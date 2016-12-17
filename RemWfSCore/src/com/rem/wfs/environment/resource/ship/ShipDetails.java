package com.rem.wfs.environment.resource.ship;

import com.rem.core.environment.Range;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.StorageHandler;
import com.rem.wfs.graphics.R;

public class ShipDetails implements StorageHandler {

	private static final int[] shipFrames = new int[]{8,16,24,32};
	private static final Range[] featureFrames = new Range[]{new Range(9,14), new Range(17,22), new Range(25,30), new Range(33,33)};
	private static final Range animalFrames = new Range(1,5);
	

	private int shipId = -1;
	private int featuresId = -1;
	private int animalId = -1;
	private SpaceShip spaceShip;
	
	public ShipDetails(SpaceShip spaceShip){
		this.spaceShip = spaceShip;
	}

	public void onCreate(){
		shipId = spaceShip.getSpaceShipClassificationId();
		featuresId = featureFrames[spaceShip.getSpaceShipClassificationId()].getRandomIndex();
		animalId = animalFrames.getRandomIndex();
	}
	@Override
	public void load(DataPresenter data) {
		featuresId = data.nextInteger();
		animalId = data.nextInteger();
	}

	@Override
	public void save(DataCollector toSave) {
		toSave.collect(featuresId);
		toSave.collect(animalId);
	}

	public GraphicElement generateIcon() {
		GraphicElement face = new GraphicElement(R.spaceships,shipFrames[shipId],R.MID_LAYER);
		face.tree.addChild(new GraphicElement(R.spaceships,featureFrames[shipId].get(featuresId),R.MID_LAYER));
		face.tree.addChild(new GraphicElement(R.spaceships,animalFrames.get(animalId),R.MID_LAYER));
		return face;
	}
}
