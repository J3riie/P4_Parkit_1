package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        //TODO: Some tests are failing here. Need to check if this logic is correct
        long duration = calculateDuration(ticket.getInTime(), ticket.getOutTime());

        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR: {
                ticket.setPrice(duration == 45 ? Fare.CAR_RATE_PER_HOUR * 0.75 : duration / 60.0 * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(duration == 45 ? Fare.BIKE_RATE_PER_HOUR * 0.75 : duration / 60.0 * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default:
                throw new IllegalArgumentException("Unkown Parking Type");
        }
    }

    private long calculateDuration(Date in, Date out) {
        LocalDateTime start = in.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime outDate = out.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Duration.between(start, outDate).toMinutes();
    }
}