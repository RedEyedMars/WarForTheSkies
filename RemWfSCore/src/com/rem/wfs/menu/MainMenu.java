package com.rem.wfs.menu;

import com.rem.core.Hub;
import com.rem.core.IFileManager;
import com.rem.core.gui.graphics.MenuButton;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.storage.Storage;
import com.rem.wfs.Game;
import com.rem.wfs.environment.SpaceSector;

public class MainMenu extends Menu{

	@Override
	protected void displayButtons() {		

		MenuButton generateNewSectorButton = new MenuButton("Generate New"){
			@Override
			public boolean onClick(ClickEvent event){

				if(dim.isWithin(event.getX(), event.getY())){
					if(event.getAction()==ClickEvent.ACTION_UP){
						Hub.map = new SpaceSector();
						Hub.map.onCreate();
						Storage.save(Hub.manager.createOutputStream("scratch.sector", IFileManager.RELATIVE), Hub.map);
						Hub.gui.setView(new Game());
					}
					return super.onClick(event);
				}
				else return false;
			}		
		};
		generateNewSectorButton.reposition(0.2f, 0.5f-generateNewSectorButton.dim.getHeight()/2f);
		tree.addChild(generateNewSectorButton);

		if(Hub.manager.getDirectory("scratch.sector",IFileManager.RELATIVE).exists()){
			generateNewSectorButton.reposition(
					0.2f, 0.666f-generateNewSectorButton.dim.getHeight()/2f);
			MenuButton continueSectorButton = new MenuButton("Continue"){
				@Override
				public boolean onClick(ClickEvent event){

					if(dim.isWithin(event.getX(), event.getY())){
						if(event.getAction()==ClickEvent.ACTION_UP){
							Hub.map = new SpaceSector();
							Hub.loadMapFromFileName("scratch.sector");
							Hub.gui.setView(new Game());
						}
						return super.onClick(event);
					}
					else return false;
				}
			};
			continueSectorButton.reposition(0.2f, 0.333f-continueSectorButton.dim.getHeight()/2f);
			tree.addChild(continueSectorButton);
		}
	}

	@Override
	public void update(double secondsSinceLastFrame){
		super.update(secondsSinceLastFrame);
	}
}
