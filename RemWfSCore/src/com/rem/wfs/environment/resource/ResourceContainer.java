package com.rem.wfs.environment.resource;

import java.util.List;


@SuppressWarnings("rawtypes")
public interface ResourceContainer {

	public static final int MATERIAL_ID = 0;
	public static final int PERSONEL_ID = 1;
	public static final int SPACESHIP_ID = 2;
	public List<SpaceResource> getResources();
	public SpaceResource getResource(int catagory, int id);

}
