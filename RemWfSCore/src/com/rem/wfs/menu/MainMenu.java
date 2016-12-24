package com.rem.wfs.menu;

import com.rem.core.Action;
import com.rem.core.Hub;
import com.rem.core.IFileManager;
import com.rem.core.gui.graphics.GraphicText;
import com.rem.core.gui.graphics.MenuButton;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.wfs.Game;
import com.rem.wfs.environment.SpaceSector;
import com.rem.wfs.graphics.Button;
import com.rem.wfs.graphics.R;

public class MainMenu extends Menu{

	@Override
	protected void displayButtons() {		

		if(Hub.manager.getDirectory("scratch.sector",IFileManager.RELATIVE).exists()){
			MenuButton continueSectorButton = new MenuButton("Continue"){
				@Override
				public boolean onClick(ClickEvent event){

					if(dim.isWithin(event.getX(), event.getY())){
						if(event.getAction()==ClickEvent.ACTION_UP){
							Hub.map = new SpaceSector();
							Hub.loadMapFromFileName("scratch.sector");
							Hub.gui.setView(new Game());
							((SpaceSector)Hub.map).start();
						}
						return super.onClick(event);
					}
					else return false;
				}
			};
			continueSectorButton.reposition(0.2f, 0.5f-continueSectorButton.dim.getHeight()/2f);
			tree.addChild(continueSectorButton);

			GraphicText deleteText = new GraphicText(R.impact,"DELETE",R.MID_LAYER);
			Button button = new Button(new Button.Setting(R.buttons_1), R.MID_LAYER,
					0.25f+continueSectorButton.dim.getWidth(),0.5f-continueSectorButton.dim.getHeight()/2f,deleteText.dim.getWidth(),
					new Action<ClickEvent>(){
						@Override
						public void act(ClickEvent subject) {
							Hub.manager.deleteFile("scratch.sector",IFileManager.RELATIVE);
							Hub.gui.setView(new MainMenu());
						}				
					},deleteText);
			tree.addChild(button);
		}
		else {
			MenuButton generateNewSectorButton = new MenuButton("New"){
				@Override
				public boolean onClick(ClickEvent event){

					if(dim.isWithin(event.getX(), event.getY())){
						if(event.getAction()==ClickEvent.ACTION_UP){
							Hub.map = new SpaceSector();
							Hub.map.setNameAndFileName("scratch", Hub.manager.getDirectory("scratch.sector", IFileManager.RELATIVE).getAbsolutePath());
							Hub.map.onCreate();
							Hub.gui.setView(new Game());
							((SpaceSector)Hub.map).start();
						}
						return super.onClick(event);
					}
					else return false;
				}		
			};
			generateNewSectorButton.reposition(0.2f, 0.5f-generateNewSectorButton.dim.getHeight()/2f);
			tree.addChild(generateNewSectorButton);
		}
	}

	@Override
	public void update(double secondsSinceLastFrame){
		super.update(secondsSinceLastFrame);
	}
}
