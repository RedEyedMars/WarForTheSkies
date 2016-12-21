package com.rem.wfs.menu;

import java.util.ArrayList;
import java.util.List;

import com.rem.core.Hub;
import com.rem.core.gui.graphics.elements.BlankGraphicElement;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.gui.inputs.HoverEvent;
import com.rem.wfs.Game;
import com.rem.wfs.environment.resource.personel.Personel;
import com.rem.wfs.environment.resource.personel.PortraitIcon;
import com.rem.wfs.graphics.Background;
import com.rem.wfs.graphics.IconListener;
import com.rem.wfs.graphics.R;

public class PersonelListView extends BlankGraphicElement implements IconListener {

	private static final int MOUSE_DOWN = 0;
	private static final int MOUSE_UP = 1;
	private static final int MOUSE_DRAG = 2;

	private Background background;
	private GraphicElement close;
	private List<PortraitIcon> icons = new ArrayList<PortraitIcon>();

	private int clickState = MOUSE_UP;
	private float clickX;
	private float clickY;
	private float scroll = 0f;
	private GraphicElement previousView;
	private int currentSelectedId = -1;
	public PersonelListView(List<Personel> personelList){
		this(personelList,null);
	}
	public PersonelListView(List<Personel> personelList, GraphicElement previousView){
		super();
		final PersonelListView self = this;
		this.previousView = previousView;

		background = new Background(R.background_2,R.MID_LAYER);
		background.resize(previousView!=null?0.8f:0.8f, 0.15f);
		if(previousView==null){
			tree.addChild(background);	

			close = new GraphicElement(R.faces,0,R.MID_LAYER){
				@Override
				public boolean onClick(ClickEvent event){
					if(dim.isWithin(event.getX(), event.getY())){
						if(event.getAction()==ClickEvent.ACTION_UP){
							((Game)Hub.view).removeOverlayMenu(self);
						}
						return super.onClick(event);
					}
					else return false;
				}
			};
			tree.addChild(close);
		}

		for(int i=0;i<personelList.size();++i){
			PortraitIcon icon = personelList.get(i).getPortaitIcon(i);
			icon.setIconListener(this);
			icon.setLayer(R.MID_LAYER);
			icons.add(icon);
			tree.addChild(icon);
		}

		this.reposition(0.1f,0.4f);
	}
	@Override
	public OffsetHandler createOffsetHandler(final GraphicElement element){
		if(element instanceof PortraitIcon){
			final PortraitIcon icon = (PortraitIcon)element;
			return new OffsetHandler(){
				@Override
				public float getX(){
					return icon.getId()*icon.dim.getWidth()+0.003f+scroll;
				}
				@Override
				public float getY(){
					return background.dim.getHeight()/2f-element.dim.getHeight()/2f;
				}
			};
		}
		else if(element == close){
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
		else if(element == background){
			return new OffsetHandler(){
				@Override
				public float getWidth(float w){
					return previousView!=null?0.8f:w;
				}
			};
		}
		else return super.createOffsetHandler(element);
	}

	@Override
	public void reposition(float x, float y){
		super.reposition(x, y);
		for(int i=0;i<icons.size();++i){
			icons.get(i).setVisible(
					icons.get(i).dim.getX()>=x&&
					icons.get(i).dim.getX()+icons.get(i).dim.getWidth()<=x+background.dim.getWidth()+0.005);			
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
				if(scroll>(background.dim.getWidth()-icons.get(id).dim.getWidth()))
					scroll = background.dim.getWidth()-icons.get(id).dim.getWidth();
				else if(scroll<-icons.get(id).dim.getWidth()*(icons.size()-1)){
					scroll = -icons.get(id).dim.getWidth()*(icons.size()-1);
				}
				reposition(dim.getX(),dim.getY());		
				clickX = event.getX();
				clickY = event.getY();
			}
		}
	}

	@Override
	public void performOnRelease(int id, ClickEvent event) {
		if(clickState == MOUSE_DOWN){
			selectIcon(id);
		}
		clickState = MOUSE_UP;
	}
	@Override
	public void performOnHoverOn(int id, HoverEvent event){
		if(id != currentSelectedId){
			if(currentSelectedId!=-1){
				this.icons.get(currentSelectedId).setParentSelectedStatus(false);
			}
			this.icons.get(id).setParentSelectedStatus(true);
			currentSelectedId = id;
		}
	}
	@Override
	public void performOnHoverOff(int id, HoverEvent event) {
		
	}
	
	public void selectIcon(int id){
		((Game)Hub.view).removeOverlayMenu(previousView!=null?previousView:this);
		((Game)Hub.view).addOverlayMenu(
				new PersonelView(icons.get(id).getPersonel(),
								previousView!=null?previousView:this));
	}
}
