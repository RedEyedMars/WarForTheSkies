package com.rem.otl.pc;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;

import com.rem.otl.pc.gui.gl.Log;
import com.rem.otl.pc.gui.gl.PCGui;
import com.rem.otl.pc.gui.graphics.PCGraphicRenderer;
import com.rem.otl.pc.gui.music.Track;
import com.rem.wfs.environment.SpaceSector;
import com.rem.core.gui.IGui;
import com.rem.core.gui.graphics.GraphicRenderer;
import com.rem.core.gui.music.ITrack;
import com.rem.core.gui.music.MusicPlayer;
import com.rem.core.ICreator;
import com.rem.core.IFileManager;
import com.rem.core.ILog;
import com.rem.core.Setupable;
import com.rem.core.environment.Environment;

public class Creator implements ICreator{


	@Override
	public IGui createGui(Setupable setupable) {
		return new PCGui(setupable);
	}

	@Override
	public GraphicRenderer createGraphicRenderer(Setupable setupable){
		GraphicRenderer renderer = new PCGraphicRenderer();
		renderer.loadImages();
		return renderer;
	}

	public ITrack createTrack(String a, String b, String c, String d){
		return new Track(a,b,c,d);
	}

	@Override
	public String copyFromClipboard() {
		String ret = null;
		try {
			Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
			ret = (String) clip.getContents(null).getTransferData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException e){
			e.printStackTrace();			
		} catch(IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public void copyToClipboard(String toCopy) {
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection stringSelection = new StringSelection(toCopy);
		clip.setContents(stringSelection, null);
	}

	@Override
	public int getPlainFontStyle() {
		return Font.PLAIN;
	}


	@Override
	public ILog createLog() {
		return new Log();
	}

	@Override
	public MusicPlayer createMusic() {
		return new MusicPlayer();
	}
	@Override
	public IFileManager createFileManager(Setupable main) {
		for(String folderName:new String[]{
				"WarForTheSkies",
				"WarForTheSkies"+File.separator+"data"
		}){
		File defaultFolder = new File(System.getenv("APPDATA"),folderName);
		if(!defaultFolder.exists()){
			defaultFolder.mkdirs();
		}
		}
		return new FileManager();
	}
	@Override
	public Environment createPlaceHolderEnvironment() {
		return new SpaceSector();
	}
}
