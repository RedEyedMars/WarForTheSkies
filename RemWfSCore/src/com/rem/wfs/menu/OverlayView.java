package com.rem.wfs.menu;


import com.rem.core.Hub;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.wfs.Game;
import com.rem.wfs.graphics.Background;
import com.rem.wfs.graphics.R;
import com.rem.wfs.graphics.TextBox;

public abstract class OverlayView extends GraphicIconContainer {

	protected Background background;
	protected GraphicElement close;
	protected TextBox title;
	
	public OverlayView(String name, float width, float height){
		super();
		final OverlayView self = this;	

		background = new Background(R.background_2,R.MID_LAYER);
		background.resize(width, height);
		tree.addChild(background);
		
		close = new GraphicElement(R.faces,0,R.MID_LAYER){
			@Override
			public boolean onClick(ClickEvent event){
				if(!isVisible()){
					return super.onClick(event);
				}
				if(dim.isWithin(event.getX(), event.getY())){
					if(event.getAction()==ClickEvent.ACTION_UP){
						((Game)Hub.view).collapseOverlay(self);
					}
					return super.onClick(event);
				}
				else return false;
			}
		};
		tree.addChild(close);
		
		title = new TextBox(R.background_2,R.impact,name,R.MID_LAYER);
		tree.addChild(title);
		
		close.setVisible(false);
		background.setVisible(false);
	}
	@Override
	public OffsetHandler createOffsetHandler(final GraphicElement element){
		if(element == close){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return background.dim.getWidth();
				}
				@Override
				public float getY(){
					return background.dim.getHeight();
				}
			};
		}
		else if(element == title){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return background.dim.getWidth()/2-title.dim.getWidth()/2f;
				}
				@Override
				public float getY(){
					return background.dim.getHeight();
				}
			};
		}
		else return super.createOffsetHandler(element);
	}
	


	public Background getBackground() {
		return background;
	}
	
	public void setPrimary(){
		close.setVisible(true);
		background.setVisible(true);
	}
	public void clickNoIcon(ClickEvent event){
		if(close.isWithin(event.getX(), event.getY())){
			close.onClick(event);
		}
	}
}
