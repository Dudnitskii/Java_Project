package sample.classes.solo;

import java.io.Serializable;

public class Jwls implements Serializable {
    private int id;
    private String item;

    public Jwls(){};

    public Jwls(int id, String item) {
        this.id = id;
        this.item = item;
    }

    public Jwls(String item) {
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return  "id: " + id +
                ", item=" + item;
    }
}