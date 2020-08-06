package gridentertainment.net.projectpickle.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class SellerItem implements Parcelable {

    public String name;
    public String ratings;
    public String tags;
    public String image;
    public String phone;
    public String address;
    public String userID;
    public String dFee;
    public String city;

    public SellerItem(Parcel in) {
        name = in.readString();
        tags = in.readString();
        ratings = in.readString();
        image = in.readString();
        userID = in.readString();
        phone = in.readString();
        address = in.readString();
        dFee = in.readString();
        city = in.readString();
    }

    public static final Creator<SellerItem> CREATOR = new Creator<SellerItem>() {
        @Override
        public SellerItem createFromParcel(Parcel in) {
            return new SellerItem(in);
        }

        @Override
        public SellerItem[] newArray(int size) {
            return new SellerItem[size];
        }
    };

    public SellerItem() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String UserID) {
        this.userID = UserID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getdFee() {
        return dFee;
    }

    public void setdFee(String dFee) {
        this.dFee = dFee;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(ratings);
        parcel.writeString(tags);
        parcel.writeString(image);
        parcel.writeString(address);
        parcel.writeString(phone);
        parcel.writeString(userID);
        parcel.writeString(dFee);
    }
}
