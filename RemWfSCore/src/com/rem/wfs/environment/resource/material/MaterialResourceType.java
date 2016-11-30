package com.rem.wfs.environment.resource.material;

import com.rem.wfs.Icon;
import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.ResourceIcon;
import com.rem.wfs.environment.resource.ResourceType;
import com.rem.wfs.environment.resource.SpaceResource;

public abstract class MaterialResourceType extends ResourceType {

	public MaterialResourceType(String name, String description, String iconTexture, int iconFrame,
			String iconBackgroundTexture, int iconBackgroundFrame) {
		super(name, description, iconTexture, iconFrame, iconBackgroundTexture, iconBackgroundFrame);
	}

	@Override
	public Icon createIcon(final SpaceResource resource){
		return new ResourceIcon(resource,this,ResourceIcon.LEFT_JUSTIFIED);
	}

	@Override
	public SpaceResource createPlaceHolder(ResourceContainer container){ 
		return new Material(this, 0f,0,0f);
	}
}
