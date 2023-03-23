package sample.classes.descent;

import java.io.Serializable;

public class Real extends Deal implements Serializable {
    protected String address;

    public Real(int id, int userId, String date, int sum, String address){
        super(id,userId,date,sum);
        this.address = address;
    }

    public Real(int userId, String date, int sum, String address){
        super(userId,date,sum);
        this.address = address;
    }

    public Real() {
        super();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}