package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.model.Ticket;

import static com.parkit.parkingsystem.constants.Fare.BIKE_RATE_PER_HOUR;
import static com.parkit.parkingsystem.constants.Fare.CAR_RATE_PER_HOUR;

public class FareCalculatorService {

    public static final int NO_DISCOUNT = 1;

    public void calculateFare(Ticket ticket) {
        calculate(ticket, NO_DISCOUNT);
    }


    public void calculateFare(Ticket ticket, double discount) {
        calculate(ticket, discount);
    }

    private static void calculate(Ticket ticket, double discount) {
        if (ticket.isOutOfDate()) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR: {
                ticket.computePrice(CAR_RATE_PER_HOUR * discount);
                break;
            }
            case BIKE: {
                ticket.computePrice(BIKE_RATE_PER_HOUR * discount);
                break;
            }
            default:
                throw new IllegalArgumentException("Unknown Parking Type");
        }
    }


}