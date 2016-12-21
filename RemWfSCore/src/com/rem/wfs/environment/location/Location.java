package com.rem.wfs.environment.location;

import java.io.IOException;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.StorageHandler;

public class Location implements StorageHandler{
	private int x;
	private int y;
	public Location(int x, int y){
		this.x = x;
		this.y = y;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	@Override
	public void load(DataPresenter data) throws IOException {
		this.x = data.nextInteger();
		this.y = data.nextInteger();
	}
	@Override
	public void save(DataCollector data) throws IOException {
		data.collect(x);
		data.collect(y);
	}
}
