package sample.classes.descent;

import java.io.Serializable;

public class Virtual extends Deal implements Serializable {
    protected int cvv;

    public Virtual(int id, int userId, String date, int sum, int promocode){
        super(id,userId,date,sum);
        this.cvv = promocode;
    }

    public Virtual(int userId, String date, int sum, int cvv){
        super(userId,date,sum);
        this.cvv = cvv;
    }

    public Virtual() {
        super();
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

}