package com.rem.wfs.environment.resource.personel;

import com.rem.core.environment.Range;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.StorageHandler;
import com.rem.wfs.graphics.R;

public class PortraitDescription implements StorageHandler{

	private static final Range backgroundFrames = new Range.List(new Range(1,1),new Range(9,9));
	private static final Range featureFrames = new Range(2,7);
	private static final Range hairFrames = new Range(10,15);

	private int backgroundId = -1;
	private int featuresId = -1;
	private int hairId = -1;

	public void onCreate(){
		backgroundId = backgroundFrames.getRandomIndex();
		featuresId = featureFrames.getRandomIndex();
		hairId = hairFrames.getRandomIndex();
	}
	@Override
	public void load(DataPresenter data) {
		backgroundId = data.nextInteger();
		featuresId = data.nextInteger();
		hairId = data.nextInteger();
	}

	@Override
	public void save(DataCollector toSave) {
		toSave.collect(backgroundId);
		toSave.collect(featuresId);
		toSave.collect(hairId);
	}

	public GraphicElement generateFace() {
		GraphicElement face = new GraphicElement(R.faces_traits,backgroundFrames.get(backgroundId),R.MID_LAYER);
		face.tree.addChild(new GraphicElement(R.faces_traits,featureFrames.get(featuresId),R.MID_LAYER));
		face.tree.addChild(new GraphicElement(R.faces_traits,hairFrames.get(hairId),R.MID_LAYER));
		return face;
	}

}
