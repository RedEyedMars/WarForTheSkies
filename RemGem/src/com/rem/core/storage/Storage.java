package com.rem.core.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rem.core.Hub;
import com.rem.core.IFileManager;
import com.rem.core.environment.Environment;

public class Storage {

	public static boolean debug_save = false;
	public static boolean debug_load = false;
	public static boolean debug_load_raw = false;

	public static String loadMap(FileResource<InputStream> input){

		if(input.getPath().equals("Restart")){
			if(Storage.getFileName(Hub.map)==null){
				return null;
			}
			else {
				input = Hub.manager.createInputStream(Hub.map.getFileName(),IFileManager.ABSOLUTE);
			}
		}
		String mapName = getMapNameFromFileName(input.getPath());
		InputStream reader = input.get();
		if(Storage.debug_load)Hub.log.debug("Storage.loadMap", input.getPath());
		if(input.exists()){

			if(Storage.debug_load)Hub.log.debug("Storage.loadMap", "Exists");
			byte[] file = readVerbatum(reader);
			loadMap(mapName,input.getPath(),file);
		}
		else {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Hub.map = Hub.creator.createPlaceHolderEnvironment();
		}
		return mapName;
	}
	public static String getFileName(Environment map) {
		return map.getFileName();
	}
	public static String getMapNameFromFileName(String filename){
		if(filename.trim().equals("Restart")){
			return filename;
		}
		int lastSeparator = filename.lastIndexOf('\\');
		if(lastSeparator==-1){
			lastSeparator = filename.lastIndexOf('/');
			if(lastSeparator==-1){
				lastSeparator=0;
			}
			else {
				++lastSeparator;
			}
		}
		else {
			++lastSeparator;
		}
		int dot = filename.lastIndexOf('.');
		String mapName = "";
		if(dot==-1){
			mapName = filename.substring(lastSeparator);
		}
		else {
			mapName = filename.substring(lastSeparator, dot);
		}
		return mapName;
	}

	public static void loadMap(String name,String filename, byte[] file){
		int index = 0;
		for(;(char)file[index]!='\n';++index);
		Map<Integer,String> strings = loadStringMap(file);
		DataPresenter header = new DataPresenter();
		header.decodeHeader(file, index+1);
		++index;
		for(;(char)file[index]!='\n';++index);
		DataPresenter loadedData = new DataPresenter();
		loadedData.decode(file, index+1, strings, header);
		
		Hub.map = Hub.creator.createPlaceHolderEnvironment();
		Hub.map.setNameAndFileName(name,filename);
		Hub.map.load(loadedData);
	}

	private static Map<Integer,String> loadStringMap(byte[] file) {
		Map<Integer,String> strings = new HashMap<Integer,String>();
		if(file[0]=='\n')return strings;
		int next = 0;		
		for(int i=1;i<file.length;i=next+1){
			next=i;
			while(file[next]!='\t'&&file[next]!='\n'){
				++next;
			}
			strings.put(strings.size(), readStringFromBytes(file,i,next));
			if(file[next]=='\n'){				
				return strings;
			}
		}
		return strings;
	}

	private static String readStringFromBytes(byte[] file, int start, int end){
		StringBuilder builder = new StringBuilder();
		for(;start<end;++start){
			builder.append(((char)(byte)file[start]));
		}
		return builder.toString();
	}

	public static byte[] readVerbatum(InputStream reader){
		List<Byte> builder = new ArrayList<Byte>();
		try {
			int next = reader.read();
			while(((int)next)>=0){

				if(Storage.debug_load_raw)Hub.log.bufferDebug("Storage.loadMap", next+",");
				builder.add((byte)next);
				next = reader.read();
			}
			if(Storage.debug_load_raw)Hub.log.debug("Storage.loadMap", "");
			reader.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		byte[] bytes = new byte[builder.size()];
		for(int i=0;i<builder.size();++i){
			bytes[i]=builder.get(i);
		}
		return bytes;
	}



	public static String loadText(FileResource<InputStream> saveTo) {
		StringBuilder builder = new StringBuilder();
		for(Byte b:readVerbatum(saveTo.get())){
			builder.append((char)(int)b);
		}
		return builder.toString();
	}

	public static void saveText(FileResource<OutputStream> resource, String text) {
		createFolder("data");
		OutputStream writer = resource.get();
		try {
			byte[] bytes = new byte[text.length()];
			char[] chars = text.toCharArray();
			for(int i=0;i<chars.length;++i){
				bytes[i] = (byte) chars[i];
			}
			writer.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(writer!=null){
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void saveMap(FileResource<OutputStream> resource, Environment map) {
		DataCollector container = new DataCollector();
		map.save(container);
		if(Storage.debug_load)Hub.log.debug("Storage.saveMap","");
		save(resource,container);
	}
	public static void save(FileResource<OutputStream> resource, DataCollector toSave) {
		createFolder("data");
		OutputStream writer = resource.get();
		try {
			List<Byte> build = new ArrayList<Byte>();
			for(String string:toSave){
				build.add((byte) '\t');
				for(char b:string.toCharArray()){
					build.add((byte)b);
				}
				
			}

			build.add((byte)'\n');
			toSave.getHeader().encode(build);
			if(Storage.debug_load)Hub.log.debug("Storage.saveMap","");

			build.add((byte)'\n');
			toSave.encode(build);
			for(int i=0;i<build.size();++i){
				writer.write(build.get(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(writer!=null){
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void createFolder(String folderName){
		StringBuilder path = new StringBuilder();
		path.append("./");
		path.append(folderName);
		File f = new File(path.toString());
		if(!f.exists()){
			f.mkdirs();
		}
	}
	
}
