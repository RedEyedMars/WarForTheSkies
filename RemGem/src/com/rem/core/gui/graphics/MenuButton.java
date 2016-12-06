package com.rem.core.gui.graphics;

import com.rem.core.Hub;
import com.rem.core.gui.graphics.GraphicEntity;
import com.rem.core.gui.graphics.GraphicText;
import com.rem.core.gui.inputs.ClickEvent;

public class MenuButton extends GraphicEntity {
	private MenuButton self = this;
	protected GraphicText text;
	protected GraphicEntity left;
	protected GraphicEntity mid;
	protected GraphicEntity right;
	public MenuButton(String name) {
		super(new GraphicElement());
		left = new GraphicEntity(
				new GraphicElement(R.speech_bubble,0,
						0.2f,0f,
						0.1f,0.15f,Hub.MID_LAYER)){
			@Override
			public void performOnClick(ClickEvent e){
				self.performOnClick(e);
			}
		};
		addChild(left);
		mid = new GraphicEntity(
				new GraphicElement(
						R.speech_bubble,1,
						0.3f,0f,
						0.4f,0.15f,Hub.MID_LAYER)){
			@Override
			public void performOnClick(ClickEvent e){
				self.performOnClick(e);
			}
		};
		addChild(mid);
		right = new GraphicEntity(
				new GraphicElement(R.speech_bubble,2,
						0.7f,0f,
						0.1f,0.15f,Hub.MID_LAYER)){
			@Override
			public void performOnClick(ClickEvent e){
				self.performOnClick(e);
			}
		};
		addChild(right);
		text = new GraphicText(R.impact,name,Hub.MID_LAYER){
			@Override
			public void reposition(float x, float y){
				super.reposition(x, y);
			}
		};
		text.setJustified(GraphicText.MIDDLE_JUSTIFIED);
		text.setFontSize(GraphicText.FONT_SIZE_LARGE);
		addChild(text);
		resize(0.6f,0.15f);
	}
	public float offsetY(int index){
		return index==3?0.02f:0f;
	}
	public float offsetX(int index){
		if(getChild(index) instanceof GraphicText){
			GraphicText t = (GraphicText)getChild(index);
			if(t.isJustified(GraphicText.MIDDLE_JUSTIFIED)){
				return 0.0f;
			}
			else if(t.isJustified(GraphicText.LEFT_JUSTIFIED)){
				return 0.05f;
			}
			else if(t.isJustified(GraphicText.RIGHT_JUSTIFIED)){
				return -0.05f;
			}
		}
		return index==2?getChild(0).getWidth()+getChild(1).getWidth():
			   index==1?getChild(0).getWidth():0f;
	}
	@Override
	public void resize(float x, float y){
		super.resize(x, y);
		left.resize(x*0.1f/0.6f, y);
		mid.resize(x*0.4f/0.6f, y);
		right.resize(x*0.1f/0.6f, y);
		text.resize(x, y);
	}
	public String getText() {
		return text.getText();
	}
	public void changeText(String name) {
		text.change(name);
		resize(getWidth(),getHeight());
		reposition(getX(),getY());
	}
}

