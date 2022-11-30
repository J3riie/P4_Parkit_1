package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.model.Ticket;

import static com.parkit.parkingsystem.constants.Fare.BIKE_RATE_PER_HOUR;
import static com.parkit.parkingsystem.constants.Fare.CAR_RATE_PER_HOUR;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket) {
        if (isInvalidOutTime(ticket)) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR: {
                ticket.computePrice(CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.computePrice(BIKE_RATE_PER_HOUR);
                break;
            }
            default:
                throw new IllegalArgumentException("Unkown Parking Type");
        }
    }

    private boolean isInvalidOutTime(Ticket ticket) {
        return ticket.getOutTime() == null || ticket.getOutTime().before(ticket.getInTime());
    }
}