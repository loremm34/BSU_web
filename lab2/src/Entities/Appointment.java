package Entities;

public class Appointment {
    private String description;
    private int id;

    public Appointment(int id,String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
