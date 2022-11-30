package com.parkit.parkingsystem.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

import static java.time.ZoneId.systemDefault;

public class Ticket {

    private int id;
    private ParkingSpot parkingSpot;
    private String vehicleRegNumber;
    private double price;
    private Date inTime;
    private Date outTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public void computePrice(double ratePerHour) {
        Duration duration = calculateDuration();
        if (isMinutesParking(duration)) {
            this.setPrice(ratePerHour * duration.toMinutes() / 60);
        } else {
            this.setPrice(duration.toHours() * ratePerHour);
        }
    }

    private boolean isMinutesParking(Duration duration) {
        return duration.toMinutes() > 0;
    }

    private Duration calculateDuration() {
        LocalDateTime start = this.inTime.toInstant().atZone(systemDefault()).toLocalDateTime();
        LocalDateTime outDate = this.outTime.toInstant().atZone(systemDefault()).toLocalDateTime();
        return Duration.between(start, outDate);
    }
}
