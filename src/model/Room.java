package model;

/**
 * Created by Wasiq on 12/22/2018.
 */
public class Room {
    private int id;
    private int cap;
    private String floor;

    public Room() {
    }

    public Room(int id, int cap, String  floor) {
        this.id = id;
        this.cap = cap;
        this.floor = floor;
    }

    public Room(int id, int cap) {
        this.id = id;
        this.cap = cap;
    }

    public int getId() {
        return id;
    }

    public int getCap() {
        return cap;
    }

    public String getFloor() {
        return floor;
    }
}
