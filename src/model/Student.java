package model;

/**
 * Created by Wasiq on 12/22/2018.
 */
public class Student {

    private int id;
    private String fname;
    private String lname;
    private String reg;
    private String dep;
    private String city;
    private String state;
    private String phone;
    private int rid;


    public Student(Student student) {
    }

    public Student(int id, String fname, String lname, String reg, String dep, String city, String state, String phone, int rid) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.reg = reg;
        this.dep = dep;
        this.city = city;
        this.state = state;
        this.phone = phone;
        this.rid = rid;
    }

    public Student(int id, String fname, String lname) {
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

    public String getReg() {
        return reg;
    }

    public String getDep() {
        return dep;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPhone() {
        return phone;
    }

    public int getRid() {
        return rid;
    }
}
