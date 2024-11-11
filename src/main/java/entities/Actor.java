package entities;

public class Actor {
    private int id;
    private String fullName;

    public Actor() {}
    public Actor(String fullName) {
        this.fullName = fullName;
    }
    public Actor(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
