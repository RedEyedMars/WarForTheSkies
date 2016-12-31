package com.rem.wfs.environment.resource.cluster;

import java.util.List;

import com.rem.core.storage.handler.StorableListStorageHandler;
import com.rem.wfs.environment.hexagon.SpaceHexagon;

public class ResourceClusterListStorageHandler extends StorableListStorageHandler<ResourceCluster> {

	private SpaceHexagon container;

	@SuppressWarnings("unchecked")
	public ResourceClusterListStorageHandler(SpaceHexagon container) {
		super((List<ResourceCluster>)container.getResources(),ResourceCluster.numberOfClusters);
		this.container = container;
	}

	@Override
	public ResourceCluster createObject() {
		return new ResourceCluster(container.getResources(),container.getResources().size());
	}




}
