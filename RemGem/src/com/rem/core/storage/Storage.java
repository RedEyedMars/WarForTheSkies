package com.rem.core.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
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
			loadMap(mapName,input.getPath(),reader);
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

	public static void loadMap(String name,String filename, InputStream input){
		DataDecoder dataLoader = new DataDecoder(input);
		
		Hub.map = Hub.creator.createPlaceHolderEnvironment();
		Hub.map.setNameAndFileName(name,filename);
		try {
			Hub.map.collect(dataLoader);
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public static void save(FileResource<OutputStream> resource, StorageHandler target) {
		createFolder("data");
		OutputStream writer = resource.get();
		try {
			DataCollector toSave = new DataEncoder(writer);
			target.collect(toSave);
			if(Storage.debug_load)Hub.log.debug("Storage.saveMap","");
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
