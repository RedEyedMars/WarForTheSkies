package com.rem.wfs.graphics;

import java.util.HashMap;
import java.util.Map;

import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;
import com.rem.core.gui.graphics.elements.StaticHeightGraphicElement;
import com.rem.core.gui.graphics.elements.StaticWidthGraphicElement;
import com.rem.core.gui.graphics.elements.StretchableGraphicElement;
import com.rem.core.gui.graphics.elements.dimension.DimensionHandler;

public class Background extends StretchableGraphicElement{
	private final static Map<Integer, Integer> colourMap = new HashMap<Integer,Integer>();
	static {
		colourMap.put(R.background_2, R.COLOUR_WHITE);
	}
	private int backgroundTexture;
	private boolean isSelected = false;
	public Background(int textureId, int layer){
		super(R.solid_colour,colourMap.get(textureId),layer);
		this.backgroundTexture = textureId;
		tree.addChild(new GraphicElement(textureId,0,layer));
		tree.addChild(new StaticHeightGraphicElement(textureId,1,layer));
		tree.addChild(new GraphicElement(textureId,2,layer));
		tree.addChild(new StaticWidthGraphicElement(textureId,3,layer));
		tree.addChild(new StaticWidthGraphicElement(textureId,7,layer));
		tree.addChild(new GraphicElement(textureId,4,layer));
		tree.addChild(new StaticHeightGraphicElement(textureId,5,layer));
		tree.addChild(new GraphicElement(textureId,6,layer));		
	}

	@Override
	public void resize(float w, float h){
		super.resize(w, h);
		reposition(dim.getX(),dim.getY());
	}

	@Override
	public OffsetHandler createOffsetHandler(final GraphicElement element){
		return new BackgroundOffsetHandler();
	}
	protected class BackgroundOffsetHandler extends OffsetHandler{
		final int index = tree.size();
		@Override
		public float getX(){
			if(index==0||index==3||index==5){ //East border and corners.
				return -DimensionHandler.getDefaultWidth(backgroundTexture);
			}
			else if(index==1||index==6){//North and South borders
				return 0f;
			}
			else if(index==2||index==4||index==7){//West border and corners.
				return dim.getWidth();
			}
			else return super.getX();
		}
		@Override
		public float getY(){
			if(index==0||index==1||index==2){//north border and corners
				return dim.getHeight();
			}
			else if(index==3||index==4){//East and west borders
				return 0f;
			}
			else if(index==5||index==6||index==7){//south border and corners;
				return -DimensionHandler.getDefaultHeight(backgroundTexture);
			}
			else return super.getY();
		}
	}
	
	public void setSelected(boolean selected){
		if(this.isSelected==selected) return;
		else {
			this.isSelected = selected;
			if(isSelected){
				for(int i=0;i<8;++i){
					tree.getChild(i).setFrame(tree.getChild(i).getFrame()+8);
				}
			}
			else {
				for(int i=0;i<8;++i){
					tree.getChild(i).setFrame(tree.getChild(i).getFrame()-8);
				}
			}
		}
	}
}
