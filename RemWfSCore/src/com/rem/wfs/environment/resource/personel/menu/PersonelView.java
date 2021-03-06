package com.rem.wfs.environment.resource.personel.menu;


import java.util.Iterator;

import com.rem.core.Action;
import com.rem.core.gui.graphics.GraphicText;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.OffsetHandler;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.gui.inputs.HoverEvent;
import com.rem.wfs.environment.resource.personel.Personel;
import com.rem.wfs.environment.resource.personel.PersonelTrait;
import com.rem.wfs.environment.resource.personel.PortraitIcon;
import com.rem.wfs.graphics.Button;
import com.rem.wfs.graphics.R;
import com.rem.wfs.graphics.icons.Icon;
import com.rem.wfs.graphics.icons.IconListener;
import com.rem.wfs.graphics.icons.Iconic;
import com.rem.wfs.menu.OverlayView;

public class PersonelView extends OverlayView{
	private Personel person;
	private PortraitIcon icon;
	private GraphicText nameLabel;
	private GraphicText firstName;
	private GraphicText lastName;
	private GraphicElement swapTraits_1_2;
	private GraphicElement swapTraits_0_2;
	private GraphicElement swapTraits_0_1;
	public PersonelView(
			String name,
			Personel personToView, GraphicElement previousView){		
		super(name,0.7f, 0.7f);
		this.person = personToView;
		icon = personToView.getPortaitIcon(0);
		icon.setLayer(R.MID_LAYER);
		icon.resize(0.16f, 0.16f);
		tree.addChild(icon);
		
		nameLabel = new GraphicText(R.impact,"First Name:\n\nLast Name:",R.MID_LAYER);
		tree.addChild(nameLabel);
		
		firstName = new GraphicText(R.impact,personToView.getName().getFirstName(),R.MID_LAYER);
		tree.addChild(firstName);
		lastName = new GraphicText(R.impact,personToView.getName().getLastName(),R.MID_LAYER);
		tree.addChild(lastName);
		
		for(int i=0;i<personToView.getTraits().size();++i){
			Icon icon = personToView.getTraits().get(i).getIcon();
			icon.setParentSelectedStatus(false);
			icon.setLayer(R.MID_LAYER);
			tree.addChild(icon);
		}		
		
		swapTraits_0_2 = 
				new Button(new Button.Setting(R.buttons_1),
						   R.MID_LAYER,0f,0f,0f,
						   new Action<ClickEvent>(){
							@Override
							public void act(ClickEvent subject) {
								person.getTraits().get(0).swap(
										person.getTraits().get(2)
										);
							}
					
						   },
						   new GraphicElement(R.traits,16,R.MID_LAYER));
		tree.addChild(swapTraits_0_2);
		swapTraits_0_1 = 
				new Button(new Button.Setting(R.buttons_1),
						   R.MID_LAYER,0f,0f,0f,
						   new Action<ClickEvent>(){
							@Override
							public void act(ClickEvent subject) {
								person.getTraits().get(0).swap(
										person.getTraits().get(1)
										);
							}
					
						   },
						   new GraphicElement(R.traits,24,R.MID_LAYER));
		tree.addChild(swapTraits_0_1);
		swapTraits_1_2 = 
				new Button(new Button.Setting(R.buttons_1),
						   R.MID_LAYER,0f,0f,0f,
						   new Action<ClickEvent>(){
							@Override
							public void act(ClickEvent subject) {
								person.getTraits().get(1).swap(
										person.getTraits().get(2)
										);
							}
					
						   },
						   new GraphicElement(R.traits,8,R.MID_LAYER));
		tree.addChild(swapTraits_1_2);
		
		reposition(0.15f,0.15f);
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
					return background.dim.getHeight()-icon.dim.getHeight()-0.055f;
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
					return background.dim.getHeight()-icon.dim.getHeight();
				}
			};
		}
		else if(element==firstName){
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
		else if(element==lastName){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return 0.1f+icon.dim.getWidth()+0.2f;
				}
				@Override
				public float getY(){
					return background.dim.getHeight()-icon.dim.getHeight()-0.05f;
				}
			};
		}
		else if(element==swapTraits_0_2){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return 0.45f;
				}
				@Override
				public float getY(){
					return 0.3f;
				}
			};
		}
		else if(element==swapTraits_0_1){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return 0.25f;
				}
				@Override
				public float getY(){
					return 0.3f;
				}
			};
		}
		else if(element==swapTraits_1_2){
			return new OffsetHandler(){
				@Override
				public float getX(){
					return 0.35f;
				}
				@Override
				public float getY(){
					return 0.1f;
				}
			};
		}
		else if(element instanceof Icon){
			final Icon elementAsIcon = (Icon)element;
			return new OffsetHandler(){
				@Override
				public float getX(){
					int id = elementAsIcon.getId();
					if(id==0){
						return element.dim.getWidth()/2f+0.25f;
					}
					else {
						return (id-1)*element.dim.getWidth()+0.25f;
					}
				}
				@Override
				public float getY(){
					if( elementAsIcon.getId()==0){
						return 0.2f+element.dim.getHeight()*0.75f;
					}
					else {
						return 0.2f;
					}
				}
			};
		}
		else return super.createOffsetHandler(element);
	}

	@Override
	public Iterator<Iconic> iterator() {
		final Iterator<PersonelTrait> itr = person.getTraits().iterator();
		return new Iterator<Iconic>(){

			@Override
			public boolean hasNext() {
				return itr.hasNext();
			}

			@Override
			public Iconic next() {
				return itr.next().getIcon();
			}

			@Override
			public void remove() {				
			}
			
		};
	}

	@Override
	public IconListener getIconListener(Iconic icon) {
		return null;
	}

	@Override
	public void hoverNoIcon(HoverEvent event) {		
	}
}
