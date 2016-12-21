package com.rem.core.gui.graphics;

import com.rem.core.gui.graphics.GraphicText;
import com.rem.core.gui.graphics.elements.StretchableGraphicElement;
import com.rem.core.gui.graphics.elements.BlankGraphicElement;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;

public class MenuButton extends BlankGraphicElement {
	protected GraphicText text;
	protected StretchableGraphicElement left;
	protected StretchableGraphicElement mid;
	protected StretchableGraphicElement right;
	public MenuButton(String name) {
		super();
		left = new StretchableGraphicElement(
				R.speech_bubble,0,R.MID_LAYER,
						0.2f,0f,
						0.1f,0.15f);
		mid = new StretchableGraphicElement(
						R.speech_bubble,1,R.MID_LAYER,
						0.3f,0f,
						0.4f,0.15f);
		right = new StretchableGraphicElement(
				R.speech_bubble,2,R.MID_LAYER,
						0.7f,0f,
						0.1f,0.15f);
		text = new GraphicText(R.impact,name,R.MID_LAYER);
		text.setJustified(GraphicText.MIDDLE_JUSTIFIED);
		text.setFontSize(GraphicText.FONT_SIZE_LARGE);

		tree.addChild(left);
		tree.addChild(mid);
		tree.addChild(right);
		tree.addChild(text);
		resize(0.6f,0.15f);
	}
	@Override
	public OffsetHandler createOffsetHandler(final GraphicElement element){
		if(element==left){
			return new OffsetHandler(){
				@Override
				public float getWidth(float w){
					return w*0.1f/0.6f;
				}
			};
		}
		else if(element==mid){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return left.dim.getWidth();
				}
				@Override
				public float getWidth(float w){
					return w*0.4f/0.6f;
				}
			};
		}
		else if(element==right){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return left.dim.getWidth()+mid.dim.getWidth();
				}
				@Override
				public float getWidth(float w){
					return w*0.1f/0.6f;
				}
			};
		}
		else if(element==text){
			return new OffsetHandler(){
				@Override
				public float getX(){
					if(text.isJustified(GraphicText.MIDDLE_JUSTIFIED)){
						return 0.0f;
					}
					else if(text.isJustified(GraphicText.LEFT_JUSTIFIED)){
						return 0.05f;
					}
					else if(text.isJustified(GraphicText.RIGHT_JUSTIFIED)){
						return -0.05f;
					}
					else return super.getX();
				}
				@Override
				public float getY(){
					return 0.02f;
				}
			};
		}
		else return super.createOffsetHandler(element);
	}
	public String getText() {
		return text.getText();
	}
	public void changeText(String name) {
		text.change(name);
		resize(dim.getWidth(),dim.getHeight());
		reposition(dim.getX(),dim.getY());
	}
}

