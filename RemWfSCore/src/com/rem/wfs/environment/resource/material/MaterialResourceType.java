package com.rem.wfs.environment.resource.material;

import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.ResourceType;

public abstract class MaterialResourceType extends ResourceType<Material> {

	public MaterialResourceType(String name, String description, int iconTexture, int iconFrame,
			int iconBackgroundTexture, int iconBackgroundFrame) {
		super(name, description, iconTexture, iconFrame, iconBackgroundTexture, iconBackgroundFrame);
	}

	@Override
	public Material createPlaceHolder(ResourceContainer container){ 
		return new Material(container, this, 0f,0,0f);
	}
}
