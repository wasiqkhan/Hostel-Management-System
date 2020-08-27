package model;

/**
 * Created by Wasiq on 12/25/2018.
 */
public class Staff {
    private int id;
    private String fname;
    private String lname;
    private String  salary;
    private String city;
    private String state;
    private String email;
    private String phone;
    private String ttype;

    public Staff() {

    }

    public Staff(int id, String fname, String lname, String salary, String city, String state, String email, String phone, String ttype) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.salary = salary;
        this.city = city;
        this.state = state;
        this.email = email;
        this.phone = phone;
        this.ttype = ttype;
    }

    public Staff(int id, String fname, String lname) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
    }

    public int getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String  getSalary() {
        return salary;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getTtype() {
        return ttype;
    }
}



