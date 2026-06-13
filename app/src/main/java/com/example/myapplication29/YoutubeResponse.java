package com.example.myapplication29;

import java.util.List;

public class YoutubeResponse {
    private List<VideoItem> items;

    public List<VideoItem> getItems() { return items; }

    public static class VideoItem {
        private Id id;
        private Snippet snippet;

        public Id getId() { return id; }
        public Snippet getSnippet() { return snippet; }
    }

    public static class Id {
        private String videoId;
        public String getVideoId() { return videoId; }
    }

    public static class Snippet {
        private String title;
        private String description;
        private String publishedAt;
        private String channelTitle;
        private Thumbnails thumbnails;

        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public String getPublishedAt() { return publishedAt; }
        public String getChannelTitle() { return channelTitle; }
        public Thumbnails getThumbnails() { return thumbnails; }
    }

    public static class Thumbnails {
        private Thumbnail high;

        public Thumbnail getHigh() { return high; }
    }

    public static class Thumbnail {
        private String url;

        public String getUrl() { return url; }
    }
}
