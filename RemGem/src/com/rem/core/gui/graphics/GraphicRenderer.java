package com.rem.core.gui.graphics;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.rem.core.Action;
import com.rem.core.Hub;
import com.rem.core.IFileManager;
import com.rem.core.gui.graphics.elements.GraphicElement;
import com.rem.core.gui.graphics.elements.dimension.DimensionHandler;
import com.rem.core.storage.FileResource;



public abstract class GraphicRenderer {

	public static boolean debug_load = false;

	private static int textureSetupIndex = 0;

	protected float viewX;
	protected float viewY;
	protected float viewZ=0f;

	protected int currentDrawMode = R.DRAW_MODE_VERTICES;

	protected LinkedList<GraphicElementArrayChangeEvent> changeEvents = new LinkedList<GraphicElementArrayChangeEvent>();
	protected List<GraphicElement> botLayer = new ArrayList<GraphicElement>();
	protected List<GraphicElement> midLayer = new ArrayList<GraphicElement>();
	protected List<GraphicElement> topLayer = new ArrayList<GraphicElement>();

	protected List<Action<Integer>> loadImage = new ArrayList<Action<Integer>>();
	protected int[] texMap = new int[]{
			-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,


			-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1,
			-1,-1,-1,-1,-1,-1,-1,-1
	};
	protected int[] sizMap = new int[128];

	protected List<String> toLoadtext = new ArrayList<String>();


	public boolean animate;
	protected long last = System.currentTimeMillis();
	private boolean loaded = false;

	public long animationInterval = 100;

	public GraphicRenderer() {
	}
	public void setupTextureBuffer(int xMax, int yMax) {
		float length = (float)xMax;
		float height = (float)yMax;
		float xFactor = 0.02f/xMax;
		float yFactor = 0.02f/yMax;
		FloatBuffer[] squareTextureBuffer = new FloatBuffer[xMax*yMax];
		FloatBuffer[] hexagonTextureBuffer = new FloatBuffer[xMax*yMax];
		float textures[];
		for(int y=0;y<yMax;++y){
			for(int x=0;x<xMax;++x){
				textures= new float[]{
						// Mapping coordinates for the vertices
						x/length+xFactor, (y+1)/height-yFactor,		// top left		(V2)
						x/length+xFactor, (y)/height+yFactor,		// bottom left	(V1)
						(x+1)/length-xFactor, (y+1)/height-yFactor,		// top right	(V4)
						(x+1)/length-xFactor, (y)/height+yFactor		// bottom right	(V3)
				};
				//builder.append((x)/length+","+(y)/height+"|");

				ByteBuffer byteBuffer = ByteBuffer.allocateDirect(2 * 4 * 4);
				byteBuffer.order(ByteOrder.nativeOrder());
				squareTextureBuffer[y*xMax+x] = byteBuffer.asFloatBuffer();
				squareTextureBuffer[y*xMax+x].put(textures);
				squareTextureBuffer[y*xMax+x].position(0);

				textures= new float[]{
						// Mapping coordinates for the vertices
						x/length, (y+0.5f)/height,		// top left		(V2)
						(x+1f/3f)/length, (y)/height,		// top left		(V2)
						(x+1f/3f)/length, (y+1)/height,		// bottom left	(V1)
						(x+2/3)/length, (y)/height,		// top right	(V4)
						(x+2/3f)/length, (y+1)/height,		// bottom right	(V3)
						(x+1)/length, (y+0.5f)/height		// bottom right	(V3)
				};	
				byteBuffer = ByteBuffer.allocateDirect(2 * 4 * 6);
				byteBuffer.order(ByteOrder.nativeOrder());
				hexagonTextureBuffer[y*xMax+x] = byteBuffer.asFloatBuffer();
				hexagonTextureBuffer[y*xMax+x].put(textures);
				hexagonTextureBuffer[y*xMax+x].position(0);
			}
		}
		ShapeHandler.square.addTextureBuffer(xMax+yMax*1000, squareTextureBuffer);
		ShapeHandler.hexagon.addTextureBuffer(xMax+yMax*1000, hexagonTextureBuffer);

		//Hub.log.debug("graphicRenderer:"+xMax+"x"+yMax, builder.toString());
	}
	public boolean isLoaded(){
		return this.loaded;
	}
	public void display(){

		while(!changeEvents.isEmpty()){
			synchronized(changeEvents){
				changeEvents.removeFirst().act();
			}
		}

		setupRenderCycle();

		if(animate){
			drawLayerWithAnimate(botLayer);
			drawLayerWithAnimate(midLayer);
			drawLayerWithAnimate(topLayer);
		}
		else {
			drawLayer(botLayer);
			drawLayer(midLayer);
			drawLayer(topLayer);
		}

		cleanupRenderCycle();				
	}
	private void drawLayer(List<GraphicElement> layer){
		VisualBundle bundle = new VisualBundle();
		for(int i=0;i<layer.size();++i){
			if(layer.get(i).draw(bundle)){
				drawTexture(layer.get(i));
				drawVisual(bundle);
			}
		}
	}
	private void drawLayerWithAnimate(List<GraphicElement> layer){
		VisualBundle bundle = new VisualBundle();
		for(int i=0;i<layer.size();++i){
			if(layer.get(i).draw(bundle)){
				if(currentDrawMode!=layer.get(i).getDrawMode()){
					changeDrawMode(currentDrawMode,layer.get(i).getDrawMode());
					currentDrawMode = layer.get(i).getDrawMode();
				}
				drawTexture(layer.get(i));
				drawVisual(bundle);
			}
			layer.get(i).animate();
		}
	}

	protected abstract void setupRenderCycle();
	protected abstract void cleanupRenderCycle();
	protected abstract void bindTexture(GraphicElement d);
	protected abstract void createFont(int texId, String fontName, int fontStyle,int size, float[] foreGroundColour, float[] backgroundColour);

	public abstract void drawVisual(VisualBundle visualBundle);
	public abstract void changeDrawMode(int oldMode, int newMode);

	public void drawTexture(GraphicElement d){
		bindTexture(d);
	}

	public boolean buffersInclude(int sizeX, Integer sizeY) {
		return ShapeHandler.square.containsTextureBuffer(sizeX+sizeY*1000);
	}

	public void addElement(GraphicElement e){
		synchronized(changeEvents){
			changeEvents.add(new AddGraphicElementEvent(e));
		}
	}
	public void removeElement(GraphicElement e){
		synchronized(changeEvents){
			changeEvents.add(new RemoveGraphicElementEvent(e));
		}
	}

	public void clearAdditions(){

		synchronized(changeEvents){
			for(int i=0;i<changeEvents.size();++i){
				if(changeEvents.get(i) instanceof AddGraphicElementEvent){
					changeEvents.remove(i);
					--i;
				}
			}
		}
	}
	public void loadImages(){
		if(!loaded ){
			try {
				loadImage.add(new Action<Integer>(){
					@Override
					public void act(Integer event) {
					}				
				});loadImage.add(new Action<Integer>(){
					@Override
					public void act(Integer event) {
					}				
				});
				loadText("arial","Arial Black", Hub.creator.getPlainFontStyle(),16, new float[]{0f,0f,0f,1}, new float[]{0,0,0,0f});
				loadText("impact","Cooper Black", Hub.creator.getPlainFontStyle(), 32,new float[]{0f,0f,0f,1}, new float[]{0,0,0,0f});

				Iterator<String> fileNames = Hub.manager.getFileNames("images",IFileManager.FROM_IMAGE_RESOURCE);
				int currentSizeX = 0;
				int currentSizeY = 0;
				while(fileNames.hasNext()){
					final String fileName = fileNames.next();
					if(fileName.matches("\\d+/.*")){
						currentSizeX = Integer.parseInt(fileName.substring(0,fileName.indexOf('/')));
						currentSizeY = 1;
					}
					else if(fileName.matches("\\d+x\\d+/.*")){
						currentSizeX = Integer.parseInt(fileName.substring(0,fileName.indexOf('x')));
						currentSizeY = Integer.parseInt(fileName.substring(fileName.indexOf('x')+1,fileName.indexOf('/')));
					}

					final Integer sizeX = currentSizeX;
					final Integer sizeY = currentSizeY;
					final String imageFilename = ("images/"+fileName);

					String imageName = fileName.substring(fileName.indexOf('/')+1,fileName.lastIndexOf('.'));

					int[] pixelDimensions = getPixelDimensions(imageFilename);
					if(GraphicRenderer.debug_load)Hub.log.debug("GraphicRenderer.loadImages", imageFilename+":"+pixelDimensions[0]+"x"+pixelDimensions[1]);
					final int textureId = setupTexture(imageName,pixelDimensions[0]/sizeX,pixelDimensions[1]/sizeY);

					loadImage.add(new Action<Integer>(){
						@Override
						public void act(Integer event) {
							loadImageFromPath(imageFilename,textureId,sizeX,sizeY);
						}				
					});

				}
				//loadText("impactWhite","Cooper Black", Hub.creator.getPlainFontStyle(), 32,new float[]{1f,1f,1f,1}, new float[]{0,0,0,0f});

				loaded = true;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private int[] getPixelDimensions(String imageFilename) throws IOException {
		ImageInputStream in = ImageIO.createImageInputStream(
				Hub.manager.createImageResource(-1, imageFilename).get());
		final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
		if (readers.hasNext()) {
			ImageReader reader = readers.next();
			try {
				reader.setInput(in);
				return new int[]{reader.getWidth(0), reader.getHeight(0)};
			} finally {
				reader.dispose();
			}
		}
		throw new RuntimeException("Could not find the Dimensions of file:"+ imageFilename);
	}
	private Integer setupTexture(String textureName, int pixelWidth, int pixelHeight) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException{
		int currentId = textureSetupIndex++;
		Hub.r.getClass().getField(textureName).set(Hub.r.getClass(), currentId);
		DimensionHandler.addTexture(currentId, pixelWidth, pixelHeight);
		return currentId;
	}

	/*public void prepareCustomLoader(final String imageName, String dimension) {

		if(!loadImage.contains(imageName)){
			final int sizeX = Integer.parseInt(dimension.substring(0,dimension.indexOf('x')));
			final int sizeY = Integer.parseInt(dimension.substring(dimension.indexOf('x')+1,dimension.length()));

			final String imageFilename = "res/images/"+imageName+".png";

			loadImage.put(imageName,new Action<ClickEvent>(){
				@Override
				public void act(ClickEvent event) {
					loadImageFromExternalPath(imageFilename,sizeX,sizeY,imageName);
				}
			});
		}
	}*/

	public Map<Integer,List<Float>> letterWidths= new HashMap<Integer,List<Float>>();
	private void loadText(String texName, String fontName, int fontStyle, int size, float[] foregroundColour, float[] backgroundColour) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException{
		if(!buffersInclude(16,16)){
			setupTextureBuffer(16,16);			
		}
		int id = setupTexture(texName,size,size);
		letterWidths.put(id, new ArrayList<Float>());
		createFont(id, fontName, fontStyle, size, foregroundColour, backgroundColour);
	}

	public void loadImageFromPath(String path, int textureId, int sizeX, Integer sizeY){
		if(!buffersInclude(sizeX,sizeY)){
			setupTextureBuffer(sizeX,sizeY);			
		}
		int tex = Hub.gui.createTexture(Hub.manager.createImageResource(textureId,path));

		addTexture(textureId, tex, sizeX, sizeY);
	}
	public void loadImageFromExternalPath(String path,int textureId, int sizeX, int sizeY){
		if(!buffersInclude(sizeX,sizeY)){			
			setupTextureBuffer(sizeX,sizeY);			
		}
		int tex = Hub.gui.createTexture(new FileResource<File>(textureId,path,new File(path)));
		addTexture(textureId,tex, sizeX,sizeY);
	}
	public void translate(float x, float y, float z){
		viewX+=x;
		viewY+=y;
		viewZ+=z;
	}
	public void translateTo(float x, float y){
		viewX=x;
		viewY=y;
	}

	public float getViewX() {
		return viewX;
	}
	public float getViewY() {
		return viewY;
	}

	public int getFrameLimit(int textureId) {
		return ShapeHandler.square.getTextureBuffer(sizMap[textureId]).length;
	}

	protected void addTexture(int textureId, int texture, int xMax, int yMax){

		texMap[textureId] = texture;
		sizMap[textureId] = xMax+1000*yMax;
	}

	private interface GraphicElementArrayChangeEvent {
		public void act();
	}
	private class AddGraphicElementEvent implements GraphicElementArrayChangeEvent{
		private GraphicElement element;

		public AddGraphicElementEvent(GraphicElement element){
			this.element = element;
		}

		@Override
		public void act() {
			if(element.getLayer()==R.TOP_LAYER){
				topLayer.add(element);
			}
			else if(element.getLayer()==R.MID_LAYER){
				midLayer.add(element);
			}
			else if(element.getLayer()==R.BOT_LAYER){
				botLayer.add(element);
			}
			if(element.getTexture()!=-1&&texMap[element.getTexture()]==-1){
				loadImage.get(element.getTexture()).act(null);
			}
		}
	}
	private class RemoveGraphicElementEvent implements GraphicElementArrayChangeEvent{
		private GraphicElement element;

		public RemoveGraphicElementEvent(GraphicElement element){
			this.element = element;
		}

		@Override
		public void act() {
			if(element==null)return;
			if(element.getLayer()==R.TOP_LAYER){
				topLayer.remove(element);
			}
			else if(element.getLayer()==R.MID_LAYER){
				midLayer.remove(element);
			}
			else if(element.getLayer()==R.BOT_LAYER){
				botLayer.remove(element);
			}
		}
	}
}
