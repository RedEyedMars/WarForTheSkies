package com.rem.wfs.environment.resource;

import com.rem.core.gui.graphics.GraphicText;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;
import com.rem.core.gui.inputs.HoverEvent;
import com.rem.wfs.environment.resource.ResourceType;
import com.rem.wfs.environment.resource.SpaceResource;
import com.rem.wfs.graphics.R;
import com.rem.wfs.graphics.icons.IconLineBackground;

public class ResourceIcon <T extends SpaceResource<T>> extends IconLineBackground {

	private static final int SHOW_VALUE_STATE = 0;
	private static final int SHOW_VALUE_AND_LIMIT_STATE = 1;
	private static final int SHOW_FULL_STATE = 2;

	public static final int LEFT_JUSTIFIED = 0;
	public static final int RIGHT_JUSTIFIED = 1;

	private GraphicElement icon;
	private GraphicText text;
	private int state = SHOW_VALUE_STATE;
	private T resource;
	private int justified;

	private static final int FREE_STATE = 0;
	private static final int HIDE_STATE = 1;
	private static int overallState = FREE_STATE;

	public ResourceIcon(T resource, ResourceType<T> type, int justified) {
		super(type.getIconBackground()[0],
				type.getIconBackground()[1],
				type.getIconBackground()[2], type.getId());
		this.resource = resource;
		this.justified = justified;
		icon = type.getIconElement();
		text = new GraphicText(R.arial,
				getStateText(),R.MID_LAYER);
		text.setFontSize(GraphicText.FONT_SIZE_SMALL);
		tree.addChild(icon);
		tree.addChild(text);
	}
	@Override
	public boolean onHover(HoverEvent event){
		boolean returnValue = false;
		if(isWithin(event.getX(),event.getY())){
			state = SHOW_FULL_STATE;
			overallState = HIDE_STATE;
			returnValue = true;
		}
		else {

			if(state==SHOW_FULL_STATE){
				overallState = FREE_STATE;
			}
			if(parentSelected){
				state = SHOW_VALUE_AND_LIMIT_STATE;
			}
			else {
				state = SHOW_VALUE_STATE;
			}
		}

		text.change(getStateText());
		resize(dim.getHeight(),dim.getHeight());
		return returnValue;
	}
	private String getStateText() {
		if(resource==null)return "";
		if(overallState==FREE_STATE){
			switch(state){
			case SHOW_VALUE_STATE:return getJustValue();
			case SHOW_VALUE_AND_LIMIT_STATE:return getShortTextValue();
			case SHOW_FULL_STATE:return getFullTextValue();
			}
		}
		else if(overallState==HIDE_STATE){
			switch(state){
			case SHOW_VALUE_STATE:return getJustValue();
			case SHOW_VALUE_AND_LIMIT_STATE:return getJustValue();
			case SHOW_FULL_STATE:return getFullTextValue();
			}	
		}
		return null;
	}
	@Override
	public void resize(float x, float y){
		float previousWidth = dim.getWidth();
		int stateTextLength = getStateText().length();
		if(stateTextLength<=1)stateTextLength=2;
		super.resize(0.02f+(float) (stateTextLength*x*0.25f), y);
		if(text!=null){
			text.resize(stateTextLength*x*0.25f,y);
		}
		if(icon!=null){
			icon.resize(0.022f, y);
		}
		if(justified==LEFT_JUSTIFIED){

			reposition(dim.getX(),dim.getY());
		}
		else if(justified==RIGHT_JUSTIFIED){
			reposition(dim.getX()-(dim.getWidth()-previousWidth),dim.getY());
		}
	}
	@Override
	public OffsetHandler createOffsetHandler(final GraphicElement element){
		if(element==icon){
			return new OffsetHandler(){
				@Override
				public float getX(){
					if(justified==LEFT_JUSTIFIED){
						return -0.003f;
					}
					else if(justified==RIGHT_JUSTIFIED){
						return text.dim.getWidth()-0.003f;
					}
					else return super.getX();

				}
			};
		}
		else if(element==text){
			return new OffsetHandler(){
				@Override
				public float getX(){

					if(justified==LEFT_JUSTIFIED){
						return icon.dim.getWidth();
					}
					else if(justified==RIGHT_JUSTIFIED){
						return -0.003f;
					}
					else return super.getX();
				}
				@Override
				public float getY(){
					return 0.003f;
				}
			};
		}
		return super.createOffsetHandler(element);
	}

	public String getFullTextValue(){
		return (resource.getValue()+"000").substring(0,(""+resource.getValue()).indexOf('.')+2)+" /"+resource.getLimit();
	}
	public String getShortTextValue(){
		return (""+resource.getValue()).substring(0,(""+resource.getValue()).indexOf('.'))+" /"+resource.getLimit();
	}
	public String getJustValue(){
		return (""+resource.getValue()).substring(0,(""+resource.getValue()).indexOf('.'));
	}
}
