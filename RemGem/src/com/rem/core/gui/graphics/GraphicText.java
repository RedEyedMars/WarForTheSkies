package com.rem.core.gui.graphics;

import java.util.ArrayList;
import java.util.List;

import com.rem.core.Hub;

public class GraphicText extends GraphicEntity {

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
	private int layer;
	private boolean isVisible = true;
	
	private int justified = LEFT_JUSTIFIED;
	protected GraphicEntity blinker;
	public GraphicText(int font, String text, int layer) {
		super(new GraphicElement());
		//Hub.renderer.loadFont(font);
		this.font = font;
		this.text = text;
		this.layer = layer;
		this.blinker = new GraphicEntity(
					new GraphicElement(
							R.solid_colour,GraphicElement.COLOUR_YELLOW,
							0f,0.975f,0.005f,0.025f,Hub.TOP_LAYER)){
				private double since;
				@Override
				public void resize(float w, float h){
					super.resize(0.005f,0.025f);
				}
				@Override
				public void update(double time){
					since+=time;
					if(isVisible()&&since>1){
						blinker.setVisible(!blinker.isVisible());
						since-=1f;
					}
					else if(!isVisible()&&since>0.5f){
						blinker.setVisible(!blinker.isVisible());
						since-=0.5f;
					}
					if(blinker.isVisible()){
						blinker.reposition(self.getX()+self.offsetX(0), self.getY()+self.offsetY(0));
					}
				}
			};
		this.blinker.turnOff();
		addChild(blinker);
		String[] lines = text.split("\n");
		for(int i=0;i<lines.length;++i){
			TextLine line = new TextLine(lines[i]);
			this.lines.add(line);
			addChild(line);
		}
		this.resize(1f, 1f);
		this.reposition(0f,0.97f);

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
				this.addChild(line);
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

		this.resize(getWidth(), getHeight());
		this.reposition(getX(),getY());
	}

	protected TextLine getLine(int i) {
		while(i>=lines.size()){
			TextLine line = new TextLine("");
			this.lines.add(line);
			this.addChild(line);
		}
		return lines.get(i);
	}
	public void setJustified(int justified){
		this.justified = justified;
		reposition(getX(),getY());
	}
	public boolean isJustified(int justified){
		return this.justified == justified;
	}
	@Override
	public float offsetX(int index){
		if(getChild(index)==blinker){
			if(lineIndex<lines.size()&&lines.get(lineIndex).length()>0&&charIndex>1){
				int horizontalIndex = Math.min(charIndex-2, lines.get(lineIndex).length()-1);
				return	lines.get(lineIndex).chars.get(horizontalIndex).getX();
				
			}
			else if(lineIndex<lines.size()&&lines.get(lineIndex).length()>0&&charIndex==1){
				return lines.get(lineIndex).chars.get(0).getWidth()*
					   lines.get(lineIndex).chars.get(0).getWidthValue()*visualW;
			}
			return 0f;
		}
		else if(justified==LEFT_JUSTIFIED){
			return super.offsetX(index);
		}
		else if(justified==RIGHT_JUSTIFIED){
			if(getChild(index) instanceof TextLine){
				return getWidth()-((TextLine)getChild(index)).getCharWidth();
			}
		}
		else if(justified==MIDDLE_JUSTIFIED){
			if(getChild(index) instanceof TextLine){
				return getWidth()/2f-((TextLine)getChild(index)).getCharWidth()/2f;
			}
		}
		return super.offsetX(index);
	}
	@Override
	public float offsetY(int index){

		if(getChild(index)==blinker){
			return 0.005f-lineIndex*0.025f;
		}
		return 0.025f*(-index+1)*visualH;
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
		this.resize(getWidth(), getHeight());
		this.reposition(getX(),getY());
	}

	@Override
	public void setVisible(boolean visible){
		super.setVisible(visible);
		this.isVisible = visible;
	}
	
	protected class TextLine extends GraphicEntity{
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
				addChild(c);
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
					addChild(c);
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
		public float offsetX(int index){
			if(index==0){
				offset = 0;
			}
			else if(index<chars.size()){
				offset+=getChild(index-1).getWidth()*chars.get(index-1).getWidthValue()*visualW;
			}

			return offset;
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
				accumulator+=getChild(i).getWidth()*chars.get(i).getWidthValue()*visualW;
			}
			return accumulator;
		}
		public String wrap(float max) {
			float accumulator = 0f;
			int i=0;
			for(;i<text.length();++i){
				accumulator+=getChild(i).getWidth()*chars.get(i).getWidthValue()*visualW;
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
	

	private class GraphicChar extends GraphicEntity{
		private float value;

		public GraphicChar(char c) {
			super(new GraphicElement(font,c,layer));
			setValue(c);
			setVisible(isVisible);
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
		public void resize(float x, float y){
			super.resize(0.025f*visualW,0.025f*visualH);
		}
	}
	@Override
	public void setLayer(int layer){
		this.layer = layer;
		super.setLayer(layer);
	}
}
