package model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GitHubFollower implements Parcelable {


    @SerializedName("login")
    private String login;


    public GitHubFollower(String login) {
        this.login = login;

    }

    protected GitHubFollower(Parcel in) {
        login = in.readString();
    }

    public static final Creator<GitHubFollower> CREATOR = new Creator<GitHubFollower>() {
        @Override
        public GitHubFollower createFromParcel(Parcel in) {
            return new GitHubFollower(in);
        }

        @Override
        public GitHubFollower[] newArray(int size) {
            return new GitHubFollower[size];
        }
    };

    public String getLogin() {
        return login;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
    }
}
