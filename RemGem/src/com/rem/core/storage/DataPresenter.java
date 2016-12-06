package com.rem.core.storage;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;

import com.rem.core.Hub;

public class DataPresenter  {

	private int booleanIndex = 0;
	private int integerIndex = 0;
	private int floatIndex = 0;
	private int longIndex = 0;
	private int stringIndex = 0;
	private boolean[] boolData;
	private int[] intData;
	private float[] floatData;
	private long[] longData;
	private String[] stringData;

	private Iterator<Boolean> booleanIterator = new Iterator<Boolean>(){
		@Override
		public boolean hasNext() {
			return booleanIndex<boolData.length;
		}
		@Override
		public Boolean next() {
			return nextBoolean();
		}
		@Override
		public void remove() {
			
		}			
	};
	private Iterator<Integer> integerIterator = new Iterator<Integer>(){
		@Override
		public boolean hasNext() {
			return integerIndex<intData.length;
		}
		@Override
		public Integer next() {
			return nextInteger();
		}
		@Override
		public void remove() {
			
		}			
	};
	private Iterator<Float> floatIterator = new Iterator<Float>(){
		@Override
		public boolean hasNext() {
			return floatIndex<floatData.length;
		}

		@Override
		public Float next() {
			return nextFloat();
		}

		@Override
		public void remove() {
			
		}
	};
	private Iterator<Long> longIterator = new Iterator<Long>(){
		@Override
		public boolean hasNext() {
			return longIndex<longData.length;
		}

		@Override
		public Long next() {
			return nextLong();
		}

		@Override
		public void remove() {
			
		}
	};
	private Iterator<String> stringIterator = new Iterator<String>(){
		@Override
		public boolean hasNext() {
			return stringIndex<stringData.length;
		}

		@Override
		public String next() {
			return nextString();
		}

		@Override
		public void remove() {
			
		}
	};

	public Boolean nextBoolean(){
		if(Storage.debug_load)Hub.log.bufferDebug("DataPresenter.nextBoolean", boolData[booleanIndex]+",");
		return boolData[booleanIndex++];
	}

	public Integer nextInteger(){
		if(Storage.debug_load)Hub.log.bufferDebug("DataPresenter.nextInteger", intData[integerIndex]+",");
		return intData[integerIndex++];
	}

	public Float nextFloat(){
		if(Storage.debug_load)Hub.log.bufferDebug("DataPresenter.nextFloat", floatData[floatIndex]+",");
		return floatData[floatIndex++];
	}
	public Long nextLong(){
		if(Storage.debug_load)Hub.log.bufferDebug("DataPresenter.nextLong", longData[longIndex]+",");
		return longData[longIndex++];
	}
	public String nextString(){
		return stringData[stringIndex++];
	}

	public DataPresenter(){
	}

	public Iterator<Boolean> getBooleans(){
		return booleanIterator;
	}
	public Iterator<Integer> getIntegers(){
		return integerIterator;
	}
	public Iterator<Float> getFloats(){
		return floatIterator;
	}
	public Iterator<Long> getLongs(){
		return longIterator;
	}
	public Iterator<String> getStrings(){
		return stringIterator;
	}
	
	public void decodeHeader(byte[] file,int startAt){

		integerIndex = 0;
		intData = new int[5];
		for(int index = 0;index<intData.length;++index){
			startAt = decodeInteger(index,file,startAt);
		}
	}
	public void decode(byte[] file,int startAt,Map<Integer,String> stringMap, DataPresenter header){
		booleanIndex = 0;
		integerIndex = 0;
		floatIndex = 0;
		longIndex = 0;
		stringIndex = 0;

		boolData = new boolean[header.nextInteger()];
		intData = new int[header.nextInteger()];
		floatData = new float[header.nextInteger()];
		longData = new long[header.nextInteger()];
		stringData = new String[header.nextInteger()];
		if(Storage.debug_load)Hub.log.debug("DataPresenter.()", intData.length+","+floatData.length+","+stringData.length);

		startAt = decodeBooleans( boolData.length,file,startAt);
		for(int index = 0;index<intData.length;++index){
			startAt = decodeInteger(index,file,startAt);
		}
		
		for(int index=0;index<floatData.length;++index){
			floatData[index] = decodeFloat(file,startAt);
			startAt+=4;
		}
		for(int index=0;index<longData.length;++index){
			longData[index] = decodeLong(file,startAt);
			startAt+=8;
		}
		for(int index=0;index<stringData.length;++index){
			startAt = decodeIntegerIntoString(index, stringMap, file,startAt);
		}
	}


	public Float decodeFloat(byte[] cs, int a){
		byte[] b = new byte[4];
		for(int i=0;i<4;++i){
			b[i]=cs[i+a];
		}
		return  ByteBuffer.wrap(b).getFloat();
	}
	public Long decodeLong(byte[] cs, int a){
		byte[] b = new byte[8];
		for(int i=0;i<8;++i){
			b[i]=cs[i+a];
		}
		return  ByteBuffer.wrap(b).getLong();
	}

	public Integer decodeInteger(int index, byte[] cs, int a){
		int i=0;
		while(cs[a]>=0){
			i*=128;
			i+=cs[a];
			++a;
		}
		i*=64;
		if(cs[a]>=-128+64){
			i+=cs[a]-64+128;			
		}
		else {
			i+=cs[a]+128;
			i=-i;
		}
		intData[index] = i;
		return a+1;
	}
	public Integer decodeIntegerIntoString(int index,Map<Integer,String> stringMap, byte[] cs, int a){
		int i=0;
		while(cs[a]>=0){
			i*=128;
			i+=cs[a];
			++a;
		}
		i*=64;
		if(cs[a]>=-128+64){
			i+=cs[a]-64+128;			
		}
		else {
			i+=cs[a]+128;
			i=-i;
		}
		stringData[index] = stringMap.get(i);
		return a+1;
	}
	

	public Integer decodeBooleans(int booleanLength, byte[] file,  int startAt) {
		int currentLimit = booleanLength>7?7:booleanLength-1;
		int arrayIndex=0;
		while(booleanLength>0){
			arrayIndex   +=currentLimit;
			byte current = file[startAt++];
			for(int factor=(int) Math.pow(2, currentLimit);;--arrayIndex,factor=factor/2){
				boolData[arrayIndex] = current>=factor||current<0;
				if(boolData[arrayIndex]){
					current-=factor;
				}
				if(factor==1)break;
			}
			arrayIndex   +=currentLimit+1;
			
			booleanLength-=currentLimit+1;
			currentLimit = booleanLength>7?7:booleanLength-1;
		}
		return startAt;
	}
}
