package myOrders;

import java.util.ArrayList;

/**
 * Created by Chengen on 2017-06-07.
 */

public class ProviderMyOrders {
    private String subTotal;
    private String tax;
    private String total;
    private String time;
    private String deliverFee;
    private ArrayList<String> itemNames;
    private ArrayList<String> itemAmounts;
    private ArrayList<String> itemPrices;

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<String> getItemNames() {
        return itemNames;
    }

    public String getDeliverFee() {
        return deliverFee;
    }

    public void setDeliverFee(String deliverFee) {
        this.deliverFee = deliverFee;
    }

    public void setItemNames(ArrayList<String> itemNames) {
        this.itemNames = itemNames;
    }

    public ArrayList<String> getItemAmounts() {
        return itemAmounts;
    }

    public void setItemAmounts(ArrayList<String> itemAmounts) {
        this.itemAmounts = itemAmounts;
    }

    public ArrayList<String> getItemPrices() {
        return itemPrices;
    }

    public void setItemPrices(ArrayList<String> itemPrices) {
        this.itemPrices = itemPrices;
    }
}
