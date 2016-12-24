package com.rem.wfs.graphics.icons;

import com.rem.core.gui.graphics.elements.tree.TreeHandler;

public interface Iconic {

	public void addToTree(TreeHandler addSelfToThisTree);
	
	public void setParentSelectedStatus(boolean status);

	public int getId();

	public void addIconListener(IconListener iconListener);

	public void removeIconListener();

}
