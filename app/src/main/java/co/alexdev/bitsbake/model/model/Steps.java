package co.alexdev.bitsbake.model.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import co.alexdev.bitsbake.BR;
import co.alexdev.bitsbake.model.response.Recipe;

@Entity(indices = {@Index(value = {"id"}, unique = true)})
public class Steps extends BaseObservable {

    @PrimaryKey
    @ForeignKey(entity = Recipe.class, parentColumns = "id", childColumns = "id")
    private int id;
    private String cake;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailUrl;

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getCake() {
        return cake;
    }

    public void setCake(String cake) {
        this.cake = cake;
    }

    @Bindable
    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        notifyPropertyChanged(BR.shortDescription);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
        notifyPropertyChanged(BR.videoURL);
    }

    @Bindable
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        notifyPropertyChanged(BR.thumbnailUrl);
    }

    @Override
    public String toString() {
        return "Steps{" +
                "id=" + id +
                ", cake='" + cake + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                '}';
    }
}
