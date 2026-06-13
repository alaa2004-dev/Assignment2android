package com.example.myapplication29;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeApiService {

    @GET("search?part=snippet&type=video")
    Call<YoutubeResponse> searchVideos(
            @Query("q") String query,
            @Query("maxResults") int maxResults,
            @Query("key") String apiKey
    );
}