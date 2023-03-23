package sample.classes.descent;

import java.io.Serializable;

public class Transaction extends Virtual implements Serializable {
    private int otherId;
    private String comment;

    public Transaction(int userId, String date, int sum, int cvv, int otherId,  String comment) {
        super(userId,date,sum,cvv);
        this.otherId = otherId;
        this.comment = comment;
    }

    public Transaction(int id, int userId, String date, int sum, int cvv, int otherId,   String comment) {
        super(id,userId,date,sum,cvv);
        this.otherId = otherId;
        this.comment = comment;
    }

    public Transaction() {}

    public int getOtherId() {
        return otherId;
    }

    public void setOtherId(int operation) {
        this.otherId = otherId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String operation) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", userid=" + userId +
                ", date='" + date + '\'' +
                ", sum=" + sum +
                ", cvv=" + cvv +
                ", otherid='" + otherId +
                ", comment='" + comment + '\'' +
                '}';
    }
}
