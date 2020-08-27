package model;

/**
 * Created by Wasiq on 12/22/2018.
 */
public class Floor {

    private int id;
    private String name;
    private int cap;

    public Floor() {
    }

    public Floor(int id, String name, int cap) {
        this.id = id;
        this.name = name;
        this.cap = cap;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCap() {
        return cap;
    }
}
