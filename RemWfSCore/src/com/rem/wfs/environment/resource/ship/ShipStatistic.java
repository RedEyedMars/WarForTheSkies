package com.rem.wfs.environment.resource.ship;

import java.io.IOException;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.StorageHandler;
import com.rem.wfs.environment.Statistic;
import com.rem.wfs.graphics.Meter;
import com.rem.wfs.graphics.icons.Iconic;

public abstract class ShipStatistic implements StorageHandler, Statistic {

	private float value;
	private int capacity;
	private Meter meter;
	@Override
	public void collect(DataCollector data) throws IOException {
		value = data.collect(value);
		capacity = data.collect(capacity);
	}

	public void setCapacity(int cap) {
		this.capacity = cap;
	}
	public float add(float amount){
		if(this.value+amount<this.capacity){
			this.value += amount;
			return 0f;
		}
		else {
			float needed = this.capacity-this.value;
			this.value = this.capacity;
			return amount-needed;
		}
	}
	@Override
	public float getValue() {
		return value;
	}
	@Override
	public int getLimit() {
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
