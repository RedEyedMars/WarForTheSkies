package com.rem.core.gui.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import com.rem.core.gui.graphics.elements.GraphicElement;

public abstract class ShapeHandler {

	
	
	public static final ShapeHandler square = new ShapeHandler(GraphicElement.SHAPE_SQUARE){

		@Override
		protected float[] getBufferArray() {
			return new float[]{
					0f, 0f,  0.0f,		// V1 - bottom left
					0f,  1f,  0.0f,		// V2 - top left
					1f, 0f,  0.0f,		// V3 - bottom right
					1f,  1f,  0.0f,		// V4 - top right
			};
		}
		
	};
	
	public static final ShapeHandler iso_triangle = new ShapeHandler(GraphicElement.SHAPE_ISOSCELES_TRIANGLE){
		@Override
		protected float[] getBufferArray() {
			return new float[]{
					0f, 0f,  0.0f,		// V1 - bottom left
					0.5f,  1f,  0.0f,		// V2 - top left
					1f, 0f,  0.0f,		// V3 - bottom right
			};
		}		
	};
	
	public static final ShapeHandler top_left_triangle = new ShapeHandler(GraphicElement.SHAPE_TOP_LEFT_TRIANGLE){
		@Override
		protected float[] getBufferArray() {
			return  new float[]{
					0f, 0f,  0.0f,		// V1 - bottom left
					0f,  1f,  0.0f,		// V2 - top left
					1f,  1f,  0.0f,		// V4 - top right
			};
		}
		@Override
		public int getReflectedShape() {
			return GraphicElement.SHAPE_BOTTOM_LEFT_TRIANGLE;
		}
	};
	
	public static final ShapeHandler top_right_triangle = new ShapeHandler(GraphicElement.SHAPE_TOP_RIGHT_TRIANGLE){
		@Override
		protected float[] getBufferArray() {
			return  new float[]{
					0f, 1f,  0.0f,		// V1 - bottom left
					1f,  1f,  0.0f,		// V4 - top right
					1f, 0f,  0.0f,		// V3 - bottom right
			};
		}
		@Override
		public int getReflectedShape() {
			return GraphicElement.SHAPE_BOTTOM_RIGHT_TRIANGLE;
		}
	};
	public static final ShapeHandler bot_left_triangle = new ShapeHandler(GraphicElement.SHAPE_BOTTOM_LEFT_TRIANGLE){
		@Override
		protected float[] getBufferArray() {
			return  new float[]{
					0f, 0f,  0.0f,		// V1 - bottom left
					0f,  1f,  0.0f,		// V2 - top left
					1f,  0f,  0.0f,		// V4 - top right
			};
		}
		@Override
		public int getReflectedShape() {
			return GraphicElement.SHAPE_TOP_LEFT_TRIANGLE;
		}
	};
	public static final ShapeHandler bot_right_triangle = new ShapeHandler(GraphicElement.SHAPE_BOTTOM_RIGHT_TRIANGLE){
		@Override
		protected float[] getBufferArray() {
			return new float[]{
					0f, 0f,  0.0f,
					1f, 1f,  0.0f,
					1f,  0f,  0.0f,

			};
		}
		@Override
		public int getReflectedShape() {
			return GraphicElement.SHAPE_TOP_RIGHT_TRIANGLE;
		}
	};
	public static final ShapeHandler hexagon = new ShapeHandler(GraphicElement.SHAPE_HEXAGON){
		@Override
		protected float[] getBufferArray() {
			return new float[]{
					0f,  0.2f,  0.0f,
					0f,0.8f,  0.0f,
					0.5f,0f,  0.0f,
					0.5f,1f,  0.0f,
					1f,0.2f,  0.0f,
					1f,  0.8f,  0.0f
			};
		}
	};

	private int id;
	private int numberOfVertices;
	protected FloatBuffer buffer;
	protected Map<Integer,FloatBuffer[]> textureBuffers = new HashMap<Integer,FloatBuffer[]>();
	public ShapeHandler(int id) {
		this.id = id;
		float[] vertices = getBufferArray();

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		buffer = byteBuffer.asFloatBuffer();
		buffer.clear();
		buffer.put(vertices);
		buffer.position(0);
		
		numberOfVertices = vertices.length/3;
	}
	public void setVisual(VisualBundle bundle) {
		bundle.setShapeInfo(numberOfVertices,buffer);
	}

	public int getReflectedShape() {
		return -1;
	}
	public boolean is(int shapeId) {
		return this.id==shapeId;
	}
	protected abstract float[] getBufferArray();
	public void addTextureBuffer(int key, FloatBuffer[] value) {
		this.textureBuffers.put(key, value);
	}
	public boolean containsTextureBuffer(int key) {
		return this.textureBuffers.containsKey(key);
	}
	public FloatBuffer[] getTextureBuffer(int key) {
		return textureBuffers.get(key);
	}
}
