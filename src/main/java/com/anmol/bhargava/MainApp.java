package com.anmol.bhargava;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.anmol.bhargava.model.Customer;
import com.anmol.bhargava.model.RoomType;
import com.anmol.bhargava.service.ReservationService;

public class MainApp {

    static final Scanner in = new Scanner(System.in);
    static ReservationService reservationService = ReservationService.getSingleton();
    private final static Calendar cal = Calendar.getInstance();
    

    public static void printOptions(){
        System.out.println("--------------------------------------------------------------------------------------\n"
                            + "Hote Reservation System v1.0.0\n"
                            + "Welcome, please select an action (enter number displayed against the action):\n"
                            + "1. Check Availability for a room type\n"
                            + "2. Book a room\n"
                            + "3. Exit system\n"
                            + "NOTE: room types are 'Single', 'Double' and 'Delux'\n"
                            + "--------------------------------------------------------------------------------------\n");
    }

    public static void menu(){
        
        initSystem();
        String inp = "";
       
        do{
            printOptions();
            inp = in.nextLine();
            if (inp.length() == 1) {
                switch (inp.charAt(0)) {
                    case '1':
                        checkAvailability();
                        break;
                    case '2':
                        bookARoom();
                        break;
                    case '3':
                        System.out.println("Exiting system!\n" + "Thank you\n" );
                        in.close();
                        System.exit(0);
                        break;
                    case '4': // Hidden options
                        System.out.println("Selected action is to print all reservations in system");
                        reservationService.printAllReservations();
                        break;
                    default:
                        System.out.println("Unknown action\n");
                        break;
                }
            } else {
                System.out.println("Error: Invalid action\n");
            }
        }while(true);
        
    }

    private static void initSystem() {

        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);

        RoomType roomType = RoomType.SINGLE;
        for(int i = 1; i < 11; i++){
            switch(i%3){
                case 0:
                    roomType = RoomType.SINGLE;
                    break;
                case 1:
                    roomType = RoomType.DOUBLE;
                    break;
                case 2:
                    roomType = RoomType.DELUX;
                    break;
                default:
                    break;
            }
            reservationService.addRoom(100 + i, roomType, 0.0);
        }
    }

    private static void checkAvailability() {
        System.out.println("Enter the room type that you want (either single, double or delux):");
        RoomType roomType = getValidRoom(in.nextLine());

        System.out.println("Enter the check-in date that is desired in the format YYYY/MM/DD:");
        Date checkInDate = getValidCheckInDate(in.nextLine());
        
        System.out.println("Enter the check-out date that is desired in the format YYYY/MM/DD:");
        Date checkOutDate = getValidCheckOutDate(in.nextLine(), checkInDate);
        
        if(isAvailable(roomType, checkInDate, checkOutDate)){
            System.out.println("Room is avaiable.");
        }else{
            System.out.println("Room is not avaiable.");
        }
    }

    private static void bookARoom() {
        System.out.println("Enter the room type that you want (either single, double or delux):");
        RoomType roomType = getValidRoom(in.nextLine());

        System.out.println("Enter the check-in date that is desired in the format YYYY/MM/DD:");
        Date checkInDate = getValidCheckInDate(in.nextLine());

        System.out.println("Enter the check-out date that is desired in the format YYYY/MM/DD:");
        Date checkOutDate = getValidCheckOutDate(in.nextLine(), checkInDate);
        Set<Integer> availableRooms = reservationService.availableRooms(roomType, checkInDate, checkOutDate);

        if(availableRooms.size() > 0){
            System.out.println("A room for these dates is Available. Enter customer information:");
            Customer customer = getCustomerInfo();
            reservationService.bookARoom(roomType, checkInDate, checkOutDate, customer, new ArrayList<>(availableRooms));
            System.out.println("Room(s) sucessfully booked.");
        }else{
            System.out.println("Room is not avaiable.");
        }
    }

    private static Customer getCustomerInfo(){
        System.out.println("Enter Customers first name:");
        String firstName = getValidName(in.nextLine());

        System.out.println("Enter Customers last name:");
        String lastName = getValidName(in.nextLine());

        System.out.println("Enter Customers email id:");
        String email_id = getValidEmail(in.nextLine());

        return reservationService.getCustomer(firstName, lastName, email_id);
    }

    private static String getValidEmail(String nextLine) {
        if (nextLine == null) {
            System.out.println("Empty string entered. Try Again!");
            getCustomerInfo();
        }

        String regex = "^(.+)@(\\S+)$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(nextLine);
  
        if(m.matches()){
            return nextLine;
        }else{
            System.out.println("Invalid email.");
            getCustomerInfo();
        }
        return null;
    }

    

    private static String getValidName(String nextLine) {
        if (nextLine == null) {
            System.out.println("Empty string entered. Try Again!");
            getCustomerInfo();
        }

        String regex = "[A-Za-z]*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(nextLine);
  
        if(m.matches()){
            return nextLine;
        }else{
            System.out.println("Invalid name. A name can contain only english alpahbets.");
            getCustomerInfo();
        }
        return null;
    }

    private static Date getValidCheckOutDate(String nextLine, Date checkInDate) {
        Date checkOutDate = getValidDate(nextLine);
        if(checkOutDate.after(checkInDate)){
            return checkOutDate;
        }
        System.out.println("Error: Invalid checkout date. Check-out date needs to be greater than equal to check-in date.");
        checkAvailability();
        return null;
    }

    private static Date getValidCheckInDate(String nextLine) {
        Date checkInDate = getValidDate(nextLine);
        Date today = cal.getTime();  

        if(checkInDate.before(today)){
            System.out.println("Error: Invalid checkin date. Check-In date needs to be greater than equal to today");
            checkAvailability();
        }
        return checkInDate;
    }

    
    private static Date getValidDate(String nextLine) {
        try {
            return new SimpleDateFormat("yyyy/MM/dd").parse(nextLine);
        } catch (ParseException ex) {
            System.out.println("Error: Invalid date format.");
            checkAvailability();
        }
        return null;
    }

    private static RoomType getValidRoom(String roomType) {
        try{
            return RoomType.valueOf(roomType.toUpperCase());
        }catch(IllegalArgumentException e){
            checkAvailability();
        }
        
        return null;
    }

    private static boolean isAvailable(RoomType roomType, Date checkInDate, Date checkOutDate) {
        return reservationService.availableRooms(roomType, checkInDate, checkOutDate).size() > 0;
    }
}
