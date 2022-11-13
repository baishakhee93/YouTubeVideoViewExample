package com.baishakhee.youtubevideovioewexample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.Serializable;
import java.util.List;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.ViewHolder>{

    private final List<VideoModel> videoModelList;
    private final Context context;

    public VideoRecyclerViewAdapter(Context applicationContext, List<VideoModel> videoModels) {
        this. context=applicationContext;
        this.videoModelList=videoModels;
    }


    @NonNull
    @Override
    public VideoRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull VideoRecyclerViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RequestOptions requestOptions=new RequestOptions();
        holder.videoTitle.setText(videoModelList.get(position).name);
        String imageUrl = videoModelList.get(position).image;
        requestOptions.placeholder(R.drawable.ic_baseline_hide_image_24);
        requestOptions.error(R.drawable.ic_baseline_hide_image_24);
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(imageUrl).into(holder.imageView);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,YouTubeVideoViewActivity.class);
                // intent.putExtra("videoList", (Serializable) videoModelList);
                intent.putExtra("videoLink",videoModelList.get(position).videoUrl);
                intent.putExtra("videoTitle",videoModelList.get(position).name);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView videoTitle;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.linearLayout);
            imageView=itemView.findViewById(R.id.videoImage);
            videoTitle=itemView.findViewById(R.id.video_title);
        }
    }
}