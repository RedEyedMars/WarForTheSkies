package com.rem.core.gui.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.rem.core.Hub;




public class GraphicElement {

	
	public static final int SHAPE_SQUARE = 0;
	public static final int SHAPE_ISOSCELES_TRIANGLE = 1;
	public static final int SHAPE_TOP_LEFT_TRIANGLE = 2;
	public static final int SHAPE_TOP_RIGHT_TRIANGLE = 3;
	public static final int SHAPE_BOTTOM_LEFT_TRIANGLE = 4;
	public static final int SHAPE_BOTTOM_RIGHT_TRIANGLE = 5;
	public static final int SHAPE_HEXAGON = 6;
	

	public static final int COLOUR_WHITE = 0;
	public static final int COLOUR_BLACK = 1;
	public static final int COLOUR_RED = 2;
	public static final int COLOUR_GREEN = 3;
	public static final int COLOUR_BLUE = 4;
	public static final int COLOUR_PURPLE = 5;
	public static final int COLOUR_YELLOW = 6;
	public static final int COLOUR_CYAN = 7;

	public static final int SEGMENT_COUNT = 10;
	
	protected static FloatBuffer squareBuffer;
	protected static FloatBuffer triangleBuffer;
	protected static FloatBuffer trTriangleBuffer;
	protected static FloatBuffer tlTriangleBuffer;
	protected static FloatBuffer brTriangleBuffer;
	protected static FloatBuffer blTriangleBuffer;	
	protected static FloatBuffer hexagonBuffer;

	protected float x = 0f;
	protected float y = 0f;
	protected float width = 1f;
	protected float height = 1f;
	protected float angle = 0.0f;
	
	protected int vertexNumber = 4;
	protected FloatBuffer vertexBuffer;

	protected String texName;
	protected int frame = 0;
	protected int layer = 0;

	protected boolean isVisible = true;
	protected boolean on = true;
	
	private GraphicView view;
	private int shapeId;
	private boolean isBlank = false;

	public GraphicElement(String textureName, int initialFrame, float x, float y, float w, float h, int layer, int shape){
		setTextureName(textureName);
		setFrame(initialFrame);
		setWidth(w);
		setHeight(h);
		setX(x);
		setY(y);
		setShape(shape);
		setLayer(layer);
	}
	public GraphicElement(String textureName, int initialFrame, float x, float y, float w, float h, int layer){
		this(textureName, initialFrame, x,y,w,h, layer, SHAPE_SQUARE);
	}
	public GraphicElement(String textureName, int initialFrame, float x, float y, int layer){
		this(textureName, initialFrame, x,y,1f,1f, layer, SHAPE_SQUARE);
	}
	public GraphicElement(String textureName, float x, float y, int layer){
		this(textureName, 0, x,y,1f,1f, layer, SHAPE_SQUARE);
	}
	public GraphicElement(String textureName, int initialFrame, int layer){
		this(textureName, initialFrame, 0f,0f,1f,1f, layer, SHAPE_SQUARE);
	}
	public GraphicElement(String textureName, int initialFrame, float x, float y, float w, float h){
		this(textureName, initialFrame, x,y,w,h, Hub.BOT_LAYER, SHAPE_SQUARE);
	}
	public GraphicElement(String textureName, float x, float y, float w, float h){
		this(textureName, 0, x,y,w,h, Hub.BOT_LAYER, SHAPE_SQUARE);
	}
	public GraphicElement(String textureName, float x, float y){
		this(textureName, 0, x,y,1f,1f, Hub.BOT_LAYER, SHAPE_SQUARE);
	}
	public GraphicElement(String textureName, int initialFrame){
		this(textureName, initialFrame, 0f,0f,1f,1f, Hub.BOT_LAYER, SHAPE_SQUARE);
	}
	public GraphicElement(String textureName) {
		this(textureName,0, 0f,0f,1f,1f, Hub.BOT_LAYER, SHAPE_SQUARE);
	}
	public GraphicElement(){
		this.isBlank  = true;
	}
	

	public void setView(GraphicView view) {
		this.view = view;
	}
	public void onDraw(){
		
	}
	static {
		float[] vertices = new float[]{
				0f, 0f,  0.0f,		// V1 - bottom left
				0f,  1f,  0.0f,		// V2 - top left
				1f, 0f,  0.0f,		// V3 - bottom right
				1f,  1f,  0.0f,		// V4 - top right

		};

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		squareBuffer = byteBuffer.asFloatBuffer();
		squareBuffer.clear();
		squareBuffer.put(vertices);
		squareBuffer.position(0);
		
		vertices = new float[]{
				0f, 0f,  0.0f,		// V1 - bottom left
				0.5f,  1f,  0.0f,		// V2 - top left
				1f, 0f,  0.0f,		// V3 - bottom right

		};

		byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		triangleBuffer = byteBuffer.asFloatBuffer();
		triangleBuffer.clear();
		triangleBuffer.put(vertices);
		triangleBuffer.position(0);
		
		//topleft
		vertices = new float[]{
				0f, 0f,  0.0f,		// V1 - bottom left
				0f,  1f,  0.0f,		// V2 - top left
				1f,  1f,  0.0f,		// V4 - top right

		};

		byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		tlTriangleBuffer = byteBuffer.asFloatBuffer();
		tlTriangleBuffer.clear();
		tlTriangleBuffer.put(vertices);
		tlTriangleBuffer.position(0);
		//topright
		vertices = new float[]{

				0f, 1f,  0.0f,		// V1 - bottom left
				1f,  1f,  0.0f,		// V4 - top right
				1f, 0f,  0.0f,		// V3 - bottom right

		};

		byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		trTriangleBuffer = byteBuffer.asFloatBuffer();
		trTriangleBuffer.clear();
		trTriangleBuffer.put(vertices);
		trTriangleBuffer.position(0);
		
		//bottomleft
		vertices = new float[]{
				0f, 0f,  0.0f,		// V1 - bottom left
				0f,  1f,  0.0f,		// V2 - top left
				1f,  0f,  0.0f,		// V4 - top right

		};

		byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		blTriangleBuffer = byteBuffer.asFloatBuffer();
		blTriangleBuffer.clear();
		blTriangleBuffer.put(vertices);
		blTriangleBuffer.position(0);
		
		//bottomright
		vertices = new float[]{
				0f, 0f,  0.0f,
				1f, 1f,  0.0f,
				1f,  0f,  0.0f,

		};

		byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		brTriangleBuffer = byteBuffer.asFloatBuffer();
		brTriangleBuffer.clear();
		brTriangleBuffer.put(vertices);
		brTriangleBuffer.position(0);
		/*
		 * 
  3----5
 /|\   |\
1 | \  | 6
 \|  \ |/
  2----4
		 */
		vertices = new float[]{
				0f,  0.2f,  0.0f,
				0f,0.8f,  0.0f,
				0.5f,0f,  0.0f,
				0.5f,1f,  0.0f,
				1f,0.2f,  0.0f,
				1f,  0.8f,  0.0f
		};

		byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		hexagonBuffer = byteBuffer.asFloatBuffer();
		hexagonBuffer.clear();
		hexagonBuffer.put(vertices);
		hexagonBuffer.position(0);
	}
	public void setTextureName(String n){
		texName = n;
	}

	public String getTextureName() {
		return texName;
	}

	public void draw(){
		if(!isBlank){
			
			if(isVisible()&&on)
			{
				Hub.renderer.drawTexture(this);
				Hub.renderer.drawGraphicElement(
						new VisualBundle(x,y,width,height,angle,vertexNumber,vertexBuffer));

				if(Hub.renderer.animate){
					view.animate();
				}
				onDraw();
			}
		}
	}
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public void rotate(float r){
		angle = r;
	}
	public boolean isVisible(){
		return isVisible;
	}
	public void setWidth(float w){
		width = w;
	}
	public void setHeight(float h){
		height = h;		
	}

	public float getHeight(){
		return height;
	}
	public float getWidth(){
		return width;
	}

	public int getFrame(){
		return frame ;
	}
	public void setFrame(int frame) {
		this.frame = frame;
	}

	public boolean isWithin(float dx, float dy) {
		if(width<0){
			if(dx<=x+width||dx>x){
				return false;
			}
		}
		else if(dx<=x||dx>x+width){
			return false;
		}
		if(height<0){
			if(dy<=y+height||dy>y){
				return false;
			}
		}
		else if(dy<=y||dy>y+height){
			return false;
		}
		return true;
	}

	public void setVisible(boolean b) {
		this.isVisible = b;
	}
	
	public void on(boolean b) {
		this.on = b;
	}
	public int getLayer() {
		return layer;
	}
	public void setLayer(int layer){
		this.layer = layer;
	}
	public float getAngle() {
		return angle;
	}
	public void setShape(int shapeId){
		switch(shapeId){
		case GraphicElement.SHAPE_SQUARE:{
			vertexBuffer=squareBuffer;
			this.shapeId=shapeId;
			vertexNumber=4;
			break;
		}
		case GraphicElement.SHAPE_ISOSCELES_TRIANGLE:{
			vertexBuffer=triangleBuffer;
			this.shapeId=shapeId;
			vertexNumber=3;
			break;
		}
		case GraphicElement.SHAPE_TOP_LEFT_TRIANGLE:{
			vertexBuffer=tlTriangleBuffer;
			this.shapeId=shapeId;
			vertexNumber=3;
			break;
		}
		case GraphicElement.SHAPE_TOP_RIGHT_TRIANGLE:{
			vertexBuffer=trTriangleBuffer;
			this.shapeId=shapeId;
			vertexNumber=3;
			break;
		}
		case GraphicElement.SHAPE_BOTTOM_LEFT_TRIANGLE:{
			vertexBuffer=blTriangleBuffer;
			this.shapeId=shapeId;
			vertexNumber=3;
			break;
		}
		case GraphicElement.SHAPE_BOTTOM_RIGHT_TRIANGLE:{
			vertexBuffer=brTriangleBuffer;
			this.shapeId=shapeId;
			vertexNumber=3;
			break;
		}
		case GraphicElement.SHAPE_HEXAGON:{
			vertexBuffer=hexagonBuffer;
			vertexNumber=6;
			this.shapeId=shapeId;
			break;
		}
		}
	}
	public int getReflectedShape() {
		if(vertexBuffer==brTriangleBuffer){
			return 2;
		}
		else if(vertexBuffer==blTriangleBuffer){
			return 3;
		}
		else if(vertexBuffer==trTriangleBuffer){
			return 4;
		}
		else if(vertexBuffer==tlTriangleBuffer){
			return 5;
		}
		return -1;
	}
	public boolean isShape(int shapeId){
		return shapeId==this.shapeId;
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
}
