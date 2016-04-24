package com.github.adepalatis.ee461.recipez;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by michael on 4/20/16.
 */
public class Ingredient implements Parcelable {
    private String aisle = null;
    private String name = null;
    private String unit = null;
    private String originalString = null;
    private String image = null;
    private Double amount = null;

    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOriginalString() {
        return originalString;
    }

    public void setOriginalString(String originalString) {
        this.originalString = originalString;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient(Parcel p) {
        aisle = p.readString();
        name = p.readString();
        unit = p.readString();
        originalString = p.readString();
        image = p.readString();
        amount = p.readDouble();
    }

    public String getName() {
        if (name != null) {
            return name;
        }
        return "";
    }

    @Override
    public String toString() {
        String s = "{\n";

        if (aisle != null) {
            s += "\taisle: " + aisle + "\n";
        }

        if (name != null) {
            s += "\tname: " + name + "\n";
        }

        if (amount != null) {
            s += "\tamount: " + amount + "\n";
        }

        if (unit != null) {
            s += "\tunit: " + unit + "\n";
        }

        if (originalString != null) {
            s += "\toriginalString: " + originalString + "\n";
        }

        if (image != null) {
            s += "\timage: " + image + "\n";
        }

        return s + "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(aisle);
        dest.writeString(name);
        dest.writeString(unit);
        dest.writeString(originalString);
        dest.writeString(image);
        dest.writeDouble(amount);
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
