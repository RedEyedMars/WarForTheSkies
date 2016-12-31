package com.rem.wfs.environment.resource.ship.stations;

import java.io.IOException;
import java.util.List;

import com.rem.core.storage.DataCollector;
import com.rem.core.storage.StorageHandler;
import com.rem.wfs.environment.resource.ship.SpaceShip;

public class SpaceStationPosition {

	private int stationId = -1;
	private int positionId = -1;
	private SpaceShip currentSpaceShip = null;
	public SpaceStationPosition(int stationId, int positionId){
		this.stationId = stationId;
		this.positionId = positionId;
	}
	
	public SpaceShip getShip(){
		return currentSpaceShip;
	}

	protected void associateSpaceShip(SpaceShip spaceShip) {
		this.currentSpaceShip = spaceShip;
		this.currentSpaceShip.setSpaceStationPosition(this);
	}

	public void removeShip() {
		this.currentSpaceShip.setSpaceStationPosition(null);
		this.currentSpaceShip = null;
	}
	
	public static StorageHandler getStorageHandler(final SpaceShip spaceShip){
		return new StorageHandler(){
			@Override
			public void collect(DataCollector data) throws IOException {
				int sId = -1;
				int pId = -1;
				SpaceStationPosition position = spaceShip.getSpaceStationPosition();
				if(position==null){
					sId = data.collect(-1);
					pId = data.collect(-1);
				}
				else {
					sId = data.collect(position.stationId);
					pId = data.collect(position.positionId);
				}
				if(sId>-1){
					List<SpaceStation> stations = spaceShip.getAvailibleStations();
					if(stations==null||stations.size()<sId){
						throw new RuntimeException("Stations("+stations+")["+
								(stations!=null?stations.size():-1)+"] do not have an element for intdex:"+sId);
					}
					else {
						SpaceStation station = stations.get(sId);
						position = station.createPosition(pId);
						position.associateSpaceShip(spaceShip);
					}
				}
			}
		};
	}

}
