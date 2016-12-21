package com.rem.wfs.menu;

import java.util.ArrayList;
import java.util.List;

import com.rem.core.Hub;
import com.rem.core.gui.graphics.GraphicText;
import com.rem.core.gui.graphics.elements.BlankGraphicElement;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.gui.inputs.HoverEvent;
import com.rem.wfs.Game;
import com.rem.wfs.environment.resource.personel.Personel;
import com.rem.wfs.environment.resource.personel.PersonelTrait;
import com.rem.wfs.environment.resource.ship.DetailedShipIcon;
import com.rem.wfs.environment.resource.ship.SpaceShip;
import com.rem.wfs.graphics.Background;
import com.rem.wfs.graphics.Icon;
import com.rem.wfs.graphics.IconListener;
import com.rem.wfs.graphics.R;

public class SpaceShipView extends BlankGraphicElement{
	
	private List<com.rem.wfs.menu.SpaceShipView.TraitCluster.ReleaseEvent> events = 
			new ArrayList<com.rem.wfs.menu.SpaceShipView.TraitCluster.ReleaseEvent>();
	
	private GraphicElement previousMenu;
	private Background background;
	private DetailedShipIcon icon;
	private GraphicText nameLabel;
	private GraphicText name;
	private GraphicText suffix;
	private GraphicElement close;
	private SpaceShip ship;
	private PersonelListView crew;

	private List<TraitCluster> traitClusters = new ArrayList<TraitCluster>();
	private int currentHoverId = -1;

	public SpaceShipView(SpaceShip ship, GraphicElement previousView){
		super();
		events.clear();
		this.previousMenu = previousView;
		final SpaceShipView self = this;
		this.ship = ship;
		background = new Background(R.background_2,R.MID_LAYER);
		background.resize(0.8f, 0.8f);
		tree.addChild(background);
		icon = ship.getDetailedIcon(0);
		icon.setLayer(R.MID_LAYER);
		icon.resize(0.16f, 0.16f);
		tree.addChild(icon);

		nameLabel = new GraphicText(R.impact,"Name:",R.MID_LAYER);
		tree.addChild(nameLabel);

		name = new GraphicText(R.impact,ship.getName().getName(),R.MID_LAYER);
		tree.addChild(name);
		suffix = new GraphicText(R.impact,ship.getName().getSuffix(),R.MID_LAYER);
		tree.addChild(suffix);

		close = new GraphicElement(R.faces,0,R.MID_LAYER){
			@Override
			public boolean onClick(ClickEvent event){
				if(dim.isWithin(event.getX(), event.getY())){
					if(event.getAction()==ClickEvent.ACTION_UP){
						((Game)Hub.view).removeOverlayMenu(self);
						((Game)Hub.view).addOverlayMenu(previousMenu);
					}
					return super.onClick(event);
				}
				else return false;
			}
		};
		tree.addChild(close);

		crew = new PersonelListView(this.ship.getCrew(),this){

			@Override
			public void performOnHoverOn(int id, HoverEvent event) {
				super.performOnHoverOn(id, event);
				setHovering(id);
			}
		};

		List<Personel> crewList = this.ship.getCrew();
		for(int i=0;i<crewList.size();++i){
			TraitCluster cluster = new TraitCluster(crewList.get(i),i);
			traitClusters.add(cluster);
			tree.addChild(cluster);
		}


		tree.addChild(crew);

		reposition(0.1f,0.1f);
	}


	@Override
	public OffsetHandler createOffsetHandler(final GraphicElement element){
		if(element==icon){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return 0.1f;
				}
				@Override
				public float getY(){
					return background.dim.getHeight()-icon.dim.getHeight()+0.0f;
				}
			};
		}
		else if(element==nameLabel){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return 0.1f+icon.dim.getWidth()+0.02f;
				}
				@Override
				public float getY(){
					return background.dim.getHeight()-icon.dim.getHeight()+0.05f;
				}
			};
		}
		else if(element==name){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return 0.1f+icon.dim.getWidth()+0.12f;
				}
				@Override
				public float getY(){
					return background.dim.getHeight()-icon.dim.getHeight()+0.05f;
				}
			};
		}
		else if(element==suffix){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return 0.1f+icon.dim.getWidth()+0.2f;
				}
				@Override
				public float getY(){
					return background.dim.getHeight()-icon.dim.getHeight();
				}
			};
		}
		else if(element==close){
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
		else if(element==crew){
			return new OffsetHandler(){
				@Override
				public float getY(){
					return background.dim.getHeight()*0.6f;
				}
			};
		}
		else if(element instanceof TraitCluster){
			final TraitCluster cluster = (TraitCluster)element;
			return new OffsetHandler(){
				@Override
				public float getX(){
					return (cluster.id*2/5)*0.15f;
				}
				@Override
				public float getY(){
					return ((cluster.id%5)+(cluster.id%5>2?-2.5f:0))*0.15f;
				}
			}; 
		}
		else return super.createOffsetHandler(element);
	}

	private void setHovering(int id){
		if(id!=currentHoverId){
			if(currentHoverId>=0){
				traitClusters.get(currentHoverId ).select(false);
			}
			traitClusters.get(id).select(true);
			this.currentHoverId = id;
		}
	}
	
	@Override
	public void update(double secondsSinceLastFrame){
		super.update(secondsSinceLastFrame);
		while(!events.isEmpty()){
			events.remove(0).act();
		}
	}

	private class TraitCluster extends BlankGraphicElement implements IconListener {

		private List<PersonelTrait> traits = new ArrayList<PersonelTrait>(Personel.numberOfTraitsPerPerson);
		
		private int id;
		private boolean isSelected;
		public TraitCluster(Personel personel, int id) {
			super();
			this.id = id;
			//this.person = personel;
			for(int i=0;i<Personel.numberOfTraitsPerPerson;++i){
				traits.add(personel.getTraits().get(i));
			}
			for(PersonelTrait trait:traits){
				Icon icon = trait.getIcon();
				icon.setLayer(R.MID_LAYER);
				icon.tree.getChild(0).setLayer(R.TOP_LAYER);
				icon.setIconListener(this);
				tree.addChild(icon);
			}
		}
		public void select(boolean selected) {
			for(PersonelTrait trait:traits){
				trait.getIcon().setParentSelectedStatus(selected);
			}
			this.isSelected = selected;
		}
		@Override
		public OffsetHandler createOffsetHandler(final GraphicElement element){
			if(element instanceof Icon){
				final int id = ((Icon)element).getId();

				return new OffsetHandler(){
					@Override
					public float getX(){
						if(id==0){
							return element.dim.getWidth()/2f;
						}
						else {
							return (id-1)*element.dim.getWidth();
						}
					}
					@Override
					public float getY(){
						if( id ==0){
							return element.dim.getHeight()*0.75f;
						}
						else {
							return 0f;
						}
					}
				};
			}
			else return super.createOffsetHandler(element);
		}			

		@Override
		public void performOnHoverOn(int id, HoverEvent event) {
			setHovering(this.id);			
			crew.performOnHoverOn(this.id, event);
		}
		@Override
		public void performOnHoverOff(int id, HoverEvent event) {
		}
		@Override
		public void performOnClick(int id, ClickEvent event) {			
		}
		@Override
		public void performOnRelease(int id, ClickEvent event) {
			events.add(new ReleaseEvent());
		}
		
		public class ReleaseEvent {
			public void act(){
				if(isSelected){
					crew.selectIcon(id);
				}
			}
		}

	}
}
