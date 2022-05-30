package com.anmol.bhargava.model;

import java.util.Date;

public class Reservation {

    private Date checkInDate;
    private Date checkOutDate;
    private Customer customer;
    private Room room;
    private int reservationId; 
    
    public Reservation(Date checkInDate, Date checkOutDate, Customer customer, Room room) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.customer = customer;
        this.room = room;
        //this.reservationId = reservationId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    @Override
    public String toString() {

        return "Reservation{ "
                + "customer: " + this.customer + ", "
                + "roomt: " + this.room + ", "
                + "checkInDate: " + this.checkInDate + ", "
                + "checkOutDate: " + this.checkOutDate + " }";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Reservation)) {
            return false;
        }

        Reservation r = (Reservation) obj;

        return this.checkInDate.equals(r.getCheckInDate())
                && this.checkOutDate.equals(r.getCheckOutDate())
                && this.customer.equals(r.getCustomer())
                && this.room.equals(r.getRoom());
    }
}
