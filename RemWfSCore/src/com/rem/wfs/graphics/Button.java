package com.rem.wfs.graphics;

import java.util.HashMap;
import java.util.Map;

import com.rem.core.Action;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;
import com.rem.core.gui.graphics.elements.StaticHeightGraphicElement;
import com.rem.core.gui.graphics.elements.dimension.DimensionHandler;
import com.rem.core.gui.inputs.ClickEvent;

public class Button extends StaticHeightGraphicElement{

	private GraphicElement leftCap;
	private GraphicElement rightCap;
	private GraphicElement centerPiece;
	private Action<ClickEvent> onClick;
	private int leftCapWhenUp;
	private int middleWhenUp;
	private int rightCapWhenUp;
	private int leftCapWhenDown;
	private int middleWhenDown;
	private int rightCapWhenDown;
	private boolean hovered = false;

	public Button(
			 Setting settings,
			int layer,
			float x, float y, float w,
			Action<ClickEvent> onClick,
			GraphicElement centerPiece) {
		this(settings.texture,
				settings.leftCapWhenUp, settings.middleWhenUp, settings.rightCapWhenUp,
				settings.leftCapWhenDown, settings.middleWhenDown, settings.rightCapWhenDown,
				layer,
				x,y,w,onClick,centerPiece);
	}
	
	public Button(
			int textureId, int leftCapWhenUp, int middleWhenUp,int rightCapWhenUp,
						 int leftCapWhenDown, int middleWhenDown,int rightCapWhenDown,
						 int layer, 
			float x, float y, float w,
			Action<ClickEvent> onClick,
			GraphicElement centerPiece) {
		super(textureId, middleWhenUp, layer, x, y, w, DimensionHandler.getDefaultHeight(textureId));
		this.centerPiece = centerPiece;
		this.onClick = onClick;
		leftCap = new GraphicElement(textureId,leftCapWhenUp,layer,
									0f,0f,DimensionHandler.getDefaultWidth(textureId)/2f,DimensionHandler.getDefaultHeight(textureId));
		rightCap = new GraphicElement(textureId,rightCapWhenUp,layer,
				                    0f,0f,DimensionHandler.getDefaultWidth(textureId)/2f,DimensionHandler.getDefaultHeight(textureId));
		tree.addChild(leftCap);		
		tree.addChild(rightCap);
		tree.addChild(centerPiece);
		this.leftCapWhenUp = leftCapWhenUp;
		this.middleWhenUp = middleWhenUp;
		this.rightCapWhenUp = rightCapWhenUp;
		this.leftCapWhenDown = leftCapWhenDown;
		this.middleWhenDown = middleWhenDown;
		this.rightCapWhenDown = rightCapWhenDown;		
		
		this.reposition(dim.getX(), dim.getY());
	}
	
	@Override
	public OffsetHandler createOffsetHandler(final GraphicElement element){
		if(element==leftCap){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return -element.dim.getWidth();
				}

				@Override
				public float getWidth(float w){
					return centerPiece.dim.getWidth();
				}
				@Override
				public float getHeight(float h){
					return centerPiece.dim.getHeight();
				}
			};
		}
		else if(element == rightCap){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return dim.getWidth();
				}

				@Override
				public float getWidth(float w){
					return centerPiece.dim.getWidth();
				}
				@Override
				public float getHeight(float h){
					return centerPiece.dim.getHeight();
				}
			};
		}
		else if(element == centerPiece){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return dim.getWidth()/2-centerPiece.dim.getWidth()/2;
				}
				@Override
				public float getY(){
					if(hovered){
						return dim.getHeight()*1/2-centerPiece.dim.getHeight()/2;
					}
					else {
						return dim.getHeight()*2/3-centerPiece.dim.getHeight()/2;
					}
				}
				@Override
				public float getWidth(float w){
					return centerPiece.dim.getWidth();
				}
				@Override
				public float getHeight(float h){
					return centerPiece.dim.getHeight();
				}
			};
		}
		else return super.createOffsetHandler(element);
	}
	
	@Override
	public boolean onClick(ClickEvent event){
		if(onClick!=null&&(event.isWithin(this)||event.isWithin(leftCap)||event.isWithin(rightCap))){
			if(event.getAction() == ClickEvent.ACTION_DOWN){
				leftCap.setFrame(leftCapWhenDown);
				this.setFrame(middleWhenDown);
				rightCap.setFrame(rightCapWhenDown);
				hovered = true;
			}
			else if(event.getAction() == ClickEvent.ACTION_UP){
				leftCap.setFrame(leftCapWhenUp);
				this.setFrame(middleWhenUp);
				rightCap.setFrame(rightCapWhenUp);
				hovered = false;
				
				onClick.act(event);
			}
		}
		else {
			leftCap.setFrame(leftCapWhenUp);
			this.setFrame(middleWhenUp);
			rightCap.setFrame(rightCapWhenUp);
			hovered = false;
		}

		reposition(dim.getX(),dim.getY());
		return super.onClick(event);
	}
	
	private final static Map<Integer,Setting> settings = new HashMap<Integer,Setting>();
	static {
		new Setting(R.buttons_1,4,5,6,0,1,2);
	}
	public static class Setting {
		private int leftCapWhenUp;
		private int middleWhenUp;
		private int rightCapWhenUp;
		private int leftCapWhenDown;
		private int middleWhenDown;
		private int rightCapWhenDown;
		private int texture;

		public Setting(int texture){
			Setting other = settings.get(texture);
			this.texture = texture;
			this.leftCapWhenUp = other.leftCapWhenUp;
			this.middleWhenUp = other.middleWhenUp;
			this.rightCapWhenUp = other.rightCapWhenUp;
			this.leftCapWhenDown = other.leftCapWhenDown;
			this.middleWhenDown = other.middleWhenDown;
			this.rightCapWhenDown = other.rightCapWhenDown;
		}

		private Setting(
				int texture,
				int leftCapWhenUp, int middleWhenUp,int rightCapWhenUp,
				int leftCapWhenDown, int middleWhenDown,int rightCapWhenDown) {
			this.texture = texture;
			this.leftCapWhenUp = leftCapWhenUp;
			this.middleWhenUp = middleWhenUp;
			this.rightCapWhenUp = rightCapWhenUp;
			this.leftCapWhenDown = leftCapWhenDown;
			this.middleWhenDown = middleWhenDown;
			this.rightCapWhenDown = rightCapWhenDown;
			

			settings.put(R.buttons_1, this);
		}
	}

}
