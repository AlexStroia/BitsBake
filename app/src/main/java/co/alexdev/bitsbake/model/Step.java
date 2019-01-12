package co.alexdev.bitsbake.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*Step model class*/
@Entity
public class Step implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int room_id;
    private int id;
    @NonNull
    private String cake;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailUrl;

    protected Step(Parcel in) {
        room_id = in.readInt();
        id = in.readInt();
        cake = in.readString();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailUrl = in.readString();
    }

    public Step() {}

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCake() {
        return cake;
    }

    public void setCake(String cake) {
        this.cake = cake;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", cake='" + cake + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(cake);
        parcel.writeString(shortDescription);
        parcel.writeString(cake);
        parcel.writeString(videoURL);
        parcel.writeString(thumbnailUrl);
    }
}
