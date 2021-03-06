package com.rem.core.gui.music;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rem.core.Hub;

public class Library {
	/*
	public static Track living_nightmare = new Track("living_nightmare.mp3",
			"Living Nightmare - snowflake",
			"Living Nightmare by snowflake (c) copyright 2016 Licensed under a Creative Commons Attribution (3.0) license. http://dig.ccmixter.org/files/snowflake/54422 Ft: Blue Wave Theory");*/
	public static final List<ITrack> tracks = new ArrayList<ITrack>();
	public static final List<String> licenses = new ArrayList<String>();

	static {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("res"+File.separatorChar+"music"+File.separatorChar+"catalogue.data")));

			String currentLicense = "";
			String currentTrackName = "";
			ITrack currentTrack = null;
			String line = reader.readLine();
			while(line!=null){
				if(line.startsWith("#")){
					
				}
				else if(line.startsWith("\t\t")){
					if(currentTrack!=null){
						if(currentTrack.getHttpLink()==null){
							currentTrack.setHttpLink(line.substring(2));
						}
						else {
							currentTrack.setFeature(line.substring(2));
						}
					}
					else {
						String[] split = currentTrackName.split(" by ");
						currentTrack = Hub.creator.createTrack(
								line.substring(2),
								split[0],
								split[1],
								currentLicense);
						tracks.add(currentTrack);
					}
				}
				else if(line.startsWith("\t")){
					currentTrack = null;
					currentTrackName = line.substring(1);
				}
				else {
					currentLicense = line;
					licenses.add(currentLicense);
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
