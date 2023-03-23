package sample.classes.descent;

import java.io.Serializable;

public class Stock extends Virtual implements Serializable {
    private String company;
    private int amount;

    public Stock(int userId, String date, int sum, int cvv, String company, int amount) {
        super(userId,date,sum,cvv);
        this.company = company;
        this.amount = amount;
    }

    public Stock(int id, int userId, String date, int sum, int cvv, String company, int amount) {
        super(id,userId,date,sum,cvv);
        this.company = company;
        this.amount = amount;
    }

    public Stock() {}

    public String getCompany() {
        return company;
    }

    public void setCompany(String operation) {
        this.company = company;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int operation) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", userid=" + userId +
                ", date='" + date + '\'' +
                ", sum=" + sum +
                ", cvv=" + cvv +
                ", company='" + company + '\'' +
                ", amount='" + amount +
                '}';
    }
}
