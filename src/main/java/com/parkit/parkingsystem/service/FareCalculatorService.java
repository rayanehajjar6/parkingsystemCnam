package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        //GetTime des milliseconde; /1000 des secondes; /60 des minutes
        long inMinutes = ticket.getInTime().getTime()/1000/60;
        long outMinutes = ticket.getOutTime().getTime()/1000/60;

        long durationm = outMinutes - inMinutes;
        double rate = 1.0;
        int duration = (int) durationm / 60; 
        if (durationm <= 30) {
            rate = 0.0;
        } else if (durationm < 60) {
            rate = 0.5;
            duration = 1;
        } else {
            durationm -= 30;
            if (durationm < 60) {
                rate = 0.5;
                duration = 1;
            } else {
                duration = (int) durationm / 60; 
            }
        }

       
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(rate * duration * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(rate * duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}