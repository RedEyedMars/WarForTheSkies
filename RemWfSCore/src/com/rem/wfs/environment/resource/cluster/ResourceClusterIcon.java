package com.rem.wfs.environment.resource.cluster;

import com.rem.core.gui.graphics.elements.dimension.DimensionHandler;
import com.rem.core.gui.graphics.elements.dimension.StaticDimensionHandler;
import com.rem.wfs.graphics.Icon;

public class ResourceClusterIcon extends Icon {

	public ResourceClusterIcon(
			int textureId, int frame, int layer, 
			String description, int id) {
		super(textureId, frame, layer,
				description, id);
	}

	@Override
	public DimensionHandler createDimensionHandler(int texture, float x, float y, float w, float h){
		return new StaticDimensionHandler(texture, x, y, w, h);
	}
	
	@Override
	public void setParentSelectedStatus(boolean visible){
		super.setParentSelectedStatus(visible);
		this.setVisible(visible);
	}
}
