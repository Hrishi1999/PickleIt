package gridentertainment.net.projectpickleseller.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable {

    public String name;
    public String tags;
    public String address;
    public String city;
    public String phone;
    public String email;
    public String image;

    public Profile(Parcel in) {
        name = in.readString();
        tags = in.readString();
        address = in.readString();
        city = in.readString();
        email = in.readString();
        phone = in.readString();
        image = in.readString();
    }

    public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    public Profile() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(tags);
        parcel.writeString(city);
        parcel.writeString(image);
        parcel.writeString(phone);
        parcel.writeString(email);
    }

}
