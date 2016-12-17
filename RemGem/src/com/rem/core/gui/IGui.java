package com.rem.core.gui;

import java.net.URL;

import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.inputs.KeyBoardListener;
import com.rem.core.storage.FileResource;

/**
 * Use setMaterial(), setLight() and makeTexture() to control light and material properties.
 * <P>
 * napier at potatoland dot org
 */
public interface IGui {

	public void setup();
	
	public void update();
	
	public void run() throws InterruptedException;
	
	public void end();
	
	public void setView(GraphicElement view);

	public void setFinished(boolean b);

	public int createTexture(@SuppressWarnings("rawtypes") FileResource resource);
	
	public void openWebpage(URL url);

	public float getDisplayWidth();

	public float getDisplayHeight();

	public void showKeyBoardDisplay(KeyBoardListener listener);
	public void hideKeyBoardDisplay(KeyBoardListener listener);
	public boolean isKeyBoardShowing();

}