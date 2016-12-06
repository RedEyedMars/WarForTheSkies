package com.rem.core.gui.graphics;

import com.rem.core.Hub;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.gui.inputs.HoverEvent;

public class GraphicEntity extends GraphicView {
	protected GraphicElement element;
	protected GraphicView root = null;
	protected Animation<? extends GraphicEntity> animation = null;
	public GraphicEntity(GraphicElement element) {
		super();
		this.element = element;
		this.element.setView(this);
	}
	public GraphicEntity() {
		this(new GraphicElement());
	}
	public float getX(){
		return element.getX();
	}
	public float getY(){
		return element.getY();
	}
	public float getWidth(){
		return element.getWidth();
	}
	public float getHeight(){
		return element.getHeight();
	}
	@Override
	public void resize(float w, float h){
		element.setWidth(w);
		element.setHeight(h);
		super.resize(w, h);
	}
	@Override
	public void resize(float w, float h, float dw, float dh){
		element.setWidth(w);
		element.setHeight(h);
		super.resize(w, h,dw,dh);
	}

	@Override
	public void reposition(float x, float y){
		element.setX(x);
		element.setY(y);
		super.reposition(x,y);
	}

	@Override
	public void onAddToDrawable(){
		Hub.renderer.addElement(element);
		super.onAddToDrawable();
	}

	@Override
	public void onRemoveFromDrawable(){
		Hub.renderer.removeElement(element);
		super.onRemoveFromDrawable();

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void animate(){
		if(animation!=null){
			((Animation<GraphicEntity>)animation).onAnimate(this);
		}
		super.animate();
	}
	public void setAnimation(Animation<? extends GraphicEntity> animation){
		this.animation = animation;
	}
	@Override
	public void setVisible(boolean vis){
		element.setVisible(vis);
		super.setVisible(vis);
	}
	@Override
	public boolean isVisible(){
		return element.isVisible();
	}	

	@Override
	public void turnOff() {
		element.on(false);		
		super.turnOff();
	}
	@Override
	public void turnOn(){
		element.on(true);
		super.turnOn();
	}
	public void setFrame(int i) {
		element.setFrame(i);
	}
	public int getFrame(){
		return element.getFrame();
	}
	public void rotate(float r) {
		element.rotate((float) (r*360/2f/Math.PI));
	}

	public void setTextureName(int id) {
		this.element.setTexture(id);
	}
	public int getTexture() {
		return element.getTexture();
	}
	public GraphicElement getGraphicElement() {
		return element;
	}
	public boolean isWithin(float dx, float dy) {
		return element.isWithin(dx, dy);
	}
	public void setShape(int i){
		element.setShape(i);
	}
	@Override
	public boolean onClick(ClickEvent event) {
		if(this.isWithin(event.getX(), event.getY())){
			super.onClick(event);
			if(event.getAction()==ClickEvent.ACTION_DOWN){
				this.performOnClick(event);
			}
			else if(event.getAction()==ClickEvent.ACTION_UP){
				this.performOnRelease(event);
			}
			return true;
		}
		else return super.onClick(event);
	}

	@Override
	public boolean onHover(HoverEvent event) {
		if(this.isWithin(event.getX(), event.getY())){
			super.onHover(event);
			this.performOnHover(event);
			return true;
		}
		else return super.onHover(event);
	}

	public void performOnClick(ClickEvent event){		
	}
	public void performOnRelease(ClickEvent event){		
	}

	public void performOnHover(HoverEvent event){		
	}
	public void setLayer(int layer) {
		this.element.setLayer(layer);
		for(GraphicEntity entity:children){
			entity.setLayer(layer);
		}
	}
}
