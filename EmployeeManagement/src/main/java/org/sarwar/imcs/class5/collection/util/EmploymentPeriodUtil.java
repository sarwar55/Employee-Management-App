package org.sarwar.imcs.class5.collection.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.sarwar.imcs.class5.application.message.Message;
import org.sarwar.imcs.class5.collection.dto.EmploymentPeriod;

public class EmploymentPeriodUtil {

	public static EmploymentPeriod createEmploymentPeriod() throws DateNotValidException {
		LocalDate startDate = null;
		LocalDate endDate = null;
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(Message.DATE_FORMATE);
		String startDateInput = UserInputUtil.getStringFromUser(Message.START_DATE_MSG);
		try {
			startDate = LocalDate.parse(startDateInput, dateFormat);
		} catch (DateTimeParseException e) {
			System.out.println(Message.DATE_ERROR_MSG);
			createEmploymentPeriod();
		}
		String endDateInput = UserInputUtil.getStringFromUser(Message.END_DATE_MSG);
		if (endDateInput.equalsIgnoreCase("UN")) {
			return new EmploymentPeriod(startDate);
		} else {
			try {
				endDate = LocalDate.parse(endDateInput, dateFormat);
				return new EmploymentPeriod(startDate, endDate);
			} catch (DateTimeParseException e) {
				System.out.println(Message.DATE_ERROR_MSG);
				createEmploymentPeriod();
			}
		}
		return null;
	}
}
