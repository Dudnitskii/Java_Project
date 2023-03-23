package sample.classes.descent;

import java.io.Serializable;

public class SellJw extends Real implements Serializable {
    private String item;
    private int weight;

    public SellJw(int userId, String date, int sum, String address, String item, int weight) {
        super(userId,date,sum,address);
        this.item = item;
        this.weight = weight;
    }

    public SellJw(int id, int userId, String date, int sum, String address, String item, int weight) {
        super(id,userId,date,sum,address);
        this.item = item;
        this.weight = weight;
    }

    public SellJw() {}

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "SellJw{" +
                "id=" + id +
                ", userid=" + userId +
                ", date='" + date + '\'' +
                ", sum=" + sum +
                ", address=" + address + '\'' +
                ", item='" + item + '\'' +
                ", weight='" + weight +
                '}';
    }
}
