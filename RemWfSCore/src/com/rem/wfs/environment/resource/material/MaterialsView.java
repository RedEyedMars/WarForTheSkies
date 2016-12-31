package com.rem.wfs.environment.resource.material;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.rem.core.gui.graphics.GraphicText;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.gui.inputs.HoverEvent;
import com.rem.wfs.graphics.R;
import com.rem.wfs.graphics.icons.IconListener;
import com.rem.wfs.graphics.icons.Iconic;
import com.rem.wfs.menu.OverlayView;

public class MaterialsView extends OverlayView{

	private static final Map<Integer,Integer> textures = new HashMap<Integer,Integer>();
	private static final Map<Integer,Integer> frames = new HashMap<Integer,Integer>();
	private static void addTexture(int id, int texture, int frame){
		textures.put(id,texture);
		frames.put(id,frame);
	}
	static {
		addTexture(Material.ID_METAL,R.space_objects,24);
		addTexture(Material.ID_FUEL,R.space_objects,25);
		addTexture(Material.ID_ENERGY_CRYSTAL,R.space_objects,26);
	}
	public MaterialsView(
			String name,
			Material metal, Material fuel, Material energy){
		super(name,0.7f,0.7f);

		addResource(metal);
		addResource(fuel);
		addResource(energy);
		
		reposition(0.15f,0.15f);

	}
	private void addResource(Material mat) {
		int id = mat.getResourceType().getId();
		tree.addChild(new MaterialGraphicElement(id, mat));
	}
	@Override
	public OffsetHandler createOffsetHandler(final GraphicElement element){
		if(element instanceof MaterialGraphicElement){
			final MaterialGraphicElement mge = (MaterialGraphicElement)element;
			return new OffsetHandler(){
				@Override
				public float getX(){
					return background.dim.getWidth()/5;
				}
				@Override
				public float getY(){
					return dim.getHeight()-mge.dim.getHeight()*(mge.getId()+3);
				}
			};
		}
		else return super.createOffsetHandler(element);
	}

	private class MaterialGraphicElement extends GraphicElement {
		private int id;
		private Material material;
		private GraphicText text;
		public MaterialGraphicElement(int id, Material material) {
			super(textures.get(id),frames.get(id),R.MID_LAYER);
			this.id = id;
			this.material = material;
			this.text = new GraphicText(R.impact, getFullText(material), R.MID_LAYER);
			tree.addChild(text);
		}
		public int getId(){
			return id;
		}
		@Override
		public void update(double secondsSinceLastFrame){
			text.change(getFullText(material));
		}

		@Override
		public OffsetHandler createOffsetHandler(final GraphicElement element){
			if(element==text){
				return new OffsetHandler(){
					@Override
					public float getX(){
						return dim.getWidth()+0.05f;
					}
					@Override
					public float getY(){
						return dim.getHeight()/2;
					}
				};
			}
			else return super.createOffsetHandler(element);
		}

	}
	private static String getFullText(Material material){
		String value = material.getValue()+"000";
		String growth = material.getGrowth()+"000";
		StringBuilder builder = new StringBuilder();
		builder.append(value.substring(0,value.indexOf('.')+3));
		builder.append((material.getGrowth()>=0?"+":"-"));
		builder.append(growth.substring(0,growth.indexOf('.')+3));
		builder.append("/");
		builder.append(material.getLimit());
		return builder.toString();		
	}
	@Override
	public Iterator<Iconic> iterator() {
		return new Iterator<Iconic>(){

			@Override
			public boolean hasNext() {
				return false;
			}

			@Override
			public Iconic next() {
				return null;
			}

			@Override
			public void remove() {				
			}};
	}
	@Override
	public IconListener getIconListener(Iconic icon) {
		return null;
	}
	@Override
	public void clickNoIcon(ClickEvent event) {
		super.clickNoIcon(event);
	}
	@Override
	public void hoverNoIcon(HoverEvent event) {
	}
}
