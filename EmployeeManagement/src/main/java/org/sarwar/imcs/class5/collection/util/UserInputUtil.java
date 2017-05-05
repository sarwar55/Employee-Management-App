package org.sarwar.imcs.class5.collection.util;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.sarwar.imcs.class5.application.message.Message;

public final class UserInputUtil {

	@SuppressWarnings("resource")
	public static int getIntegerFromUser(String message) {
		int userInput = 0;
		System.out.println(message);
		try{
			userInput = new Scanner(System.in).nextInt();
		} catch (InputMismatchException e) {
			System.out.println(Message.INPUT_FORMAT_MISMATCH);
			return getIntegerFromUser(message);

		}
		return userInput;
	}

	@SuppressWarnings("resource")
	public static String getStringFromUser(String message) {
		String userInput = null;
		System.out.println(message);
		try {
			userInput = new Scanner(System.in).nextLine();
		} catch (InputMismatchException e) {
			System.out.println(Message.INPUT_FORMAT_MISMATCH);
			return getStringFromUser(message);
		}
		return userInput;
	}

	@SuppressWarnings("resource")
	public static Double getDoubleFromUser(String message) {
		Double userInput = null;
		System.out.println(message);
		try{
			userInput = new Scanner(System.in).nextDouble();
		} catch (InputMismatchException e) {
			System.out.println(Message.INPUT_FORMAT_MISMATCH);
			return getDoubleFromUser(message);
		}
		return userInput;
	}

}
