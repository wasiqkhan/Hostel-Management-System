package model;

/**
 * Created by Wasiq on 12/25/2018.
 */
public class Team {
    private String firstname;
    private String lastname;
    private String team;

    public Team() {
    }

    public Team(String firstname, String lastname, String team) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.team = team;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getTeam() {
        return team;
    }
}
