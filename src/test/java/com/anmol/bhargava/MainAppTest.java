package com.anmol.bhargava;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import com.anmol.bhargava.model.Customer;
import com.anmol.bhargava.model.RoomType;
import com.anmol.bhargava.service.ReservationService;

import org.junit.BeforeClass;
import org.junit.Test;

public class MainAppTest {

    private static ReservationService reservationService;
    private static Customer customer;
    private static Date checkInDate;
    private static Date checkOutDate;
    private static Set<Integer> availableRooms;
    private static RoomType roomType;

    @BeforeClass
    public static void initializeData(){
        reservationService = ReservationService.getSingleton();
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
             
        roomType = RoomType.SINGLE;
        customer = reservationService.getCustomer("Anmol", "Bhargava", "a@a.com");
        checkInDate = getValidDate("2022/05/29"); 
        checkOutDate = getValidDate("2022/05/31");
        availableRooms = reservationService.availableRooms(RoomType.SINGLE, checkInDate, checkOutDate);
        reservationService.bookARoom(roomType, checkInDate, checkOutDate, customer, new ArrayList<>(availableRooms));

        checkInDate = getValidDate("2022/05/29"); 
        checkOutDate = getValidDate("2022/05/31");
        availableRooms = reservationService.availableRooms(RoomType.SINGLE, checkInDate, checkOutDate);
        reservationService.bookARoom(roomType, checkInDate, checkOutDate, customer, new ArrayList<>(availableRooms));
        
        checkInDate = getValidDate("2022/05/29"); 
        checkOutDate = getValidDate("2022/05/31");
        availableRooms = reservationService.availableRooms(RoomType.SINGLE, checkInDate, checkOutDate);
        reservationService.bookARoom(roomType, checkInDate, checkOutDate, customer, new ArrayList<>(availableRooms));
    }

    @Test
    public void noRoomAvailable(){
        checkInDate = getValidDate("2022/05/29"); 
        checkOutDate = getValidDate("2022/05/31");
        availableRooms = reservationService.availableRooms(RoomType.SINGLE, checkInDate, checkOutDate);
        assertTrue("Available rooms were not 0", availableRooms.size() == 0);
    }
    
    
    @Test
    public void checkInDateIsSame(){
        checkInDate = getValidDate("2022/05/29"); 
        checkOutDate = getValidDate("2022/06/02");
        availableRooms = reservationService.availableRooms(RoomType.SINGLE, checkInDate, checkOutDate);
        assertTrue("Available rooms were not 0", availableRooms.size() == 0);
    }

    @Test
    public void checkOutDateIsSame(){
        checkInDate = getValidDate("2022/05/28"); 
        checkOutDate = getValidDate("2022/05/31");
        availableRooms = reservationService.availableRooms(RoomType.SINGLE, checkInDate, checkOutDate);
        assertTrue("Available rooms were not 0", availableRooms.size() == 0);
    }

    @Test
    public void checkInDateInReservationRange(){
        checkInDate = getValidDate("2022/05/30"); 
        checkOutDate = getValidDate("2022/06/02");
        availableRooms = reservationService.availableRooms(RoomType.SINGLE, checkInDate, checkOutDate);
        assertTrue("Available rooms were not 0", availableRooms.size() == 0);
    }

    @Test
    public void checkOutDateInReservationRange(){
        checkInDate = getValidDate("2022/05/28"); 
        checkOutDate = getValidDate("2022/05/30");
        availableRooms = reservationService.availableRooms(RoomType.SINGLE, checkInDate, checkOutDate);
        assertTrue("Available rooms were not 0", availableRooms.size() == 0);
    }

    @Test
    public void checkInAndOutDateContainsReservation(){
        checkInDate = getValidDate("2022/05/25"); 
        checkOutDate = getValidDate("2022/06/02");
        availableRooms = reservationService.availableRooms(RoomType.SINGLE, checkInDate, checkOutDate);
        assertTrue("Available rooms were not 0", availableRooms.size() == 0);
    }

    private static Date getValidDate(String nextLine) {
        try{
            return new SimpleDateFormat("yyyy/MM/dd").parse(nextLine);
        }catch(Exception e){
            // Do Nothing
        }
        return null;
    }
}
