package com.rem.core.storage;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import com.rem.core.Hub;

public class DataEncoder implements DataCollector {
	private OutputStream build;
	private ByteBuffer floatBuffer = ByteBuffer.allocate(4);
	private ByteBuffer longBuffer = ByteBuffer.allocate(8);
	
	public DataEncoder(OutputStream build){
		this.build = build;
	}
	
	public Boolean collect(Boolean b) throws IOException{
		if(Storage.debug_save)Hub.log.bufferDebug("Storage.debug",b+"b,");
		encodeInteger(b?0:1);
		return b;
	}
	public void collect(boolean[] bs) throws IOException{
		if(Storage.debug_save)Hub.log.bufferDebug("Storage.debug",bs+"bs,");
		encodeBooleans(bs);
	}
	public Integer collect(Integer i) throws IOException{
		if(Storage.debug_save)Hub.log.bufferDebug("Storage.debug",i+"i,");
		encodeInteger(i);
		return i;
	}
	public Float collect(Float f) throws IOException{
		if(Storage.debug_save)Hub.log.bufferDebug("Storage.debug",f+"f,");
		encodeFloat(f);
		return f;
	}
	public Long collect(Long l) throws IOException{
		if(Storage.debug_save)Hub.log.bufferDebug("Storage.debug",l+"l,");
		encodeLong(l);
		return l;
	}
	public String collect(String s) throws IOException{
		if(Storage.debug_save)Hub.log.bufferDebug("Storage.debug",s+"[s],");
		for(char c:s.toCharArray()){
			build.write(c);
		}
		build.write('\0');
		return s;
	}	

	public void encodeInteger(Integer i) throws IOException{
		
		int c = 0;
		int f = 1;
		int mod = 64;
		int negmod = 64;
		if(i<0){
			negmod = 0;
			i=-i;
		}
		byte[] temp = new byte[]{0,0,0,0,0,0};
		int insertIndex = 5;
		do{
			c=(i/f)%mod;
			temp[insertIndex--] = (byte) ((char)(mod==64?(int)(c-128+negmod):c));
			f*=mod;
			mod=128;
		} while(i>f);
		for(;insertIndex<6;++insertIndex){
			build.write(temp[insertIndex]);
		}
	}

	public void encodeFloat(Float f) throws IOException{
		floatBuffer.clear();
		floatBuffer.position(0);
		floatBuffer.putFloat(f);
		for(int i=0;i<4;++i){
			build.write(floatBuffer.get(i));
		}
	}
	public void encodeLong(Long l) throws IOException{
		longBuffer.clear();
		longBuffer.position(0);
		longBuffer.putLong(l);
		for(int i=0;i<8;++i){
			build.write(longBuffer.get(i));
		}
	}

	public void encodeBooleans(boolean[] booleans) throws IOException{
		Byte[] bytes = new Byte[booleans.length/8+1];
		for(int i=0;i<bytes.length;++i){
			bytes[i]=0;
		}
		for(int arrayIndex = 0, boolIndex = 0, factor=1;boolIndex<booleans.length;++boolIndex){
			if(boolIndex>0&&boolIndex%8==0){
				++arrayIndex;
				factor = 1;
			}
			if(booleans[boolIndex]){
				bytes[arrayIndex] = (byte) (bytes[arrayIndex] + (byte)factor);
			}

			factor*=2;
			
		}
		for(int i=0;i<bytes.length;++i){
			build.write(bytes[i]);
		}
	}
	
}
