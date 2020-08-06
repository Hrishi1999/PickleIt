package gridentertainment.net.projectpickle.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class SellersItems implements Parcelable {

    public String name;
    public String original_price;
    public String price;
    public String image;
    public String tags;
    public String itemID;
    public String quantity;
    public String userID;

    public SellersItems(Parcel in) {
        name = in.readString();
        tags = in.readString();
        original_price = in.readString();
        price = in.readString();
        image = in.readString();
        itemID = in.readString();
        quantity = in.readString();
        userID = in.readString();
    }

    public static final Creator<SellersItems> CREATOR = new Creator<SellersItems>() {
        @Override
        public SellersItems createFromParcel(Parcel in) {
            return new SellersItems(in);
        }

        @Override
        public SellersItems[] newArray(int size) {
            return new SellersItems[size];
        }
    };

    public SellersItems() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(original_price);
        parcel.writeString(price);
        parcel.writeString(tags);
        parcel.writeString(image);
        parcel.writeString(itemID);
        parcel.writeString(quantity);
        parcel.writeString(userID);
    }
}
