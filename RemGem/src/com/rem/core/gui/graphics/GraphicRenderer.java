package com.rem.core.gui.graphics;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.rem.core.Action;
import com.rem.core.Hub;
import com.rem.core.IFileManager;
import com.rem.core.storage.FileResource;



public abstract class GraphicRenderer {

	protected float viewX;

	protected float viewY;

	protected float viewZ=0f;

	protected LinkedList<GraphicElementArrayChangeEvent> changeEvents = new LinkedList<GraphicElementArrayChangeEvent>();
	protected List<GraphicElement> drawBotLayer = new ArrayList<GraphicElement>();
	protected List<GraphicElement> drawMidLayer = new ArrayList<GraphicElement>();
	protected List<GraphicElement> drawTopLayer = new ArrayList<GraphicElement>();

	protected List<Action<Integer>> loadImage = new ArrayList<Action<Integer>>();
	protected Map<String, Integer> nameMap = new HashMap<String,Integer>(); 
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
	protected String[] sizMap = new String[128];
	protected Map<String,FloatBuffer[]> squareTextureBuffers = new HashMap<String,FloatBuffer[]>();
	protected Map<String,FloatBuffer[]> hexagonTextureBuffers = new HashMap<String,FloatBuffer[]>();

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
		squareTextureBuffers.put(xMax+"x"+yMax, squareTextureBuffer);
		hexagonTextureBuffers.put(xMax+"x"+yMax, hexagonTextureBuffer);

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

		for(int i=0;i<drawBotLayer.size();++i){
			drawBotLayer.get(i).draw();
		}
		for(int i=0;i<drawMidLayer.size();++i){
			drawMidLayer.get(i).draw();
		}
		for(int i=0;i<drawTopLayer.size();++i){
			drawTopLayer.get(i).draw();
		}

		cleanupRenderCycle();				
	}

	protected abstract void setupRenderCycle();
	protected abstract void cleanupRenderCycle();
	protected abstract void bindTexture(GraphicElement d);
	protected abstract void createFont(int texId, String texName, String fontName, int fontStyle,int size, float[] foreGroundColour, float[] backgroundColour);

	public abstract void drawGraphicElement(VisualBundle visualBundle);

	public void drawTexture(GraphicElement d){
		bindTexture(d);
	}

	public boolean buffersInclude(int sizeX, Integer sizeY) {
		return squareTextureBuffers.containsKey(sizeX+"x"+sizeY);
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
					final String imageName = fileName.substring(fileName.indexOf('/')+1,fileName.lastIndexOf('.'));
					final Integer sizeX = currentSizeX;
					final Integer sizeY = currentSizeY;
					final String imageFilename = ("images/"+fileName);

					setupTexture(imageName);

					loadImage.add(new Action<Integer>(){
						@Override
						public void act(Integer event) {
							loadImageFromPath(imageName,imageFilename,sizeX,sizeY);
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
			}
		}
	}

	private Integer setupTexture(String textureName) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException{
		int currentId = nameMap.size();
		Hub.r.getClass().getField(textureName).set(Hub.r.getClass(), currentId);
		nameMap.put(textureName, currentId);
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
		int id = setupTexture(texName);
		letterWidths.put(id, new ArrayList<Float>());
		createFont(id,texName, fontName, fontStyle, size, foregroundColour, backgroundColour);
	}

	public void loadImageFromPath(String imageName, String path, int sizeX, Integer sizeY){
		if(!buffersInclude(sizeX,sizeY)){
			setupTextureBuffer(sizeX,sizeY);			
		}
		int tex = Hub.gui.createTexture(Hub.manager.createImageResource(imageName, path));

		addTexture(imageName, tex, sizeX+"x"+sizeY);
	}
	public void loadImageFromExternalPath(String path, int sizeX, Integer sizeY, String name){
		if(!buffersInclude(sizeX,sizeY)){			
			setupTextureBuffer(sizeX,sizeY);			
		}
		int tex = Hub.gui.createTexture(new FileResource<File>(name,path,new File(path)));
		addTexture(name,tex, sizeX+"x"+sizeY);
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
		return sizMap[textureId].length();
	}

	protected void addTexture(String name, int texture, String size){

		texMap[nameMap.get(name)] = texture;
		sizMap[nameMap.get(name)] = size;
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
			if(element.getLayer()==Hub.TOP_LAYER){
				drawTopLayer.add(element);
			}
			else if(element.getLayer()==Hub.MID_LAYER){
				drawMidLayer.add(element);
			}
			else if(element.getLayer()==Hub.BOT_LAYER){
				drawBotLayer.add(element);
			}
			if(texMap[element.getTexture()]==-1){
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
			if(element.getLayer()==Hub.TOP_LAYER){
				drawTopLayer.remove(element);
			}
			else if(element.getLayer()==Hub.MID_LAYER){
				drawMidLayer.remove(element);
			}
			else if(element.getLayer()==Hub.BOT_LAYER){
				drawBotLayer.remove(element);
			}
		}
	}
}
