package com.anmol.bhargava.model;

public class Room{

    private RoomType roomType;
    private double price;
    private int roomNum;

    public Room(RoomType roomType, double price, int roomNum) {
        this.roomType = roomType;
        this.price = price;
        this.roomNum = roomNum;
    }
    
    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    @Override
    public String toString() {
        return "Room{ " 
                + "roomNum: " + roomNum
                + " roomType: " + roomType
                + " price: " + price + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Room)) {
            return false;
        }

        Room r = (Room) obj;

        return this.roomType.equals(r.getRoomType())
                && this.roomNum == r.getRoomNum();
    }
}