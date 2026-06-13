package com.example.myapplication29;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText etSearchQuery;
    private Button btnSearch;
    private ProgressBar progressBar;
    private RecyclerView recyclerViewResults;

    private VideoAdapter videoAdapter;
    private List<VideoModel> videoList = new ArrayList<>();

    private final String API_KEY = "AIzaSyDdi0EtA3JUg2LAnTYrqxW7bn-nYdj25Wk";

    private final String BASE_URL = "https://www.googleapis.com/youtube/v3/";

    private YoutubeApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearchQuery = findViewById(R.id.etSearchQuery);
        btnSearch = findViewById(R.id.btnSearch);
        progressBar = findViewById(R.id.progressBar);
        recyclerViewResults = findViewById(R.id.recyclerViewResults);

        recyclerViewResults.setLayoutManager(new LinearLayoutManager(this));
        videoAdapter = new VideoAdapter(videoList);
        recyclerViewResults.setAdapter(videoAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(YoutubeApiService.class);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = etSearchQuery.getText().toString().trim();

                if (query.isEmpty()) {
                    etSearchQuery.setError("الرجاء إدخال كلمة للبحث!");
                    Toast.makeText(MainActivity.this, "خانة البحث فارغة", Toast.LENGTH_SHORT).show();

                    videoList.clear();
                    videoAdapter.notifyDataSetChanged();

                } else {
                    performSearch(query);
                }
            }
        });
    }

    private void performSearch(String query) {
        progressBar.setVisibility(View.VISIBLE);

        videoList.clear();
        videoAdapter.notifyDataSetChanged();

        Call<YoutubeResponse> call = apiService.searchVideos(query, 10, API_KEY);

        call.enqueue(new Callback<YoutubeResponse>() {
            @Override
            public void onResponse(Call<YoutubeResponse> call, Response<YoutubeResponse> response) {

                progressBar.setVisibility(View.GONE);

                if (!response.isSuccessful() || response.body() == null) {
                    String googleServerError = "خطأ في الخادم أو مفتاح الـ API غير صحيح";
                    if (response.errorBody() != null) {
                        try {
                            googleServerError = response.errorBody().string();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(MainActivity.this, googleServerError, Toast.LENGTH_LONG).show();
                    return;
                }

                List<YoutubeResponse.VideoItem> items = response.body().getItems();

                if (items == null || items.isEmpty()) {
                    Toast.makeText(MainActivity.this, "لم يتم العثور على أي نتائج للبحث!", Toast.LENGTH_LONG).show();
                    return;
                }

                for (YoutubeResponse.VideoItem item : items) {

                    String videoId = "";
                    if (item.getId() != null) {
                        videoId = item.getId().getVideoId();
                    }

                    YoutubeResponse.Snippet snippet = item.getSnippet();
                    if (snippet != null) {
                        String title = snippet.getTitle();
                        String description = snippet.getDescription();
                        String channelTitle = snippet.getChannelTitle();
                        String publishTime = snippet.getPublishedAt();

                        String thumbnailUrl = "";
                        if (snippet.getThumbnails() != null && snippet.getThumbnails().getHigh() != null) {
                            thumbnailUrl = snippet.getThumbnails().getHigh().getUrl();
                        }

                        videoList.add(new VideoModel(videoId, title, description, thumbnailUrl, publishTime, channelTitle));
                    }
                }
                videoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<YoutubeResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "فشل الاتصال بالشبكة! تأكد من إنترنت جهازك.", Toast.LENGTH_LONG).show();
            }
        });
    }
}