package com.rem.wfs.environment.resource.material;

import com.rem.wfs.environment.resource.ResourceContainer;
import com.rem.wfs.environment.resource.ResourceType;
import com.rem.wfs.environment.resource.SpaceResource;

public class Material extends SpaceResource {
	private static final int TEXTURE_FRAME_METAL = 24;
	private static final int TEXTURE_FRAME_FUEL = 25;
	private static final int TEXTURE_FRAME_ENERGY_CRYSTAL = 26;

	private static final int TEXTURE_FRAME_METAL_BACKGROUND = 28;
	private static final int TEXTURE_FRAME_FUEL_BACKGROUND = 29;
	private static final int TEXTURE_FRAME_ENERGY_CRYSTAL_BACKGROUND = 30;
	
	private static final ResourceType METAL = new MaterialResourceType(
			"Metal",
			"Used for Creation and Maintenaince of Ships.",
			"space_objects",TEXTURE_FRAME_METAL,
			"space_objects",TEXTURE_FRAME_METAL_BACKGROUND){
		@Override
		public float generateInitialValue(ResourceContainer container) { return 0f; }
		@Override
		public int generateInitialLimit(ResourceContainer container) { return (int) (Math.random()*4+3); }
		@Override
		public float generateInitialGrowth(ResourceContainer container) { return (float) (Math.random()*0.04+0.03); }
	};

	private static final ResourceType FUEL = new MaterialResourceType(
			"Fuel",
			"Used for Ship Travel.",
			"space_objects",TEXTURE_FRAME_FUEL,
			"space_objects",TEXTURE_FRAME_FUEL_BACKGROUND){
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
	private static final ResourceType ENERGY_CRYSTAL = new MaterialResourceType(
			"Energy Crystal",
			"Required for Battle Cruisers.",
			"space_objects",TEXTURE_FRAME_ENERGY_CRYSTAL,
			"space_objects",TEXTURE_FRAME_ENERGY_CRYSTAL_BACKGROUND){
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
	
	public Material(ResourceContainer container, int i) {
		super(container, i);
	}
	public Material(ResourceType type, float initialValue, int initialLimit, float initialGrowth) {
		super(type, initialValue, initialLimit, initialGrowth);
	}
}
