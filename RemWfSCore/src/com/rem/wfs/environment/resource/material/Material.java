package com.rem.wfs.environment.resource.material;

import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.SpaceResource;
import com.rem.wfs.graphics.R;

public class Material extends SpaceResource<Material> {
	private static final int TEXTURE_FRAME_METAL = 4;
	private static final int TEXTURE_FRAME_FUEL = 5;
	private static final int TEXTURE_FRAME_ENERGY_CRYSTAL = 6;

	private static final int TEXTURE_FRAME_METAL_BACKGROUND = 1;
	private static final int TEXTURE_FRAME_FUEL_BACKGROUND = 5;
	private static final int TEXTURE_FRAME_ENERGY_CRYSTAL_BACKGROUND = 9;
	
	private static final MaterialResourceType METAL = new MaterialResourceType(
			"Metal",
			"Used for Creation and Maintenaince of Ships.",
			R.resources,TEXTURE_FRAME_METAL,
			R.resource_backs,TEXTURE_FRAME_METAL_BACKGROUND){
		@Override
		public float generateInitialValue(ResourceContainer container) { return 0f; }
		@Override
		public int generateInitialLimit(ResourceContainer container) { return (int) (Math.random()*4+3); }
		@Override
		public float generateInitialGrowth(ResourceContainer container) { return (float) (Math.random()*0.04+0.03); }
	};

	private static final MaterialResourceType FUEL = new MaterialResourceType(
			"Fuel",
			"Used for Ship Travel.",
			R.resources,TEXTURE_FRAME_FUEL,
			R.resource_backs,TEXTURE_FRAME_FUEL_BACKGROUND){
		@Override
		public float generateInitialValue(ResourceContainer container)
		{ return 0f; }
		@Override
		public int generateInitialLimit(ResourceContainer container) 
		{ return (int) (Math.random()*100+100); }
		@Override
		public float generateInitialGrowth(ResourceContainer container)
		{ return (float) (Math.random()+1); }
	};
	private static final MaterialResourceType ENERGY_CRYSTAL = new MaterialResourceType(
			"Energy Crystal",
			"Required for Battle Cruisers.",
			R.resources,TEXTURE_FRAME_ENERGY_CRYSTAL,
			R.resource_backs,TEXTURE_FRAME_ENERGY_CRYSTAL_BACKGROUND){
		@Override
		public float generateInitialValue(ResourceContainer container)
		{ return 0f; }
		@Override
		public int generateInitialLimit(ResourceContainer container)
		{ return (int) (Math.random()); }
		@Override
		public float generateInitialGrowth(ResourceContainer container)
		{ return (float) (Math.random()*0.16);	}
	};


	public static final int ID_METAL = METAL.getId();
	public static final int ID_FUEL = FUEL.getId();
	public static final int ID_ENERGY_CRYSTAL = ENERGY_CRYSTAL.getId();
	
	public Material(ResourceContainer container, MaterialResourceType type) {
		super(container, type);
	}
	public Material(ResourceContainer container, MaterialResourceType type, float initialValue, int initialLimit, float initialGrowth) {
		super(container,type, initialValue, initialLimit, initialGrowth);
	}
}
