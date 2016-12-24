package com.rem.wfs.graphics;

import com.rem.core.gui.graphics.GraphicText;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;

public class TextBox extends Background{

	private GraphicText text;

	public TextBox(int backgroundTextureId, int fontTextureId, String initialText, int layer) {
		super(backgroundTextureId, layer);
		text = new GraphicText(fontTextureId,initialText,layer);
		tree.addChild(text);
		resize(0.1f,0.1f);
	}

	@Override
	public OffsetHandler createOffsetHandler(final GraphicElement element){
		if(element==text){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return 0.003f;
				}
				@Override
				public float getY(){
					return 0.003f;
				}
			};
		}
		else return super.createOffsetHandler(element);
	}
	@Override
	public void resize(float w, float h){
		if(text!=null){
			super.resize(text.dim.getWidth(), text.dim.getHeight());
		}
		else {
			//super.resize(w, h);
		}
	}
}
