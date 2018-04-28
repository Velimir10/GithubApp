package model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GitHubUser implements Parcelable {


    @SerializedName("avatar_url")
    private String avatar;

    @SerializedName("name")
    private String name;

    @SerializedName("login")
    private String login;

    @SerializedName("location")
    private String location;

    @SerializedName("bio")
    private String bio;

    public GitHubUser(String avatar, String name, String login, String location, String bio) {
        this.avatar = avatar;
        this.name = name;
        this.login = login;
        this.location = location;
        this.bio = bio;
    }

    protected GitHubUser(Parcel in) {
        avatar = in.readString();
        name = in.readString();
        login = in.readString();
        location = in.readString();
        bio = in.readString();
    }

    public static final Creator<GitHubUser> CREATOR = new Creator<GitHubUser>() {
        @Override
        public GitHubUser createFromParcel(Parcel in) {
            return new GitHubUser(in);
        }

        @Override
        public GitHubUser[] newArray(int size) {
            return new GitHubUser[size];
        }
    };

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(avatar);
        dest.writeString(name);
        dest.writeString(login);
        dest.writeString(location);
        dest.writeString(bio);

    }
}
