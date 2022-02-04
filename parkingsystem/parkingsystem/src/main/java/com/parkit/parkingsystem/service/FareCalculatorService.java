package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	public void calculateFare(Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}
		// times for up to one month of fare calculation
		double inDay = ticket.getInTime().getDay();
		double outDay = ticket.getOutTime().getDay();
		double inHour = ticket.getInTime().getHours();
		double outHour = ticket.getOutTime().getHours();
		double inMinute = ticket.getInTime().getMinutes();
		double outMinute = ticket.getOutTime().getMinutes();

		// constants to calculate day change or hour change crossing stays
		final double DAY_END = 24;
		final double DAY_START = 0;
		final double MINUTE_END = 60;
		final double MINUTE_START = 0;
		final double FREE = 30;

		double dayDurationSameMonth = outDay - inDay;
		double hourDurationDifferentDays = (dayDurationSameMonth - 1) * DAY_END + (outHour - DAY_START)
				+ (DAY_END - inHour);
		double minuteDurationDifferentHours = (hourDurationDifferentDays - 1) * MINUTE_END 
				+ (outMinute - MINUTE_START) + (MINUTE_END - inMinute);

		switch (ticket.getParkingSpot().getParkingType()) {
			case CAR: {
				ticket.setPrice(MINUTE_START);
				if (minuteDurationDifferentHours > FREE) { 
					ticket.setPrice((minuteDurationDifferentHours / 60) * Fare.CAR_RATE_PER_HOUR);

					if (hourDurationDifferentDays > 0) {
						ticket.setPrice((minuteDurationDifferentHours / 60) * Fare.CAR_RATE_PER_HOUR);

						if (dayDurationSameMonth > 0) {
							ticket.setPrice(( minuteDurationDifferentHours / 60)
									* Fare.CAR_RATE_PER_HOUR);
						}
					}
				} 

				break;
			}

			case BIKE: {
				ticket.setPrice(MINUTE_START);

				if (minuteDurationDifferentHours > FREE) {
					ticket.setPrice((minuteDurationDifferentHours / 60) * Fare.BIKE_RATE_PER_HOUR);

					if (hourDurationDifferentDays > 0) {
						ticket.setPrice((minuteDurationDifferentHours / 60) * Fare.BIKE_RATE_PER_HOUR);

						if (dayDurationSameMonth > 0) {
							ticket.setPrice(( minuteDurationDifferentHours / 60)
									* Fare.BIKE_RATE_PER_HOUR);
						}

					}
				}
				
				break;
			}
			default:
				throw new IllegalArgumentException("Unkown Parking Type");
		}
	}
}