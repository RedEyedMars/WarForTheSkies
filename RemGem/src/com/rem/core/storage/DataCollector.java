package com.rem.core.storage;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.rem.core.Hub;

public class DataCollector implements Iterable<String> {
	private List<Boolean> booleans = new ArrayList<Boolean>();
	private List<Integer> ints = new ArrayList<Integer>();
	private List<Float> floats = new ArrayList<Float>();
	private List<Long> longs = new ArrayList<Long>();
	private List<String> strings = new ArrayList<String>();
	private LinkedHashMap<String,Integer> stringMap = new LinkedHashMap<String,Integer>();

	public DataCollector getHeader(){
		DataCollector header = new DataCollector();
		header.collect(booleans.size());
		header.collect(ints.size());
		header.collect(floats.size());
		header.collect(longs.size());
		header.collect(strings.size());
		return header;
	}
	public boolean collect(Boolean b){
		if(Storage.debug_save)Hub.log.bufferDebug("Storage.debug",b+",");
		return booleans.add(b);
	}
	public boolean collect(Integer i){
		if(Storage.debug_save)Hub.log.bufferDebug("Storage.debug",i+",");
		return ints.add(i);
	}
	public boolean collect(Float f){
		if(Storage.debug_save)Hub.log.bufferDebug("Storage.debug",f+",");
		return floats.add(f);
	}
	public boolean collect(Long l){
		if(Storage.debug_save)Hub.log.bufferDebug("Storage.debug",l+",");
		return longs.add(l);
	}
	public boolean collect(String s){
		if(Storage.debug_save)Hub.log.bufferDebug("Storage.debug",s+",");
		if(!stringMap.containsKey(s)){
			stringMap.put(s, stringMap.size());
		}
		return strings.add(s);
	}
	public Iterator<Integer> getIntegers() {
		return ints.iterator();
	}
	public Iterator<Float> getFloats() {
		return floats.iterator();
	}
	public Iterator<Long> getLongs() {
		return longs.iterator();
	}
	public Iterator<String> getStrings() {
		return strings.iterator();
	}
	public Integer getStringIndex(String string) {
		return stringMap.get(string);
	}
	@Override
	public Iterator<String> iterator() {
		return stringMap.keySet().iterator();
	}
	
	
	public void encode(List<Byte> build){
		encodeBooleans(build);
		for(Iterator<Integer> ints=getIntegers();ints.hasNext();){
			encodeInteger(ints.next(),build);
		}
		encodeFloats(build);
		encodeLongs(build);
		for(Iterator<String> strings=getStrings();strings.hasNext();){
			encodeInteger(getStringIndex(strings.next()),build);
		}
	}

	public void encodeInteger(Integer i, List<Byte> build){
		
		int c = 0;
		int f = 1;
		int mod = 64;
		int negmod = 64;
		if(i<0){
			negmod = 0;
			i=-i;
		}
		List<Byte> temp = new ArrayList<Byte>();
		do{
			c=(i/f)%mod;
			temp.add(0,(byte) ((char)(mod==64?(int)(c-128+negmod):c)));
			f*=mod;
			mod=128;
		} while(i>f);
		build.addAll(temp);
	}

	public void encodeFloats(List<Byte> build){
		ByteBuffer buffer = ByteBuffer.allocate(4*floats.size());
		for(int i=0;i<floats.size();++i){
			buffer.putFloat(i*4,floats.get(i));
		}
		for(int i=0;i<4*floats.size();++i){
			build.add(buffer.get(i));
		}
	}
	public void encodeLongs(List<Byte> build){
		ByteBuffer buffer = ByteBuffer.allocate(8*longs.size());
		for(int i=0;i<longs.size();++i){
			buffer.putLong(i*8,longs.get(i));
		}
		for(int i=0;i<8*longs.size();++i){
			build.add(buffer.get(i));
		}
	}

	public void encodeBooleans(List<Byte> build){
		Byte[] bytes = new Byte[booleans.size()/8+1];
		for(int i=0;i<bytes.length;++i){
			bytes[i]=0;
		}
		for(int arrayIndex = 0, boolIndex = 0, factor=1;boolIndex<booleans.size();++boolIndex){
			if(boolIndex>0&&boolIndex%8==0){
				++arrayIndex;
				factor = 1;
			}
			if(booleans.get(boolIndex)){
				bytes[arrayIndex] = (byte) (bytes[arrayIndex] + (byte)factor);
			}

			factor*=2;
			
		}
		build.addAll(Arrays.asList(bytes));
	}
	
}
