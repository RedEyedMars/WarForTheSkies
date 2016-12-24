package com.rem.wfs.graphics.icons;

public class IconHandler {
	private IconListener listener = null;
	private IconHandler previous;
	public IconHandler(IconHandler previous){
		this.previous = previous;
	}
	public void setListener(IconListener listener){
		this.listener = listener;
	}
	public IconListener getListener(){
		return this.listener;
	}
	public IconHandler getPrevious(){
		return this.previous;
	}
}