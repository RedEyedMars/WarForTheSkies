package com.rem.core;

import com.rem.core.environment.Environment;
import com.rem.core.gui.IGui;
import com.rem.core.gui.graphics.GraphicRenderer;
import com.rem.core.gui.music.ITrack;
import com.rem.core.gui.music.MusicPlayer;

public interface ICreator {

	public IGui createGui(Setupable setupable);
	public GraphicRenderer createGraphicRenderer(Setupable main);
	public ILog createLog();
	public MusicPlayer createMusic();

	
	public ITrack createTrack(String substring, String string, String string2, String currentLicense);
	
	public int getPlainFontStyle();
	public void copyToClipboard(String copyTo);
	public String copyFromClipboard();
	
	public IFileManager createFileManager(Setupable main);
	public Environment createPlaceHolderEnvironment();
}
