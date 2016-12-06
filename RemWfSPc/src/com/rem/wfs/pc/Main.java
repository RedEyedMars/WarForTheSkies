package com.rem.wfs.pc;

import com.rem.core.gui.graphics.GraphicView;
import com.rem.core.Hub;
import com.rem.core.Setupable;
import com.rem.core.storage.Storage;
import com.rem.wfs.menu.MainMenu;

public class Main implements Setupable {

	private static final int LOAD_TO_MAIN = 0;
	private static final int LOAD_TO_SOLO = 1;
	@SuppressWarnings("unused")
	private static final int LOAD_TO_HOST = 2;
	@SuppressWarnings("unused")
	private static final int LOAD_TO_JOIN = 3;
	@SuppressWarnings("unused")
	private static int state = LOAD_TO_MAIN;
	@SuppressWarnings("unused")
	private static String filePath = "";
	
	public static void main(String[] args) {
		Storage.debug_save = true;
		Storage.debug_load = true;
		handleArgs(args);
		Hub.load(new Creator(),new Main(),true,true, true);
	}

	private static boolean handleArgs(String[] args) {
		for(int i=0;i<args.length;++i){
			if("solo".equals(args[i])&&i<args.length-1){
				state = LOAD_TO_SOLO;
				filePath = args[i+1];
			}
		}
		return false;
	}

	public void setup(){
	}
	public GraphicView getFirstView(){
		return new MainMenu();
	}
}
