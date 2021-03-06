package com.rem.core.storage;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import com.rem.core.Action;

public class FileResource <Type> {
	
	public static final int INPUT_STREAM = 0;
	public static final int OUTPUT_STREAM = 1;
	public static final int FILE = 2;
	public static final int INTEGER = 3;
	public static final int BITMAP = 4;
	
	
	private String path;
	private int id;
	private int typeId;
	private Type res;
	private boolean exists = true;
	private Action<FileResource<Type>> getMethod;


	public FileResource(int textureId, String path,final Type res){
		this(textureId,path,
				(res instanceof InputStream)?INPUT_STREAM:
				(res instanceof OutputStream)?OUTPUT_STREAM:
				(res instanceof File)?FILE:
				(res instanceof Integer)?INTEGER:
				("Bitmap".equals(res.getClass().getSimpleName()))?BITMAP:
					-1,				
				new Action<FileResource<Type>>(){

			@Override
			public void act(FileResource<Type> subject) {
				subject.res = res;
			}});
	}
	public FileResource(int id, String path,int type, Action<FileResource<Type>> getMethod){
		this.typeId = type;
		this.path = path;
		this.id = id;
		this.getMethod = getMethod;
	}
	
	public Type get(){
		getMethod.act(this);
		return res;
	}
	public int type(){
		return typeId;
	}
	public String getPath(){
		return path;
	}
	public int getId(){
		return id;
	}
	
	public void setExists(boolean exists){
		this.exists = exists;
	}

	public boolean exists() {
		return exists;
	}
	public void set(Type res){
		this.res = res;
	}
}
