package com.rem.core;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import com.rem.core.storage.FileResource;

public interface IFileManager {

	
	public static final int RELATIVE = 0;
	public static final int ABSOLUTE = 1;
	public static final int FROM_IMAGE_RESOURCE = 2;
	public FileResource<InputStream> createInputStream(String path, int pathType);
	public FileResource<OutputStream> createOutputStream(String path, int pathType);
	@SuppressWarnings("rawtypes")
	public FileResource createImageResource(String name, String path);
	public boolean deleteFile(String string);
	public void createDirectory(String string);
	public File getDirectory(String path, int pathType);
	public Iterator<String> getFileNames(String string, int pathType);
}
