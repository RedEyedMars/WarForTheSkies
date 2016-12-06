package com.rem.wfs.menu;

import java.util.ArrayList;
import java.util.List;

import com.rem.core.Hub;
import com.rem.core.gui.graphics.GraphicElement;
import com.rem.core.gui.graphics.GraphicEntity;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.wfs.Game;
import com.rem.wfs.environment.resource.personel.Personel;
import com.rem.wfs.environment.resource.personel.PortraitIcon;
import com.rem.wfs.graphics.Background;
import com.rem.wfs.graphics.IconListener;
import com.rem.wfs.graphics.R;

public class PersonelListViewMenu extends GraphicEntity implements IconListener {

	private static final int MOUSE_DOWN = 0;
	private static final int MOUSE_UP = 1;
	private static final int MOUSE_DRAG = 2;
	
	private Background background;
	private GraphicEntity close;
	private List<PortraitIcon> icons = new ArrayList<PortraitIcon>();
	
	private int clickState = MOUSE_UP;
	private float clickX;
	private float clickY;
	private float scroll = 0f; 
	public PersonelListViewMenu(List<Personel> personelList){
		super();
		final PersonelListViewMenu self = this;

		background = new Background(R.background_1,Hub.MID_LAYER);
		background.resize(0.8f, 0.2f);
		addChild(background);	
		
		for(int i=0;i<personelList.size();++i){
			PortraitIcon icon = personelList.get(i).getPortaitIcon(i);
			icon.setIconListener(this);
			icon.resize(0.16f, 0.16f);
			icon.setLayer(Hub.MID_LAYER);
			icons.add(icon);
			addChild(icon);
		}

		
		close = new GraphicEntity(new GraphicElement(R.faces_traits,31,0f,0f,0.05f,0.05f,Hub.MID_LAYER)){
			@Override
			public void performOnRelease(ClickEvent event){
				((Game)Hub.view).removeOverlayMenu(self);
			}
		};
		addChild(close);
		this.reposition(0.1f,0.4f);
	}
	
	@Override
	public float offsetX(int index){
		if(getChild(index) instanceof PortraitIcon){
			return icons.indexOf(getChild(index))*getChild(index).getWidth()+0.003f+scroll;
		}
		else if(getChild(index) == close){
			return background.getWidth()-close.getWidth()/4f;
		}
		else return super.offsetX(index);
	}

	@Override
	public float offsetY(int index){
		if(getChild(index) instanceof PortraitIcon){
			return 0.02f;
		}
		else if(getChild(index) == close){
			return background.getHeight()-close.getHeight()/4f;
		}
		else return super.offsetY(index);
	}
	
	@Override
	public void reposition(float x, float y){
		super.reposition(x, y);
		for(int i=0;i<icons.size();++i){
			icons.get(i).setVisible(icons.get(i).getX()>=x&&
					icons.get(i).getX()+icons.get(i).getWidth()<=x+background.getWidth()+0.003);			
		}
	}

	@Override
	public void performOnClick(int id, ClickEvent event) {
		if(clickState==MOUSE_UP){
			clickState = MOUSE_DOWN;
			clickX = event.getX();
			clickY = event.getY();
		}
		else if(clickState==MOUSE_DOWN||clickState==MOUSE_DRAG){
			double distanceFromStart = Math.sqrt(Math.pow(event.getX()-clickX, 2)+Math.pow(event.getY()-clickY, 2));
			if(distanceFromStart>0.01f){
				clickState = MOUSE_DRAG;
				scroll += event.getX()-clickX;
				if(scroll>(background.getWidth()-icons.get(id).getWidth()))
					scroll = background.getWidth()-icons.get(id).getWidth();
				else if(scroll<-icons.get(id).getWidth()*(icons.size()-1)){
					scroll = -icons.get(id).getWidth()*(icons.size()-1);
				}
				reposition(getX(),getY());		
				clickX = event.getX();
				clickY = event.getY();
			}
		}
	}

	@Override
	public void performOnRelease(int id, ClickEvent event) {
		if(clickState == MOUSE_DOWN){
			((Game)Hub.view).removeOverlayMenu(this);
			((Game)Hub.view).addOverlayMenu(new PersonelViewMenu(icons.get(id).getPersonel(),this));
		}
		clickState = MOUSE_UP;
	}
}
