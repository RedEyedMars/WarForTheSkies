package com.rem.wfs.environment.resource.material;

import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.ResourceIcon;
import com.rem.wfs.environment.resource.ResourceType;
import com.rem.wfs.graphics.Iconic;

public abstract class MaterialResourceType extends ResourceType<Material> {

	public MaterialResourceType(String name, String description, int iconTexture, int iconFrame,
			int iconBackgroundTexture, int iconBackgroundFrame) {
		super(name, description, iconTexture, iconFrame, iconBackgroundTexture, iconBackgroundFrame);
	}

	@Override
	public Iconic createIcon(final Material resource){
		return new ResourceIcon<Material>(resource,this,ResourceIcon.LEFT_JUSTIFIED);
	}

	@Override
	public Material createPlaceHolder(ResourceContainer container){ 
		return new Material(container, this, 0f,0,0f);
	}
}
