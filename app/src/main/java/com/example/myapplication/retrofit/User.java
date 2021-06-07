package com.example.myapplication.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class User implements Parcelable {

    private String userId;
    private String nickName;
    private String passWd;
    private boolean state;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nickName);
    }

    public String getUserId() {
        return userId;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPassWd() {
        return passWd;
    }

    public boolean isState() {
        return state;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPassWd(String passWd) {
        this.passWd = passWd;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
