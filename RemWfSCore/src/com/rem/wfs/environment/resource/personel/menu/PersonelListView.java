package com.rem.wfs.environment.resource.personel.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rem.core.Hub;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.gui.inputs.HoverEvent;
import com.rem.wfs.Game;
import com.rem.wfs.environment.resource.personel.Personel;
import com.rem.wfs.environment.resource.personel.PortraitIcon;
import com.rem.wfs.graphics.R;
import com.rem.wfs.graphics.icons.IconListener;
import com.rem.wfs.graphics.icons.Iconic;
import com.rem.wfs.menu.OverlayView;

public class PersonelListView extends OverlayView implements IconListener {

	private static final int MOUSE_DOWN = 0;
	private static final int MOUSE_UP = 1;
	private static final int MOUSE_DRAG = 2;

	private List<PortraitIcon> icons = new ArrayList<PortraitIcon>();

	private int clickState = MOUSE_UP;
	private float clickX;
	private float clickY;
	private int withinId = -1;
	private float scroll = 0f;
	private GraphicElement hostView;
	private PortraitIcon hoverIcon;
	public PersonelListView(
			String name,
			List<Personel> personelList){
		this(name,personelList,null);
	}
	public PersonelListView(
			String name,
			List<Personel> personelList, GraphicElement hostView){
		super(name,hostView!=null?0.8f:0.8f, 0.15f);
		this.hostView = hostView;

		if(hostView!=null){
			background.setVisible(false);
			close.setVisible(false);
			title.setVisible(false);
		}

		for(int i=0;i<personelList.size();++i){
			PortraitIcon icon = personelList.get(i).getPortaitIcon(i);
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
	public void performOnHover(int id, HoverEvent event){
		if(hoverIcon!=null){
			hoverIcon.setParentSelectedStatus(false);
		}
		hoverIcon = this.icons.get(id);
		hoverIcon.setParentSelectedStatus(true);
	}
	@Override
	public void hoverNoIcon(HoverEvent event) {
		if(hoverIcon!=null){
			hoverIcon.setParentSelectedStatus(false);
			hoverIcon = null;
		}
	}

	public void selectIcon(int id){
		((Game)Hub.view).buildOverlay(
				new PersonelView(
						"Personel",
						icons.get(id).getPersonel(),
						hostView!=null?hostView:this));
	}
	@Override
	public void performOnClick(int id, ClickEvent event) {
		if(clickState==MOUSE_UP){
			withinId = id;
			clickState = MOUSE_DOWN;
			clickX = event.getX();
			clickY = event.getY();
		}
		else if(clickState==MOUSE_DOWN||clickState==MOUSE_DRAG){
			double distanceFromStart = Math.sqrt(Math.pow(event.getX()-clickX, 2)+Math.pow(event.getY()-clickY, 2));
			if(distanceFromStart>0.01f){
				clickState = MOUSE_DRAG;
				scroll += event.getX()-clickX;
				if(scroll>(background.dim.getWidth()-icons.get(withinId).dim.getWidth()))
					scroll = background.dim.getWidth()-icons.get(withinId).dim.getWidth();
				else if(scroll<-icons.get(withinId).dim.getWidth()*(icons.size()-1)){
					scroll = -icons.get(withinId).dim.getWidth()*(icons.size()-1);
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
			selectIcon(withinId);
		}
		clickState = MOUSE_UP;
		withinId = -1;
	}
	@Override
	public void clickNoIcon(ClickEvent event){
		if(withinId!=-1){
			if(event.getAction()==ClickEvent.ACTION_DOWN){
				performOnClick(withinId,event);
			}
			else if(event.getAction()==ClickEvent.ACTION_UP){
				performOnRelease(withinId,event);
			}
		}
		else {
			super.clickNoIcon(event);
		}
	}
	@Override
	public Iterator<Iconic> iterator() {
		final Iterator<PortraitIcon> itr = icons.iterator();
		return new Iterator<Iconic>(){

			@Override
			public boolean hasNext() {
				return itr.hasNext();
			}

			@Override
			public Iconic next() {
				return itr.next();
			}

			@Override
			public void remove() {				
			}

		};
	}
	@Override
	public IconListener getIconListener(Iconic icon) {
		return this;
	}
	public Iterable<Personel> getPersonel() {
		final Iterator<PortraitIcon> itr = icons.iterator();
		return new Iterable<Personel>(){

			@Override
			public Iterator<Personel> iterator() {
				return new Iterator<Personel>(){
					@Override
					public boolean hasNext() {
						return itr.hasNext();
					}

					@Override
					public Personel next() {
						return itr.next().getPersonel();
					}

					@Override
					public void remove() {				
					}
				};
			}

		};
	}
}
