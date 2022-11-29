package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.model.Ticket;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static com.parkit.parkingsystem.constants.Fare.BIKE_RATE_PER_HOUR;
import static com.parkit.parkingsystem.constants.Fare.CAR_RATE_PER_HOUR;

public class FareCalculatorService {

    public static final int MINUTES_PARKING = 45;

    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        //TODO: Some tests are failing here. Need to check if this logic is correct
        Duration duration = calculateDuration(ticket.getInTime(), ticket.getOutTime());

        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR: {
                ticket.setPrice(computePrice(duration, CAR_RATE_PER_HOUR));
                break;
            }
            case BIKE: {
                ticket.setPrice(computePrice(duration, BIKE_RATE_PER_HOUR));
                break;
            }
            default:
                throw new IllegalArgumentException("Unkown Parking Type");
        }
    }

    private double computePrice(Duration duration, double ratePerHour) {
        if (is45MinutesParking(duration)) {
            return ratePerHour * 0.75;
        } else {
            return duration.toHours() * ratePerHour;
        }
    }

    private boolean is45MinutesParking(Duration duration) {
        return duration.toMinutes() == MINUTES_PARKING;
    }

    private Duration calculateDuration(Date in, Date out) {
        LocalDateTime start = in.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime outDate = out.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Duration.between(start, outDate);
    }
}