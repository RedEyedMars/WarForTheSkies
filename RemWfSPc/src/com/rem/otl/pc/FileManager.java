package com.rem.otl.pc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.rem.core.Hub;
import com.rem.core.IFileManager;
import com.rem.core.storage.FileResource;
import com.rem.core.storage.Storage;
import com.rem.core.Action;
import com.rem.otl.pc.gui.graphics.R;

public class FileManager implements IFileManager{
	@SuppressWarnings("rawtypes")
	@Override
	public FileResource createImageResource(String name, String path) {
		return new FileResource<InputStream>(name,path, R.getResource(path));
	}

	@Override
	public FileResource<InputStream> createInputStream(String path,final int pathType) {
		if(path==null)return null;
		else {
			if(pathType==IFileManager.RELATIVE){
				path = new File(System.getenv("APPDATA"),"WarForTheSkies"+File.separator+path).getAbsolutePath();
			}
			return new FileResource<InputStream>(
					Storage.getMapNameFromFileName(path),
					path,
					FileResource.INPUT_STREAM,
					new Action<FileResource<InputStream>>(){
						@Override
						public void act(FileResource<InputStream> resource){
							if(pathType==IFileManager.FROM_IMAGE_RESOURCE){
								InputStream is = R.getResource(resource.getPath());
								resource.set(is);
								return;
							}
							try {
								resource.set(new FileInputStream(/*new File("").getAbsolutePath()+File.separator+*/resource.getPath()));
							} catch (FileNotFoundException e) {
								File file = new File(resource.getPath());
								try {
									System.out.println(resource.getPath());
									if(	file.createNewFile() ){
										resource.set(new FileInputStream(resource.getPath()));
										resource.setExists(false);
									}
								} catch (IOException e1) {
									Hub.log.debug("FileManager.createInputStream", resource.getPath());
									e1.printStackTrace();
								}
							}
						}
					});


		}
	}
	@Override
	public FileResource<OutputStream> createOutputStream(String path, int pathType) {
		if(path==null)return null;
		if(pathType==IFileManager.RELATIVE){
			path = new File(System.getenv("APPDATA"),"WarForTheSkies"+File.separator+path).getAbsolutePath();
		}
		return new FileResource<OutputStream>(
				Storage.getMapNameFromFileName(path),
				path,
				FileResource.OUTPUT_STREAM,
				new Action<FileResource<OutputStream>>(){
					@Override
					public void act(FileResource<OutputStream> resource){
						try {
							resource.set(new FileOutputStream(resource.getPath()));
						} catch (FileNotFoundException e) {
							File file = new File(resource.getPath());
							try {
								if(	file.createNewFile() ){
									resource.set(new FileOutputStream(resource.getPath()));
									resource.setExists(false);
								}
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
				});
	}

	@Override
	public boolean deleteFile(String path){
		File file = new File(path);
		if(file.exists()){
			return file.delete();
		}
		return false;
	}

	@Override
	public void createDirectory(String path){
		File file = new File(path);
		if(!file.exists()){
			file.mkdir();
		}
	}

	@Override
	public File getDirectory(String path, int pathType) {
		if(pathType==IFileManager.RELATIVE){
			return new File(System.getenv("APPDATA"),"WarForTheSkies"+File.separator + path);
		}
		else if(pathType==IFileManager.ABSOLUTE){
			return new File(path);
		}
		else return null;
	}

}
