package sample.classes.descent;

import java.io.Serializable;

public class RefWithd extends Real implements Serializable {
    private String operation;
    private int cvv;

    public RefWithd(int userId, String date, int sum, String address, String operation, int cvv) {
        super(userId,date,sum,address);
        this.operation = operation;
        this.cvv = cvv;
    }

    public RefWithd(int id, int userId, String date, int sum, String address, String operation, int cvv) {
        super(id,userId,date,sum,address);
        this.operation = operation;
        this.cvv = cvv;
    }

    public RefWithd() {}

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    @Override
    public String toString() {
        return "RefWithd{" +
                "id=" + id +
                ", userid=" + userId +
                ", date='" + date + '\'' +
                ", sum=" + sum +
                ", address=" + address + '\'' +
                ", operation='" + operation + '\'' +
                ", cvv=" + cvv +
                '}';
    }
}
