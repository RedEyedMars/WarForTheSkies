package com.rem.wfs.menu;

import com.rem.core.gui.graphics.elements.BlankGraphicElement;
import com.rem.wfs.graphics.icons.IconListener;
import com.rem.wfs.graphics.icons.Iconic;

public abstract class GraphicIconContainer extends BlankGraphicElement implements Iterable<Iconic>{

	public abstract IconListener getIconListener(Iconic icon);
}
