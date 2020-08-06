package gridentertainment.net.projectpickleseller.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class SellersItems implements Parcelable {

    public String name;
    public String original_price;
    public String price;
    public String image;
    public String tags;
    public String itm_id;

    public SellersItems(Parcel in) {
        name = in.readString();
        tags = in.readString();
        original_price = in.readString();
        price = in.readString();
        image = in.readString();
        itm_id = in.readString();
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

    public String getItm_id() {
        return itm_id;
    }

    public void setItm_id(String itm_id) {
        this.itm_id = itm_id;
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
        parcel.writeString(itm_id);

    }
}
