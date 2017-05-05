package org.sarwar.imcs.class5.collection.util;

import org.sarwar.imcs.class5.application.message.Message;
import org.sarwar.imcs.class5.collection.dto.Gender;

public class GenderUtil {


	public static Gender createGender() {
		int genderInput = UserInputUtil.getIntegerFromUser(Message.GENDER_MSG);
		switch (genderInput) {
		case 1:
			return Gender.MALE;
		case 2:
			return Gender.FEMALE;
		default:
			System.out.println(Message.INVALID_MSG);
			createGender();
		}
		return null;
	}
}
