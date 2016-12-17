package com.rem.wfs.graphics;

import com.rem.core.gui.inputs.HoverEvent;

public interface Iconic {

	public void setParentSelectedStatus(boolean status);

	public boolean onHover(HoverEvent event);

	public void resize(float w, float h);

	public void setVisible(boolean friendlyState);

	public int getId();

}
