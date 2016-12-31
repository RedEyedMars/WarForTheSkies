package com.rem.wfs.environment.resource;

import java.util.ArrayList;
import java.util.List;

import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.R;


public abstract class ResourceType<ObjectType extends SpaceResource<ObjectType>> {

	@SuppressWarnings("rawtypes")
	public static final List<ResourceType> types = new ArrayList<ResourceType>();

	private String name;
	private int id;
	private String description;

	private int iconTexture;
	private int iconFrame;
	private int iconBackgroundTexture;
	private int iconBackgroundFrame;
	
	public ResourceType(String name,
			String description,
			int iconTexture, int iconFrame,
			int iconBackgroundTexture, int iconBackgroundFrame){

		this.name = name;
		this.id = types.size();
		this.description = description;
		this.iconTexture = iconTexture;
		this.iconFrame = iconFrame;
		this.iconBackgroundTexture = iconBackgroundTexture;
		this.iconBackgroundFrame = iconBackgroundFrame;
		types.add(this);
	}

	public String getName() {
		return name;
	}

	public int getId(){
		return id;
	}
	public String getDescription(){
		return description;
	}

	public abstract float generateInitialValue(ResourceContainer container);
	public abstract int generateInitialLimit(ResourceContainer container);
	public abstract float generateInitialGrowth(ResourceContainer container);
	public abstract SpaceResource<ObjectType> createPlaceHolder(ResourceContainer container);

	public int[] getIconBackground() {
		return new int[]{iconBackgroundTexture,iconBackgroundFrame,R.MID_LAYER};
	}
	public GraphicElement getIconElement() {
		return new GraphicElement(iconTexture,iconFrame,R.MID_LAYER);
	}

}
