package com.rem.wfs.environment.resource.ship;

import java.io.IOException;

import com.rem.core.environment.Range;
import com.rem.core.storage.DataCollector;
import com.rem.core.storage.StorageHandler;

public class ShipName implements StorageHandler {
	private static final Range nameIdRange = new Range(0,32);
	private static final Range suffixIdRange = new Range(0,31);

	private int nameId;
	private int suffixId;
	private String name;
	private String suffix;
	
	public void onCreate(){
		this.nameId = nameIdRange.getRandomElement();
		this.name = getName(nameId);
		
		this.suffixId = suffixIdRange.getRandomElement();
		this.suffix = getSuffix(suffixId);
		
	}
	
	@Override
	public void collect(DataCollector data) throws IOException {
		this.nameId = data.collect(nameId);
		if(nameId>-1){
			name = getName(nameId);
		}
		else {
			name = data.collect(name);
		}
		this.suffixId = data.collect(suffixId);
		if(suffixId>-1){
			suffix = getSuffix(suffixId);
		}
		else {
			suffix = data.collect(suffix);
		}
	}
	
	private static String getName(int id){
		switch(id){
		case 0: return "Daisy";
		case 1: return "Dolly";
		case 2: return "Faithful";
		case 3: return "Eight Lives";
		case 4: return "Fairy";
		case 5: return "Mermaid";
		case 6: return "Screamer";
		case 7: return "Angel";
		case 8: return "Moon";
		case 9: return "Lightrider";
		case 10:return "Starhopper";
		case 11:return "Firefly";
		case 12:return "Eagle";
		case 13:return "Merlin";
		case 14:return "Sandra";
		case 15:return "Sarah";
		case 16:return "Odessy";
		case 17:return "Twin Bows";
		case 18:return "Gallefrey";
		case 19:return "Gazer";
		case 20:return "Geezer";
		case 21:return "Zeus";
		case 22:return "Thor";
		case 23:return "Winged";
		case 24:return "Dragon";
		case 25:return "Demon";
		case 26:return "Windblown";
		case 27:return "Widowmaker";
		case 28:return "Ice";
		case 29:return "Bitter";
		case 30:return "Bre";
		case 31:return "Califlower";
		case 32:return "Wenslydale"; 
		}
		return null;
	}
	
	private static String getSuffix(int id){
		switch(id){
		case 0: return "the Gallant";
		case 1: return "the Destroyer";
		case 2: return "the Victor";
		case 3: return "the Immortal";
		case 4: return "the Slippery";
		case 5: return "the Dancer";
		case 6: return "the Death Defying";
		case 7: return "the Trickster";
		case 8: return "the Whimsical";
		case 9: return "the Vagabond";
		case 10:return "Forecaster";
		case 11:return "the Nimble";
		case 12:return "the Cautious";
		case 13:return "the Hardy";
		case 14:return "the Sturdy";
		case 15:return "the Free";
		case 16:return "the Beautiful";
		case 17:return "the Mighty";
		case 18:return "the I";
		case 19:return "the II";
		case 20:return "the III";
		case 21:return "the IV";
		case 22:return "the V";
		case 23:return "the VI";
		case 24:return "the VII";
		case 25:return "the VIII";
		case 26:return "the IX";
		case 27:return "the X";
		case 28:return "the XI";
		case 29:return "the XII";
		case 30:return "the Unlucky";
		case 31:return "the Lucky";
		}
		return null;
	}

	public String getFullName() {
		return name + " " + suffix;
	}

	public String getName() {
		return name;
	}
	public String getSuffix() {
		return suffix;
	}
}
