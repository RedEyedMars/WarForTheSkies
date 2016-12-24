package com.rem.wfs.pc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.rem.core.Hub;
import com.rem.core.IFileManager;
import com.rem.core.storage.FileResource;
import com.rem.wfs.pc.gui.graphics.R;
import com.rem.core.Action;

public class FileManager implements IFileManager{
	@SuppressWarnings("rawtypes")
	@Override
	public FileResource createImageResource(int id, String path) {
		return new FileResource<InputStream>(id,path, R.getResource(path));
	}

	@Override
	public FileResource<InputStream> createInputStream(String path,final int pathType) {
		if(path==null)return null;
		else {
			if(pathType==IFileManager.RELATIVE){
				path = new File(System.getenv("APPDATA"),"WarForTheSkies"+File.separator+path).getAbsolutePath();
			}
			return new FileResource<InputStream>(
					-1,
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
				-1,
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
								System.out.println(resource.getPath());
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
	public boolean deleteFile(String path, int pathType){
		File file = null;
		if(pathType==IFileManager.ABSOLUTE){
			file = new File(path);
		}
		else if(pathType == IFileManager.RELATIVE){
			file = new File(System.getenv("APPDATA"),"WarForTheSkies"+File.separator+path);
		}
		if(file!=null&&file.exists()){
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
		else if(pathType==IFileManager.FROM_IMAGE_RESOURCE){
			return R.getFile(path);
		}
		else return null;
	}

	@Override
	public Iterator<String> getFileNames(String path, int pathType) {
		File file = getDirectory(path,pathType);
		if(!file.exists()){
			try {
				file = new File(R.class.getProtectionDomain().getCodeSource().getLocation().getPath());
				JarFile jarFile = new JarFile(file);
				Enumeration<JarEntry> files = jarFile.entries();
				List<String> fileNames = new ArrayList<String>();
				path = "com/rem/wfs/pc/gui/graphics/"+path.replace(File.separator, "/")+"/";
				while(files.hasMoreElements()){
					String name = files.nextElement().getName();
					if(name.startsWith(path)&&!name.endsWith("/")){
						fileNames.add(name.replace(path, ""));
					}
				}
				return fileNames.iterator();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		else {
			file = getDirectory(path,pathType);
			List<String> fileNames = new ArrayList<String>();
			for(File dir:file.listFiles()){
				addFileNames("",dir,fileNames);
			}
			return fileNames.iterator();
		}
	}

	private void addFileNames(String path, File currentDirectory, List<String> fileNames){
		if(currentDirectory.isDirectory()){
			path = path + currentDirectory.getName() + "/";
			for(File file:currentDirectory.listFiles()){
				addFileNames(path,file,fileNames);
			}
		}
		else if(currentDirectory.isFile()){
			fileNames.add(path+currentDirectory.getName());
		}
	}
}
