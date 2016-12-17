package com.rem.wfs.environment.resource.cluster;

import com.rem.core.storage.handler.StorableListStorageHandler;
import com.rem.wfs.environment.hexagon.SpaceHexagon;

public class ResourceClusterListStorageHandler extends StorableListStorageHandler<ResourceCluster> {

	private SpaceHexagon container;

	public ResourceClusterListStorageHandler(SpaceHexagon container) {
		super(container.getResources(),ResourceCluster.numberOfClusters);
		this.container = container;
	}

	@Override
	public ResourceCluster createPlaceHolder() {
		return new ResourceCluster(container.getResources().size());
	}




}
