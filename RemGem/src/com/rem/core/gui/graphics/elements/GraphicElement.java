package com.rem.core.gui.graphics.elements;

import com.rem.core.Hub;
import com.rem.core.gui.Updatable;
import com.rem.core.gui.graphics.AnimationHandler;
import com.rem.core.gui.graphics.R;
import com.rem.core.gui.graphics.ShapeHandler;
import com.rem.core.gui.graphics.VisualBundle;
import com.rem.core.gui.graphics.elements.dimension.DimensionHandler;
import com.rem.core.gui.graphics.elements.tree.BranchTreeHandler;
import com.rem.core.gui.graphics.elements.tree.TreeHandler;
import com.rem.core.gui.inputs.ClickEvent;
import com.rem.core.gui.inputs.HoverEvent;
import com.rem.core.gui.inputs.MouseListener;




public class GraphicElement implements MouseListener, Updatable {


	public static final int SHAPE_SQUARE = 0;
	public static final int SHAPE_ISOSCELES_TRIANGLE = 1;
	public static final int SHAPE_TOP_LEFT_TRIANGLE = 2;
	public static final int SHAPE_TOP_RIGHT_TRIANGLE = 3;
	public static final int SHAPE_BOTTOM_LEFT_TRIANGLE = 4;
	public static final int SHAPE_BOTTOM_RIGHT_TRIANGLE = 5;
	public static final int SHAPE_HEXAGON = 6;

	public static final int SEGMENT_COUNT = 10;
	private static final OffsetHandler DEFAULT_OFFSET_HANDLER = new OffsetHandler();

	protected int textureId;
	protected int frame = 0;
	protected int layer = 0;

	protected boolean isVisible = true;
	protected boolean on = true;

	public final DimensionHandler dim;
	public final TreeHandler tree;
	private ShapeHandler shapeHandler;
	private AnimationHandler animationHandler;
	private OffsetHandler offsetHandler = DEFAULT_OFFSET_HANDLER;

	private GraphicElement root = null;

	public GraphicElement(int textureId, int initialFrame, int layer, float x, float y, float w, float h){

		dim = createDimensionHandler(textureId,x,y,w,h);
		shapeHandler = createShapeHandler();
		tree = createTreeHandler();
		animationHandler = createAnimationHandler();
		setTexture(textureId);
		setFrame(initialFrame);
		setLayer(layer);
	}
	public GraphicElement(int textureId, int initialFrame, int layer){
		this(textureId,initialFrame,layer,0,0,DimensionHandler.getDefaultWidth(textureId),DimensionHandler.getDefaultHeight(textureId));
	}
	
	public OffsetHandler createOffsetHandler(final GraphicElement element){
		return DEFAULT_OFFSET_HANDLER;
	}
	
	public OffsetHandler getOffset(){
		return offsetHandler;
	}
	
	public void setOffset(OffsetHandler offsetHandler){
		this.offsetHandler = offsetHandler;
	}

	protected AnimationHandler createAnimationHandler() {
		return null;
	}

	protected TreeHandler createTreeHandler() {
		return new BranchTreeHandler(this);
	}

	protected ShapeHandler createShapeHandler() {
		return ShapeHandler.square;
	}

	protected DimensionHandler createDimensionHandler(int textureId, float x, float y, float w, float h) {
		return new DimensionHandler(textureId,x,y,w,h);
	}

	public void addToDrawable(){
		Hub.renderer.addElement(this);
		for(GraphicElement element:tree){
			element.addToDrawable();
		}
	}
	public void removeFromDrawable(){
		Hub.renderer.removeElement(this);
		for(GraphicElement element:tree){
			element.removeFromDrawable();
		}
	}

	public void animate() {
		if(animationHandler!=null){
			animationHandler.animate(this);
		}
	}

	public void setTexture(int id){
		textureId = id;
	}

	public int getTexture() {
		return textureId;
	}

	public boolean draw(VisualBundle bundle){
		shapeHandler.setVisual(bundle);
		dim.setVisual(bundle);
		return isVisible()&&on;
	}
	public boolean isVisible(){
		return isVisible;
	}

	public int getFrame(){
		return frame ;
	}
	public void setFrame(int frame) {
		this.frame = frame;
	}

	public boolean isWithin(float dx, float dy) {
		return dim.isWithin(dx,dy);
	}

	public void update(double secondsSinceLastFrame) {
		for(GraphicElement element:tree){
			element.update(secondsSinceLastFrame);
		}
	}

	public void setVisible(boolean b) {
		this.isVisible = b;
		for(GraphicElement element:tree){
			element.setVisible(b);
		}
	}

	public void turnOff() {
		this.on = false;
		for(GraphicElement element:tree){
			element.turnOff();
		}
	}
	public void turnOn(){
		this.on = true;
		for(GraphicElement element:tree){
			element.turnOn();
		}
	}
	public int getLayer() {
		return layer;
	}
	public void setLayer(int layer){
		this.layer = layer;
		for(GraphicElement element:tree){
			element.setLayer(layer);
		}
	}
	public ShapeHandler shape(){
		return shapeHandler;
	}
	public void setShape(int shapeId){
		switch(shapeId){
		case GraphicElement.SHAPE_SQUARE:{
			this.shapeHandler = ShapeHandler.square;
			break;
		}
		case GraphicElement.SHAPE_ISOSCELES_TRIANGLE:{
			this.shapeHandler = ShapeHandler.iso_triangle;
			break;
		}
		case GraphicElement.SHAPE_TOP_LEFT_TRIANGLE:{
			this.shapeHandler = ShapeHandler.top_left_triangle;
			break;
		}
		case GraphicElement.SHAPE_TOP_RIGHT_TRIANGLE:{
			this.shapeHandler = ShapeHandler.top_right_triangle;
			break;
		}
		case GraphicElement.SHAPE_BOTTOM_LEFT_TRIANGLE:{
			this.shapeHandler = ShapeHandler.bot_left_triangle;
			break;
		}
		case GraphicElement.SHAPE_BOTTOM_RIGHT_TRIANGLE:{
			this.shapeHandler = ShapeHandler.bot_right_triangle;
			break;
		}
		case GraphicElement.SHAPE_HEXAGON:{
			this.shapeHandler = ShapeHandler.hexagon;
			break;
		}
		}
	}
	public boolean isShape(int shapeId){
		return shapeHandler.is(shapeId);
	}
	public static String getShapeName(int i) {
		switch(i){
		case SHAPE_SQUARE:{
			return "Square";
		}
		case SHAPE_ISOSCELES_TRIANGLE:{
			return "Isosceles Triangle";
		}
		case SHAPE_TOP_LEFT_TRIANGLE:{
			return "Right Triangle(point at the top left)";
		}
		case SHAPE_TOP_RIGHT_TRIANGLE:{
			return "Right Triangle(point at the top right)";
		}
		case SHAPE_BOTTOM_LEFT_TRIANGLE:{
			return "Right Triangle(point at the bottom left)";
		}
		case SHAPE_BOTTOM_RIGHT_TRIANGLE:{
			return "Right Triangle(point at the bottom right)";
		}
		case SHAPE_HEXAGON:{
			return "Hexagon";
		}
		}
		return null;
	}

	@Override
	public boolean onClick(ClickEvent event) {
		for(GraphicElement element:tree){
			element.onClick(event);
		}
		return true;
	}
	@Override
	public boolean onHover(HoverEvent event) {
		for(GraphicElement element:tree){
			element.onHover(event);
		}
		return true;
	}
	@Override
	public void onMouseScroll(int distance) {
		for(GraphicElement element:tree){
			element.onMouseScroll(distance);
		}
	}	

	public void resize(float w, float h) {
		dim.resize(w,h);
		for(GraphicElement element:tree){
			element.resize(element.offsetHandler.getWidth(w),
					       element.offsetHandler.getHeight(h));
		}
	}

	public void reposition(float x, float y) {
		dim.setX(x);
		dim.setY(y);
		for(int i=0;i<tree.size();++i){
			tree.getChild(i).reposition(
					x+tree.getChild(i).offsetHandler.getX(i),
					y+tree.getChild(i).offsetHandler.getY(i));
		}
	}
	public int getDrawMode() {
		return R.DRAW_MODE_VERTICES;
	}
	public GraphicElement getRoot() {
		return root;
	}
	public void setRoot(GraphicElement root) {
		this.root = root;
		for(GraphicElement element:tree){
			element.setRoot(root);
		}
	}
	
	

}
