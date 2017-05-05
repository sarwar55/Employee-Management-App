package org.sarwar.imcs.class5.collection.dto;

import java.time.LocalDate;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.sarwar.imcs.class5.collection.util.DateNotValidException;

public class EmploymentPeriod {
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean isActive;

	public EmploymentPeriod(LocalDate startDate) throws DateNotValidException{
		if (startDate.isAfter(LocalDate.now())) {
			throw new DateNotValidException("Start Date should come before Today!!!");
		}
		this.startDate = startDate;
		this.endDate = null;
		isActive = true;
	}

	public EmploymentPeriod(LocalDate startDate, LocalDate endDate) throws DateNotValidException {
		if (endDate.isBefore(startDate)) {
			throw new DateNotValidException("Start Date should come before End Date.");
		}
		this.startDate = startDate;
		this.endDate = endDate;
		if (endDate.isBefore(LocalDate.now())) {
			isActive = false;
		} else {
			isActive = true;
		}
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) throws DateNotValidException{
		if (endDate.isBefore(startDate)) {
			throw new DateNotValidException("Start Date should come before End Date.");
		}
		this.endDate = endDate;
		if (endDate.isBefore(LocalDate.now())) {
			isActive = false;
		}
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
