package com.rem.wfs.environment.resource;

import com.rem.core.storage.StorageHandler;
import com.rem.wfs.Creatable;
import com.rem.wfs.graphics.Icon;

public class SpaceResource<T extends SpaceResource<T>> implements Creatable{
	private float value;
	private int limit;
	private float growth;
	private Icon icon;
	protected ResourceType<T> resourceType;
	private ResourceContainer container;

	public SpaceResource(ResourceContainer container, ResourceType<T> type){
		this(
				container,
				type!=null?type:null,
				type!=null?type.generateInitialValue(container):0,
				type!=null?type.generateInitialLimit(container):0,
				type!=null?type.generateInitialGrowth(container):0
				);
	}

	@SuppressWarnings("unchecked")
	public SpaceResource(
			ResourceContainer container,
			ResourceType<T> type,
			float initialValue,
			int initialLimit,
			float initialGrowth){
		this.resourceType = type;
		this.value = initialValue;
		this.limit = initialLimit;
		this.growth = initialGrowth;

		this.container = container;
		if(type!=null){
			this.icon = type.createIcon((T) this);
		}
	}

	public void onCreate() {
		this.value = resourceType.generateInitialValue(container);
		this.limit = resourceType.generateInitialLimit(container);
		this.growth= resourceType.generateInitialGrowth(container);
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
		return new ResourceStorageHandler<T>(this);
	}
	
	public ResourceType<T> getResourceType(){
		return resourceType;
	}
	public ResourceContainer getContainer() {
		return this.container;
	}



}
