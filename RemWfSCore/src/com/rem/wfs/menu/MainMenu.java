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
			public void performOnRelease(ClickEvent event){
				Hub.map = new SpaceSector();
				Hub.map.onCreate();
				Storage.saveMap(Hub.manager.createOutputStream("scratch.sector", IFileManager.RELATIVE), Hub.map);
				Hub.gui.setView(new Game());
			}
		};
		generateNewSectorButton.reposition(0.2f, 0.5f-generateNewSectorButton.getHeight()/2f);
		addChild(generateNewSectorButton);
		
		if(Hub.manager.getDirectory("scratch.sector",IFileManager.RELATIVE).exists()){
			generateNewSectorButton.reposition(0.2f, 0.666f-generateNewSectorButton.getHeight()/2f);
			MenuButton continueSectorButton = new MenuButton("Continue"){
				@Override
				public void performOnRelease(ClickEvent event){
					Hub.map = new SpaceSector();
					Hub.loadMapFromFileName("scratch.sector");
					Hub.gui.setView(new Game());
				}
			};
			continueSectorButton.reposition(0.2f, 0.333f-continueSectorButton.getHeight()/2f);
			addChild(continueSectorButton);
		}
	}

	@Override
	public void update(double secondsSinceLastFrame){
		super.update(secondsSinceLastFrame);
	}
}
