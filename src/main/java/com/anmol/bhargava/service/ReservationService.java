package com.anmol.bhargava.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.anmol.bhargava.model.Customer;
import com.anmol.bhargava.model.Reservation;
import com.anmol.bhargava.model.Room;
import com.anmol.bhargava.model.RoomType;

public class ReservationService {
    private static final ReservationService SINGLETON = new ReservationService();

    private HashMap<Integer, Reservation> reservations = new HashMap<>();
    private HashMap<Integer, Room> rooms = new HashMap<>();
    private HashMap<Integer, Customer> customers = new HashMap<>();

    public static ReservationService getSingleton() {
        return SINGLETON;
    }

    public void addRoom(int roomNum, RoomType roomType, double price){
        if(!rooms.containsKey(roomNum)){
            rooms.put(roomNum, new Room(roomType, price, roomNum));
        }
    }

    public void showAllRooms() {
        System.out.println("Rooms in inventory are:");
        for(int roomNum : rooms.keySet()){
            Room room = rooms.get(roomNum);
            System.out.println(room);
        }
    }

    public Set<Integer> availableRooms(RoomType roomType, Date checkInDate, Date checkOutDate) {
        Set<Integer> roomsOfType = findAllRoomsOfType(roomType);
        
        for(Reservation r : reservations.values()){
            if(r.getRoom().getRoomType() == roomType){
                if(reservationOverlaps(r, checkInDate, checkOutDate)){
                    roomsOfType.remove(r.getRoom().getRoomNum());
                }
            }
        }
        return roomsOfType;
    }

    private Set<Integer> findAllRoomsOfType(RoomType roomType) {
        Set<Integer> roomsFound = new HashSet<>();
        roomsFound = rooms.entrySet()
                            .stream()
                            .filter(a -> a.getValue().getRoomType() == roomType)
                            .map(e -> e.getKey())
                            .collect(Collectors.toSet());
        return roomsFound;
    }

    private boolean reservationOverlaps(Reservation reservation, Date checkInDate, Date checkOutDate){
        Date resCheckIn = reservation.getCheckInDate();
        Date resCheckOut = reservation.getCheckOutDate();
        return (checkInDate.equals(resCheckIn)) || // check-in date is same
                (checkOutDate.equals(resCheckOut)) || // checkout date is same
                (checkInDate.after(resCheckIn) && checkInDate.before(resCheckOut)) || //check-in is in resrvation range
                (checkOutDate.after(resCheckIn) && checkOutDate.before(resCheckOut)) ||// check-out is in reservation range
                (checkInDate.before(resCheckIn) && checkOutDate.after(resCheckOut)); // check-in and check-out contain reervation range
    }

    public void bookARoom(RoomType roomType, Date checkInDate, Date checkOutDate, Customer customer, List<Integer> availableRooms) {
        Room room = rooms.get(availableRooms.get(0));
        Reservation reservation = new Reservation(checkInDate, checkOutDate, customer, room);
        int newReservationId = reservations.size() + 1;
        reservation.setReservationId(newReservationId);
        reservations.put(newReservationId, reservation);
    }

    private boolean customerExists(Customer customer) {
        for(Customer c : customers.values()){
            if(c.equals(customer)){
                return true;
            }
        }
        return false;
    }

    public Customer getCustomer(String firstName, String lastName, String email_id) {
        Customer customer = new Customer(firstName, lastName, email_id);
        if(!customerExists(customer)){
            int newId = customers.size() + 1;
            customers.put(newId, customer);
            customer.setCustomerId(newId);
        }
        return customer;
    }

    public void printAllReservations() {
        for(Reservation r : reservations.values()){
            System.out.println(r);
        }
    }

    
}
