package com.rem.wfs.environment.resource.ship;

import java.io.IOException;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.DataPresenter;
import com.rem.core.storage.StorageHandler;
import com.rem.wfs.environment.Statistic;
import com.rem.wfs.graphics.Meter;
import com.rem.wfs.graphics.icons.Iconic;

public abstract class ShipStatistic implements StorageHandler, Statistic {

	private int value;
	private int capacity;
	private Meter meter;
	@Override
	public void load(DataPresenter data) throws IOException {
		value = data.nextInteger();
		capacity = data.nextInteger();
	}
	@Override
	public void save(DataCollector toSave) throws IOException {
		toSave.collect(value);
		toSave.collect(capacity);
	}
	
	public void setCapacity(int cap) {
		this.capacity = cap;
	}
	public int add(int amount){
		if(this.value+amount<this.capacity){
			this.value += amount;
			return 0;
		}
		else {
			int needed = this.capacity-this.value;
			this.value = this.capacity;
			return amount-needed;
		}
	}
	@Override
	public float getValue() {
		return value;
	}
	@Override
	public float getLimit() {
		return capacity;
	}
	public Iconic getMeter(){
		if(meter==null){
			meter = createMeter();
		}
		return meter;
	}
	
	protected abstract Meter createMeter();
	
}
