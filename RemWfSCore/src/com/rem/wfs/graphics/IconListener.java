package com.rem.wfs.graphics;

import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.gui.inputs.HoverEvent;

public interface IconListener {

	public void performOnHoverOn(int id, HoverEvent event);
	public void performOnHoverOff(int id, HoverEvent event);
	public void performOnClick(int id, ClickEvent event);
	public void performOnRelease(int id, ClickEvent event);
}
