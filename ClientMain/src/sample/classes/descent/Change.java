package sample.classes.descent;

import java.io.Serializable;

public class Change extends Real implements Serializable {
    private String currFrom;
    private String currTo;

    public Change(int userId, String date, int sum, String address,  String currFrom, String currTo) {
        super(userId,date,sum,address);
        this.currFrom = currFrom;
        this.currTo = currTo;
    }

    public Change(int id, int userId,  String date, int sum, String address, String currFrom, String currTo) {
        super(id,userId,date,sum,address);
        this.currFrom = currFrom;
        this.currTo = currTo;
    }

    public Change() {}

    public String getCurrFrom() {
        return currFrom;
    }

    public void setCurrFrom(String currFrom) {
        this.currFrom = currFrom;
    }

    public String getCurrTo() {
        return currTo;
    }

    public void setCurrTo(String currTo) {
        this.currTo = currTo;
    }

    @Override
    public String toString() {
        return "Change{" +
                "id=" + id +
                ", userid=" + userId +
                ", date='" + date + '\'' +
                ", sum=" + sum +
                ", address=" + address + '\'' +
                ", curfrom='" + currFrom + '\'' +
                ", curto='" + currTo + '\'' +
                '}';
    }
}
