package com.rem.wfs.environment.resource;

import com.rem.core.Hub;
import com.rem.core.gui.graphics.GraphicEntity;
import com.rem.core.gui.graphics.GraphicText;
import com.rem.core.gui.inputs.HoverEvent;
import com.rem.wfs.environment.resource.ResourceType;
import com.rem.wfs.environment.resource.SpaceResource;
import com.rem.wfs.graphics.Icon;
import com.rem.wfs.graphics.R;

public class ResourceIcon <T extends SpaceResource<T>>extends Icon {

	private static final int SHOW_VALUE_STATE = 0;
	private static final int SHOW_VALUE_AND_LIMIT_STATE = 1;
	private static final int SHOW_FULL_STATE = 2;

	public static final int LEFT_JUSTIFIED = 0;
	public static final int RIGHT_JUSTIFIED = 1;
	
	public static final float REGULAR_SIZE = 1f;
	public static final float LARGE_SIZE = 1.15f;

	private GraphicEntity icon;
	private GraphicText text;
	private int state = SHOW_VALUE_STATE;
	private T resource;
	private int justified;
	private float iconSizeFactor = REGULAR_SIZE;

	private static final int FREE_STATE = 0;
	private static final int HIDE_STATE = 1;
	private static int overallState = FREE_STATE;

	public ResourceIcon(T resource, ResourceType<T> type, int justified) {
		super(type.getIconBackgroundElement(), type.getDescription(), type.getId());
		this.resource = resource;
		this.justified = justified;
		icon = new GraphicEntity(type.getIconElement());
		text = new GraphicText(R.arial,
				getStateText(),Hub.MID_LAYER);
		text.setFontSize(GraphicText.FONT_SIZE_SMALL);
		addChild(icon);
		addChild(text);
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
		resize(getHeight(),getHeight());
		return returnValue;
	}
	private String getStateText() {
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
	public void resize(float x, float y){

		if(justified==LEFT_JUSTIFIED){
			int stateTextLength = getStateText().length();
			if(stateTextLength<=1)stateTextLength=2;
			super.resize(x+stateTextLength*x/2.85f, y);
			icon.resize(y*iconSizeFactor,y*iconSizeFactor);
			text.resize(stateTextLength*x/3f,y);
			reposition(getX(),getY());
		}
		else if(justified==RIGHT_JUSTIFIED){
			int stateTextLength = getStateText().length();
			if(stateTextLength<=1)stateTextLength=2;
			float previousWidth = getWidth();
			super.resize(x+stateTextLength*x/2.85f, y);
			icon.resize(y*iconSizeFactor,y*iconSizeFactor);
			text.resize(stateTextLength*x/3f,y);
			reposition(getX()-(getWidth()-previousWidth),getY());
		}
	}
	@Override
	public float offsetX(int index){
		if(justified==LEFT_JUSTIFIED){
			if(getChild(index)==icon){
				return 0.003f;
			}
			else if(getChild(index)==text){
				return icon.getWidth();
			}
			else return super.offsetX(index);
		}
		else if(justified==RIGHT_JUSTIFIED){
			if(getChild(index)==icon){
				return text.getWidth();
			}
			else if(getChild(index)==text){
				return 0.003f;
			}
			else return super.offsetX(index);
		}
		else return super.offsetX(index);
	}
	@Override
	public float offsetY(int index){
		if(getChild(index)==text){
			return 0.003f;
		}
		else return super.offsetY(index);
	}
	
	public void setSizeFactor(float factor){
		this.iconSizeFactor = factor;
	}
	public String getFullTextValue(){
		return (resource.getValue()+"000").substring(0,(""+resource.getValue()).indexOf('.')+4)+" /"+resource.getLimit();
	}
	public String getShortTextValue(){
		return (""+resource.getValue()).substring(0,(""+resource.getValue()).indexOf('.'))+" /"+resource.getLimit();
	}
	public String getJustValue(){
		return (""+resource.getValue()).substring(0,(""+resource.getValue()).indexOf('.'));
	}
}
