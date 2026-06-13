package com.example.myapplication29;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<VideoModel> videoList;

    public VideoAdapter(List<VideoModel> videoList) {
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoModel video = videoList.get(position);

        holder.tvTitle.setText(video.getTitle());
        holder.tvDescription.setText(video.getDescription());
        holder.tvPublishTime.setText("نُشر في: " + video.getPublishTime());
        holder.tvChannelTitle.setText("القناة: " + video.getChannelTitle());

        Glide.with(holder.itemView.getContext())
                .load(video.getThumbnailUrl())
                .placeholder(android.R.drawable.ic_menu_gallery) // صورة مؤقتة تظهر وقت التحميل
                .into(holder.ivThumbnail);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videoId = video.getVideoId();
                if (videoId != null && !videoId.isEmpty()) {
                    Context context = v.getContext();

                    String videoUrl = "https://www.youtube.com/watch?v=" + videoId;

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
                    context.startActivity(intent);
                } else {
                    Toast.makeText(holder.itemView.getContext(), "يتعذر فتح هذا الفيديو!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList != null ? videoList.size() : 0;
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        TextView tvTitle, tvDescription, tvPublishTime, tvChannelTitle;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPublishTime = itemView.findViewById(R.id.tvPublishTime);
            tvChannelTitle = itemView.findViewById(R.id.tvChannelTitle);
        }
    }
}