package com.robby.baking_app.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Robby on 9/2/2017.
 *
 * @author Robby Tan
 */

public class RecipeStep implements Parcelable{

    public static final String TAG_ID = "id";
    public static final String TAG_SHORT_DESC = "shortDescription";
    public static final String TAG_DESC = "description";
    public static final String TAG_VIDEO_URL = "videoURL";
    public static final String TAG_THUMBNAIL_URL = "thumbnailURL";

    private String id;
    private String shortDescription;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;

    public RecipeStep() {
    }

    private RecipeStep(Parcel in) {
        id = in.readString();
        shortDescription = in.readString();
        description = in.readString();
        videoUrl = in.readString();
        thumbnailUrl = in.readString();
    }

    public static final Creator<RecipeStep> CREATOR = new Creator<RecipeStep>() {
        @Override
        public RecipeStep createFromParcel(Parcel in) {
            return new RecipeStep(in);
        }

        @Override
        public RecipeStep[] newArray(int size) {
            return new RecipeStep[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(shortDescription);
        parcel.writeString(description);
        parcel.writeString(videoUrl);
        parcel.writeString(thumbnailUrl);
    }
}
