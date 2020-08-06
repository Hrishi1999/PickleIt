package gridentertainment.net.projectpickle.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchItem implements Parcelable {

    public String name;
    public String itemID;
    public String items;
    public String image;
    public String tags;
    public String sellerID;
    public String ratings;
    public String address;
    public String phone;

    public SearchItem(Parcel in) {
        name = in.readString();
        itemID = in.readString();
        itemID = in.readString();
        items = in.readString();
        sellerID = in.readString();
        tags = in.readString();
        ratings = in.readString();
        address = in.readString();
        phone = in.readString();
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

    public SearchItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(itemID);
        parcel.writeString(items);
        parcel.writeString(sellerID);
        parcel.writeString(ratings);
    }
}
