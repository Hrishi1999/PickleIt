package gridentertainment.net.projectpickleseller.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class SellerItem implements Parcelable {

    public String name;
    public String ratings;
    public String tags;
    public String image;
    public String userID;

    public SellerItem(Parcel in) {
        name = in.readString();
        tags = in.readString();
        ratings = in.readString();
        image = in.readString();
        userID = in.readString();

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
        parcel.writeString(userID);
    }
}
