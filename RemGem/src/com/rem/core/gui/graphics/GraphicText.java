package com.rem.core.gui.graphics;

import java.util.ArrayList;
import java.util.List;

import com.rem.core.Hub;
import com.rem.core.gui.graphics.elements.StretchableGraphicElement;
import com.rem.core.gui.graphics.elements.BlankGraphicElement;
import com.rem.core.gui.graphics.elements.GraphicBundle;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;

public class GraphicText extends BlankGraphicElement{

	public static final int LEFT_JUSTIFIED = 0;
	public static final int MIDDLE_JUSTIFIED = 1;
	public static final int RIGHT_JUSTIFIED = 2;

	public static final int FONT_SIZE_LARGE = 0;
	public static final int FONT_SIZE_REGULAR = 1;
	public static final int FONT_SIZE_TALLER = 2;
	public static final int FONT_SIZE_SMALL = 3;	

	private static final float SMALL_FONT_WIDTH = 0.7f;
	private static final float SMALL_FONT_HEIGHT = 0.8f;

	private static final float REGULAR_FONT_WIDTH = 1f;
	private static final float REGULAR_FONT_HEIGHT = 1f;	

	private static final float TALLER_FONT_WIDTH = 1f;
	private static final float TALLER_FONT_HEIGHT = 1.4f;

	private static final float LARGE_FONT_WIDTH = 1.4f;
	private static final float LARGE_FONT_HEIGHT = 3.2f;

	private float visualW=1f;
	private float visualH=1f;

	protected String text;
	protected List<TextLine> lines = new ArrayList<TextLine>();

	protected int charIndex=0;
	protected int lineIndex = 0;
	private GraphicText self = this;
	private int font;
	private int textLayer;
	private boolean textVisible = true;

	private int justified = LEFT_JUSTIFIED;
	protected GraphicElement blinker;
	public GraphicText(int font, String text, int layer) {
		super();
		//Hub.renderer.loadFont(font);
		this.font = font;
		this.text = text;
		this.textLayer = layer;
		this.blinker = new GraphicElement(
				R.solid_colour,R.COLOUR_YELLOW,R.TOP_LAYER,
				0f,0.975f,0.005f,0.025f){
			@Override
			protected AnimationHandler createAnimationHandler(){
				return new AnimationHandler(){
					@Override
					public void animate(GraphicElement element) {
						element.setVisible(!element.isVisible());
						if(blinker.isVisible()){
							for(GraphicBundle bundle:tree.getBundles()){
								if(bundle.element==blinker){
									blinker.reposition(
											self.dim.getX()+bundle.offset.getX(),
											self.dim.getY()+bundle.offset.getY());
									break;
								}
							}
						}

					}

				};
			}};
			this.blinker.turnOff();
			tree.addChild(blinker);
			String[] lines = text.split("\n");
			for(int i=0;i<lines.length;++i){
				TextLine line = new TextLine(lines[i]);
				this.lines.add(line);
				tree.addChild(line);
			}
			resize(1f, 1f);
			reposition(0f,0.97f);

	}

	public String getText(){
		return text;
	}

	public void change(String text){
		if(text==null)text="";
		this.text = text;
		String[] lines = text.split("\n");
		int size = this.lines.size();
		if(size<lines.length){
			for(int i=size;i<lines.length;++i){
				TextLine line = new TextLine(lines[i]);
				this.lines.add(line);
				tree.addChild(line);
			}
		}
		for(int i=0;i<size;++i){
			if(i<lines.length){
				this.lines.get(i).change(lines[i]);
			}
			else {
				this.lines.get(i).change("");
			}
		}

		resize(dim.getWidth(), dim.getHeight());
		reposition(dim.getX(), dim.getY());
	}

	protected TextLine getLine(int i) {
		while(i>=lines.size()){
			TextLine line = new TextLine("");
			this.lines.add(line);
			tree.addChild(line);
		}
		return lines.get(i);
	}
	public void setJustified(int justified){
		this.justified = justified;
		reposition(dim.getX(),dim.getY());
	}
	public boolean isJustified(int justified){
		return this.justified == justified;
	}

	@Override
	public OffsetHandler createOffsetHandler(final GraphicElement element){
		if(element==blinker){
			return new OffsetHandler(){
				@Override
				public float getX(){
					if(lineIndex<lines.size()&&lines.get(lineIndex).length()>0&&charIndex>1){
						int horizontalIndex = Math.min(charIndex-2, lines.get(lineIndex).length()-1);
						return	lines.get(lineIndex).chars.get(horizontalIndex).dim.getX();

					}
					else if(lineIndex<lines.size()&&lines.get(lineIndex).length()>0&&charIndex==1){
						return lines.get(lineIndex).chars.get(0).dim.getWidth()*
								lines.get(lineIndex).chars.get(0).getWidthValue()*visualW;
					}
					return super.getX();
				}
				@Override
				public float getY(){
					return 0.005f-lineIndex*0.025f;
				}
			};
		}
		else if(element instanceof TextLine){
			final int thisLinesIndex = tree.size();
			return new OffsetHandler(){
				@Override
				public float getX(){
					if(justified==LEFT_JUSTIFIED){
						return super.getX();
					}
					else if(justified==RIGHT_JUSTIFIED){
						return dim.getWidth()-((TextLine)element).getCharWidth();
					}
					else if(justified==MIDDLE_JUSTIFIED){
						return dim.getWidth()/2f-((TextLine)element).getCharWidth()/2f;
					}
					else return super.getX();
				}
				@Override
				public float getY(){
					if(justified==LEFT_JUSTIFIED){
						return 0.025f*(-thisLinesIndex+1)*visualH;
					}
					else if(justified==RIGHT_JUSTIFIED){
						return 0.025f*(-thisLinesIndex+1)*visualH;
					}
					else if(justified==MIDDLE_JUSTIFIED){
						return 0.025f*(-thisLinesIndex+1)*visualH;
					}
					else return super.getY();
				}
				@Override
				public String toString(){
					return "TextLine.OffsetHandler()";
				}
			};
		}
		return super.createOffsetHandler(element);
	}

	public void setFontSize(int fontSize){

		if(fontSize==FONT_SIZE_SMALL){
			this.visualW = GraphicText.SMALL_FONT_WIDTH;
			this.visualH = GraphicText.SMALL_FONT_HEIGHT;
		}
		else if(fontSize==FONT_SIZE_LARGE){
			this.visualW = GraphicText.LARGE_FONT_WIDTH;
			this.visualH = GraphicText.LARGE_FONT_HEIGHT;
		}
		else if(fontSize==FONT_SIZE_REGULAR){
			this.visualW = GraphicText.REGULAR_FONT_WIDTH;
			this.visualH = GraphicText.REGULAR_FONT_HEIGHT;
		}
		else if(fontSize==FONT_SIZE_TALLER){
			this.visualW = GraphicText.TALLER_FONT_WIDTH;
			this.visualH = GraphicText.TALLER_FONT_HEIGHT;
		}
		resize(dim.getWidth(), dim.getHeight());
		reposition(dim.getX(), dim.getY());
	}

	@Override
	public void setVisible(boolean visible){
		super.setVisible(visible);
		this.textVisible = visible;
	}

	protected class TextLine extends BlankGraphicElement{
		private String text;
		private float offset = 0f;
		private List<GraphicChar> chars = new ArrayList<GraphicChar>();
		private int length;
		public TextLine(String text) {
			super();
			this.text = text;
			this.length = this.text.length();
			char[] chars = text.toCharArray();
			for(int i=0;i<chars.length;++i){
				GraphicChar c = new GraphicChar(chars[i]);
				this.chars.add(c);
				tree.addChild(c);
			}
		}
		public void change(String string) {
			this.text = string;
			this.length = this.text.length();
			int size = chars.size();
			if(size<string.length()){
				for(int i=size;i<string.length();++i){
					GraphicChar c = new GraphicChar(string.charAt(i));
					this.chars.add(c);	
					tree.addChild(c);
				}
			}
			for(int i=0;i<size;++i){
				if(i<string.length()){
					this.chars.get(i).change(string.charAt(i));
					this.chars.get(i).turnOn();
				}
				else {
					this.chars.get(i).turnOff();
				}
			}
		}
		@Override
		public OffsetHandler createOffsetHandler(final GraphicElement element){
			final int thisCharIndex = tree.size();
			return new OffsetHandler(){
				@Override
				public float getX(){
					if(thisCharIndex==0){
						offset = 0;
					}
					else if(thisCharIndex<chars.size()){
						offset+=tree.getChild(thisCharIndex-1).dim.getWidth()*
								chars.get(thisCharIndex-1).getWidthValue()*visualW;
					}
					return offset;
				}
				@Override
				public String toString(){
					return "GraphicChar.OffsetHandler()";
				}
			};
		}
		public int length() {
			return length;
		}
		public String getText() {
			return text;
		}
		public float getCharWidth(){
			float accumulator = 0f;
			for(int i=0;i<text.length();++i){
				accumulator+=tree.getChild(i).dim.getWidth()*
						chars.get(i).getWidthValue()*visualW;
			}
			return accumulator;
		}
		public String wrap(float max) {
			float accumulator = 0f;
			int i=0;
			for(;i<text.length();++i){
				accumulator+=tree.getChild(i).dim.getWidth()*
						chars.get(i).getWidthValue()*visualW;
				if(accumulator>=max){
					break;
				}
			}
			if(i>=text.length()-1){
				return "";
			}
			else {
				String excess = text.substring(i+1);
				change(text.substring(0, i+1));
				return excess;
			}
		}
	}


	private class GraphicChar extends StretchableGraphicElement{
		private float value;

		public GraphicChar(char c) {
			super(font,c,textLayer);
			setValue(c);
			setVisible(textVisible);
		}

		@Override
		public void resize(float x, float y){
			super.resize(0.025f*visualW,0.025f*visualH);
		}

		public float getWidthValue() {
			return this.value;
		}

		public void change(char c) {
			setFrame(c);
			setValue(c);
		}
		/*
		@Override
		public void setFrame(int frame){
			super.setFrame(frame%16);
		}*/

		private void setValue(char c){
			if(c=='\t'){
				value = 4*Hub.renderer.letterWidths.get(font).get(' ')*14/16;
			}
			else {
				value = Hub.renderer.letterWidths.get(font).get(c)*14/16;
			}
		}

		@Override
		public boolean draw(VisualBundle bundle){
			return super.draw(bundle);
		}
	}
	@Override
	public void setLayer(int layer){
		this.textLayer = layer;
		super.setLayer(layer);
	}
}
