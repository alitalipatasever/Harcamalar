package com.alitalipatasever.harcamalar;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable
{

    @SerializedName("userEmail")
    @Expose
    private String userEmail;
    public final static Creator<User> CREATOR = new Creator<User>() {


        @SuppressWarnings({
                "unchecked"
        })
        public User createFromParcel(android.os.Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }

    }
            ;

    protected User(android.os.Parcel in) {
        this.userEmail = ((String) in.readValue((String.class.getClassLoader())));
    }

    public User() {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(userEmail);
    }

    public int describeContents() {
        return 0;
    }

}
