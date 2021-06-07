package com.example.myapplication.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String nickName;

    protected User(Parcel in) {
        nickName = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getNickName() {
        return nickName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nickName);
    }
}
