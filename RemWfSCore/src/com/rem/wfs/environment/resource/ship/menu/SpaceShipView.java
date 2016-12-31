package com.rem.wfs.environment.resource.ship.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rem.core.gui.graphics.GraphicText;
import com.rem.core.gui.graphics.elements.BlankGraphicElement;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.gui.inputs.HoverEvent;
import com.rem.wfs.environment.resource.personel.Personel;
import com.rem.wfs.environment.resource.personel.PersonelTrait;
import com.rem.wfs.environment.resource.personel.PortraitIcon;
import com.rem.wfs.environment.resource.personel.menu.PersonelListView;
import com.rem.wfs.environment.resource.ship.DetailedShipIcon;
import com.rem.wfs.environment.resource.ship.SpaceShip;
import com.rem.wfs.graphics.R;
import com.rem.wfs.graphics.icons.Icon;
import com.rem.wfs.graphics.icons.IconListener;
import com.rem.wfs.graphics.icons.Iconic;
import com.rem.wfs.menu.OverlayView;

public class SpaceShipView extends OverlayView{

	private List<com.rem.wfs.environment.resource.ship.menu.SpaceShipView.TraitCluster.ReleaseEvent> events = 
			new ArrayList<com.rem.wfs.environment.resource.ship.menu.SpaceShipView.TraitCluster.ReleaseEvent>();

	private DetailedShipIcon icon;
	private GraphicText nameLabel;
	private GraphicText shipName;
	private GraphicText suffix;
	private SpaceShip ship;
	private PersonelListView crew;

	private List<TraitCluster> traitClusters = new ArrayList<TraitCluster>();
	private int currentHoverId = -1;

	private Iconic shipFuel;
	private Iconic shipHp;

	public SpaceShipView(
			String name,
			SpaceShip ship){
		super(name, 0.8f, 0.8f);
		events.clear();
		this.ship = ship;
		
		icon = ship.getDetailedIcon(0);
		icon.setLayer(R.MID_LAYER);
		icon.resize(0.16f, 0.16f);
		tree.addChild(icon);

		nameLabel = new GraphicText(R.impact,"Name:",R.MID_LAYER);
		tree.addChild(nameLabel);

		shipName = new GraphicText(R.impact,ship.getName().getName(),R.MID_LAYER);
		tree.addChild(shipName);
		suffix = new GraphicText(R.impact,ship.getName().getSuffix(),R.MID_LAYER);
		tree.addChild(suffix);		

		shipHp = ship.getHp().getMeter();
		shipHp.addToTree(tree);
		
		shipFuel = ship.getFuel().getMeter();
		shipFuel.addToTree(tree);

		crew = new PersonelListView("Crew",this.ship.getCrew(),this){
			@Override
			public void performOnRelease(int id, ClickEvent event){
				traitClusters.get(id).select(false);
				super.performOnRelease(id, event);
			}
			@Override
			public void performOnHover(int id, HoverEvent event) {
				if(currentHoverId!=-1){					
					traitClusters.get(currentHoverId).select(false);
				}
				currentHoverId=id;
				traitClusters.get(id).select(true);
				super.performOnHover(id, event);
			}
			@Override
			public void hoverNoIcon(HoverEvent event) {
				
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
					return 0.003f;
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
					return 0.003f+icon.dim.getWidth()+0.02f;
				}
				@Override
				public float getY(){
					return background.dim.getHeight()-icon.dim.getHeight()+0.05f;
				}
			};
		}
		else if(element==shipName){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return 0.003f+icon.dim.getWidth()+0.12f;
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
					return 0.003f+icon.dim.getWidth()+0.2f;
				}
				@Override
				public float getY(){
					return background.dim.getHeight()-icon.dim.getHeight();
				}
			};
		}
		else if(element==shipFuel){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return background.dim.getWidth()-0.003f-element.dim.getWidth();
				}
				@Override
				public float getY(){
					return background.dim.getHeight()-icon.dim.getHeight();
				}
			};
		}
		else if(element==shipHp){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return background.dim.getWidth()-((GraphicElement)shipFuel).dim.getWidth()-0.003f-element.dim.getWidth();
				}
				@Override
				public float getY(){
					return background.dim.getHeight()-icon.dim.getHeight();
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
		public void performOnHover(int id, HoverEvent event) {
			crew.performOnHover(this.id, event);
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
					select(false);
					crew.selectIcon(id);
				}
			}
		}
	}

	@Override
	public Iterator<Iconic> iterator() {
		final Iterator<Iconic> crewItr = crew.iterator();
		final Iterator<TraitCluster> traitClusterItr = traitClusters.iterator();
		return new Iterator<Iconic>(){

			private Iterator<PersonelTrait> traitItr;
			@Override
			public boolean hasNext() {
				return crewItr.hasNext()||traitClusterItr.hasNext();
			}

			@Override
			public Iconic next() {
				if(crewItr.hasNext()){
					return crewItr.next();
				}
				else {
					if(traitItr==null||!traitItr.hasNext()){
						traitItr = traitClusterItr.next().traits.iterator();
						return next();
					}
					else {
						return traitItr.next().getIcon();
					}
				}
			}

			@Override
			public void remove() {				
			}
			
		};
	}


	@Override
	public IconListener getIconListener(Iconic icon) {
		if(icon instanceof PortraitIcon){
			return crew.getIconListener(icon);
		}
		else {
			for(TraitCluster cluster:traitClusters){
				for(PersonelTrait trait:cluster.traits){
					if(trait.getIcon() == icon){
						return cluster;
					}
				}
			}
		}
		return null;
	}


	@Override
	public void hoverNoIcon(HoverEvent event) {
		crew.hoverNoIcon(event);
	}
	
	
}
