package model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class GitHubUserRepository implements Parcelable {

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("updated_at")
    private String updated;

    @SerializedName("language")
    private String language;

    public GitHubUserRepository(String name, String description, String updated, String language) {
        this.name = name;
        this.description = description;
        this.updated = updated;
        this.language = language;
    }

    protected GitHubUserRepository(Parcel in) {
        name = in.readString();
        description = in.readString();
        updated = in.readString();
        language = in.readString();
    }

    public static final Creator<GitHubUserRepository> CREATOR = new Creator<GitHubUserRepository>() {
        @Override
        public GitHubUserRepository createFromParcel(Parcel in) {
            return new GitHubUserRepository(in);
        }

        @Override
        public GitHubUserRepository[] newArray(int size) {
            return new GitHubUserRepository[size];
        }
    };

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }


    public String getUpdated() {
        return updated;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(updated);
        dest.writeString(language);
    }
}
