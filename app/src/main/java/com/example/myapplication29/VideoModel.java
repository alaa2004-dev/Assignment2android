package com.example.myapplication29;

public class VideoModel {
    private String videoId;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String publishTime;
    private String channelTitle;

    public VideoModel(String videoId, String title, String description, String thumbnailUrl, String publishTime, String channelTitle) {
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.publishTime = publishTime;
        this.channelTitle = channelTitle;
    }

    public String getVideoId() { return videoId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public String getPublishTime() { return publishTime; }
    public String getChannelTitle() { return channelTitle; }
}