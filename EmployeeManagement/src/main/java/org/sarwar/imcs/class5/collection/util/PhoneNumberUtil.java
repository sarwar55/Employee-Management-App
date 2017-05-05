package org.sarwar.imcs.class5.collection.util;

import org.sarwar.imcs.class5.application.message.Message;
import org.sarwar.imcs.class5.collection.dto.PhoneNumber;

public class PhoneNumberUtil {

	public static PhoneNumber createPhoneNumber() {
		String areaCode = UserInputUtil.getStringFromUser(Message.AREA_CODE_MSG);
		if(areaCode.length() != 3){
			System.out.println(Message.THREE_DIGIT_ERROR);
			return createPhoneNumber();
		}
		String number = UserInputUtil.getStringFromUser(Message.PHONE_NUMBER_ERROR_MSG);
		if(number.length() != 7 ){
			System.out.println(Message.SEVEN_DIGIT_ERROR);
			return createPhoneNumber();
		}
		return new PhoneNumber(areaCode, number);
	}
}
