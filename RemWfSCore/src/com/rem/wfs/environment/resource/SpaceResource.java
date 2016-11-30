package com.rem.wfs.environment.resource;

import com.rem.core.storage.Storable;
import com.rem.core.storage.StorageHandler;
import com.rem.wfs.Icon;

public class SpaceResource implements Storable{


	private float value;
	private int limit;
	private float growth;
	private Icon icon;
	protected ResourceType resourceType;

	public SpaceResource(ResourceContainer container, int id){
		this(container,ResourceType.types.get(id));
	}
	public SpaceResource(ResourceContainer container, ResourceType type){
		this(
				type,
				type.generateInitialValue(container),
				type.generateInitialLimit(container),
				type.generateInitialGrowth(container)
				);
	}

	public SpaceResource(
			ResourceType type,
			float initialValue,
			int initialLimit,
			float initialGrowth){
		this.resourceType = type;
		this.value = initialValue;
		this.limit = initialLimit;
		this.growth = initialGrowth;

		this.icon = type.createIcon(this);
	}
	

	public Icon getIcon(){
		return icon;
	}


	public Float getValue() {
		return value;
	}
	public void setValue(Float value) {
		this.value = value;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Float getGrowth() {
		return growth;
	}
	public void setGrowth(Float growth){
		this.growth = growth;
	}

	@Override
	public StorageHandler getStorageHandler() {
		return new ResourceStorageHandler(this);
	}
	
	public ResourceType getResourceType(){
		return resourceType;
	}


}
