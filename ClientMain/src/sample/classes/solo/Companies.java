package sample.classes.solo;

import java.io.Serializable;

public class Companies implements Serializable {
    private int id;
    private String name;

    public Companies(){};

    public Companies(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Companies(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return  "id: " + id +
                ", name=" + name;
    }
}