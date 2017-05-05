package org.sarwar.imcs.class5.collection.util;

import org.sarwar.imcs.class5.application.message.Message;
import org.sarwar.imcs.class5.collection.dto.Address;

public class AddressUtil {
	public static Address createAddress() {
		String city = UserInputUtil.getStringFromUser(Message.CITY_MSG);
		String country = UserInputUtil.getStringFromUser(Message.COUNTRY_MSG);
		int postal = UserInputUtil.getIntegerFromUser(Message.ZIP_CODE_MSG);
		if(("" + postal).length() !=5){
			System.out.println(Message.ZIP_CODE_ERROR);
			return createAddress();
		}
		return new Address(city, country, postal);
	}
}
