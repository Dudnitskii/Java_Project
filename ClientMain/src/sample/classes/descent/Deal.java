package sample.classes.descent;


import java.io.Serializable;

public class Deal implements Serializable {
    protected int id;
    protected int userId;
    protected String date;
    protected int sum;

    public Deal(int id, int userId, String date, int sum) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.sum = sum;
    }

    public Deal(int userId, String date, int sum) {
        this.userId = userId;
        this.date = date;
        this.sum = sum;
    }

    public Deal() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}