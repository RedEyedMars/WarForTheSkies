package com.rem.wfs.environment.resource.personel;

import java.io.IOException;

import com.rem.core.environment.Range;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.storage.DataCollector;
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
	public void collect(DataCollector data) throws IOException {
		backgroundId = data.collect(backgroundId);
		featuresId = data.collect(featuresId);
		hairId = data.collect(hairId);
	}

	public GraphicElement generateFace() {
		GraphicElement face = new GraphicElement(R.faces,backgroundFrames.get(backgroundId),R.MID_LAYER);
		face.tree.addChild(new GraphicElement(R.faces,featureFrames.get(featuresId),R.MID_LAYER));
		face.tree.addChild(new GraphicElement(R.faces,hairFrames.get(hairId),R.MID_LAYER));
		return face;
	}

}
