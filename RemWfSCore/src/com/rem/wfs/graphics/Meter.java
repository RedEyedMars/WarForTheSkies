package com.rem.wfs.graphics;

import com.rem.core.gui.graphics.AnimationHandler;
import com.rem.core.gui.graphics.elements.BlankGraphicElement;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.StretchableGraphicElement;
import com.rem.core.gui.graphics.elements.dimension.DimensionHandler;
import com.rem.core.gui.graphics.elements.tree.TreeHandler;
import com.rem.wfs.environment.Statistic;
import com.rem.wfs.graphics.icons.IconHandler;
import com.rem.wfs.graphics.icons.IconListener;
import com.rem.wfs.graphics.icons.Iconic;

public class Meter extends BlankGraphicElement implements Iconic{

	protected GraphicElement foreground;
	protected GraphicElement background;
	protected StretchableGraphicElement animater;
	protected boolean parentSelected = false;
	private IconHandler handler;
	private Statistic toWatch;

	public Meter(Statistic toWatch, String description,
			int animaterTextureId, int animaterFrame,
			int foregroundTextureId, int foregroundFrame,
			int backgroundTextureId, int backgroundFrame,
			int layer, Animation animation){
		super();

		if(backgroundTextureId>-1){
			background = new GraphicElement(backgroundTextureId,backgroundFrame,layer);
			tree.addChild(background);
		}
		animater = new StretchableGraphicElement(animaterTextureId,animaterFrame,layer);
		tree.addChild(animater);
		if(foregroundTextureId>-1){
			foreground = new GraphicElement(foregroundTextureId,foregroundFrame,layer);
			tree.addChild(foreground);
		}
		if(animation!=null){
			animation.setup(toWatch);
			this.animationHandler = animation;
		}
		this.toWatch = toWatch;
	}
	
	public void setAnimation(Animation animation){
		if(animation!=null){
			animation.setup(toWatch);
			this.animationHandler = animation;
		}
	}

	@Override
	public DimensionHandler createDimensionHandler(int textureId, float x, float y, float w, float h){
		return new DimensionHandler(textureId,x,y,w,h){
			@Override
			public float getWidth(){
				return animater.dim.getWidth();
			}
			@Override
			public float getHeight(){
				return animater.dim.getHeight();
			}
		};
	}

	public Meter(Statistic toWatch, String description,
			int animaterTextureId, int animaterFrame,
			int layer, Animation animation){
		this(toWatch,description, animaterTextureId,animaterFrame,-1,-1,-1,-1,layer,animation);
	}

	@Override
	public void animate(){
		if(this.animationHandler!=null){
			this.animationHandler.animate(animater);
		}
	}


	@Override
	public void setParentSelectedStatus(boolean status) {
		this.parentSelected = status;
	}

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public void addIconListener(IconListener listener) {
		this.handler = new IconHandler(this.handler);
		this.handler.setListener(listener);
	}
	@Override
	public void removeIconListener() {
		this.handler = this.handler.getPrevious();
	}
	@Override
	public void addToTree(TreeHandler addSelfToThisTree) {
		addSelfToThisTree.addChild(this);		
	}
	public static abstract class Animation implements AnimationHandler {

		private Statistic toWatch;

		private void setup(Statistic toWatch){
			this.toWatch = toWatch;
		}

		@Override
		public void animate(GraphicElement element) {
			animate(element,toWatch.getValue()/toWatch.getLimit());
		}

		public abstract void animate(GraphicElement element, float percentFull);

	}
	public GraphicElement getAnimater() {
		return animater;
	}

	public GraphicElement getForeground() {
		return foreground;
	}

	@Override
	public IconListener getIconListener() {
		return handler.getListener();
	}

}
