package com.rem.wfs.environment.resource.personel;

import java.io.IOException;

import com.rem.core.environment.Range;
import com.rem.core.storage.DataCollector;
import com.rem.core.storage.StorageHandler;

public class PersonelName implements StorageHandler{

	private static final Range firstNameIdRange = new Range(0,32);
	private static final Range lastNameIdRange = new Range(0,31);

	private int firstNameId;
	private int lastNameId;
	private String firstName;
	private String lastName;
	
	public void onCreate(){
		this.firstNameId = firstNameIdRange.getRandomElement();
		this.firstName = getFirstName(firstNameId);
		
		this.lastNameId = lastNameIdRange.getRandomElement();
		this.lastName = getLastName(lastNameId);
		
	}
	
	@Override
	public void collect(DataCollector data) throws IOException {
		this.firstNameId = data.collect(firstNameId);
		if(firstNameId>-1){
			firstName = getFirstName(firstNameId);
		}
		else {
			firstName = data.collect(firstName);
		}
		this.lastNameId = data.collect(lastNameId);
		if(lastNameId>-1){
			lastName = getLastName(lastNameId);
		}
		else {
			lastName = data.collect(lastName);
		}
	}


	
	private static String getFirstName(int id){
		switch(id){
		case 0: return "Adamn";
		case 1: return "Authur";
		case 2: return "Ben";
		case 3: return "Brian";
		case 4: return "Casey";
		case 5: return "Corey";
		case 6: return "Devin";
		case 7: return "Donald";
		case 8: return "Earl";
		case 9: return "Ernest";
		case 10:return "Fillip";
		case 11:return "Fred";
		case 12:return "George";
		case 13:return "Greg";
		case 14:return "Ian";
		case 15:return "Ivan";
		case 16:return "Jason";
		case 17:return "Jake";
		case 18:return "James";
		case 19:return "Jimmy";
		case 20:return "Kelly";
		case 21:return "Mark";
		case 22:return "Matt";
		case 23:return "Nathan";
		case 24:return "Orwel";
		case 25:return "Oscar";
		case 26:return "Quin";
		case 27:return "Ron";
		case 28:return "Sam";
		case 29:return "Steve";
		case 30:return "Tucker";
		case 31:return "Victor";
		case 32:return "William"; 
		}
		return null;
	}
	
	private static String getLastName(int id){
		switch(id){
		case 0: return "Anchor";
		case 1: return "Augur";
		case 2: return "Beckman";
		case 3: return "Blecker";
		case 4: return "Cliford";
		case 5: return "Cornor";
		case 6: return "Dawson";
		case 7: return "Driver";
		case 8: return "Eagleson";
		case 9: return "Everest";
		case 10:return "Forecaster";
		case 11:return "Forman";
		case 12:return "Green";
		case 13:return "Goodman";
		case 14:return "Harding";
		case 15:return "Hunter";
		case 16:return "Irwin";
		case 17:return "Iving";
		case 18:return "Kelly";
		case 19:return "Clarkson";
		case 20:return "Lawson";
		case 21:return "Lovesong";
		case 22:return "Madding";
		case 23:return "Miggs";
		case 24:return "Norman";
		case 25:return "Norton";
		case 26:return "Oxglove";
		case 27:return "Quil";
		case 28:return "Rogers";
		case 29:return "Stevens";
		case 30:return "Thomason";
		case 31:return "Watts";
		}
		return null;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
}
