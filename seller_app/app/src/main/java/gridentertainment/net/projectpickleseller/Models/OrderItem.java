package gridentertainment.net.projectpickleseller.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderItem implements Parcelable {

    public String name;
    public String quantity;
    public String price;
    public String itemID;
    public String orderID;
    public String items;
    public String status;
    public String userID;

    public OrderItem(Parcel in) {
        name = in.readString();
        quantity = in.readString();
        itemID = in.readString();
        orderID = in.readString();
        price = in.readString();
        itemID = in.readString();
        items = in.readString();
        status = in.readString();
        userID = in.readString();
    }

    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };

    public OrderItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getuserID() {
        return userID;
    }

    public void setuserID(String userID) {
        this.userID = userID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(status);
        parcel.writeString(price);
        parcel.writeString(itemID);
        parcel.writeString(quantity);
        parcel.writeString(orderID);
        parcel.writeString(items);
        parcel.writeString(userID);
    }
}
