package com.baishakhee.youtubevideovioewexample;

import androidx.annotation.NonNull;

public class VideoModel {
 String id,name,videoUrl,image;

    public VideoModel(String id, String name, String videoUrl, String image) {
        this.id = id;
        this.name = name;
        this.videoUrl = videoUrl;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
