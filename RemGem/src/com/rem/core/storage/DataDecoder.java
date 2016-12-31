package com.rem.core.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.rem.core.Hub;

public class DataDecoder implements DataCollector {
	
	private InputStream input;

	public Boolean collect(Boolean value) throws IOException{
		value = decodeInteger()==0;
		if(Storage.debug_load)Hub.log.bufferDebug("DataPresenter.nextBoolean", value+"b,");
		return value;
	}

	public Integer collect(Integer value) throws IOException{
		value = decodeInteger();
		if(Storage.debug_load)Hub.log.bufferDebug("DataPresenter.nextInteger", value+"i,");
		return value;
	}

	public Float collect(Float value) throws IOException{
		value = decodeFloat();
		if(Storage.debug_load)Hub.log.bufferDebug("DataPresenter.nextFloat", value+"f,");
		return value;
	}
	public Long collect(Long value) throws IOException{
		value = decodeLong();
		if(Storage.debug_load)Hub.log.bufferDebug("DataPresenter.nextLong", value+"l,");
		return value;
	}
	public String collect(String string) throws IOException{
		return decodeString();
	}

	public DataDecoder(InputStream input){
		this.input = input;
	}


	public Float decodeFloat() throws IOException{
		byte[] b = new byte[4];
		for(int i=0;i<4;++i){			
			b[i]=(byte) input.read();
		}
		return  ByteBuffer.wrap(b).getFloat();
	}
	public Long decodeLong() throws IOException{
		byte[] b = new byte[8];
		for(int i=0;i<8;++i){
			b[i]=(byte)input.read();
		}
		return  ByteBuffer.wrap(b).getLong();
	}

	public Integer decodeInteger() throws IOException{
		int i=0;
		int b = (byte)input.read();
		while(b>=0){
			i*=128;
			i+=b;
			b = (byte)input.read();
		}
		i*=64;
		if(b>=-128+64){
			i+=b+64;			
		}
		else {
			i+=b+128;
			i=-i;
		}
		return i;
	}
	public String decodeString() throws IOException{
		StringBuilder builder = new StringBuilder();
		char c = (char)input.read();
		while(c!='\0'){
			builder.append(c);
		}
		return builder.toString();
	}
	

	public boolean[] decodeBooleans(int booleanLength) throws IOException {
		int currentLimit = booleanLength>7?7:booleanLength-1;
		int arrayIndex=0;
		boolean[] boolData = new boolean[booleanLength];
		while(booleanLength>0){
			arrayIndex   +=currentLimit;
			byte current = (byte)input.read();
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
		return boolData;
	}
}
