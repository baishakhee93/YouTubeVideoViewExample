package com.baishakhee.youtubevideovioewexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baishakhee.youtubevideovioewexample.databinding.ActivityYouTubeVideoViewBinding;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class YouTubeVideoViewActivity extends AppCompatActivity {
    ActivityYouTubeVideoViewBinding binding;
   // String link="https://www.youtube.com/watch?v=vImoWYQ1OFg";
    String link="https://www.youtube.com/watch?v=S3FKIcL-58g";
    private static final String TAG="YouTubeVideoViewActivity";

    String videoName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_you_tube_video_view);

        gettingData();

        binding.youTubeVideoView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId =  link.split("v=")[1].substring(0, 11);;
                youTubePlayer.loadVideo(videoId, 0);
            }

        });
        binding.videoTitle.setText(videoName);


        binding.sharedValueSet.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                final String appPackageName = getPackageName();

                Log.e(TAG, "app package name" + appPackageName.toString());

                List<Intent> targetedShareIntents = new ArrayList<Intent>();
                Intent sendIntent = new Intent();
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Video Link");
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, link);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(sendIntent, 0);

                if (!resInfo.isEmpty()) {
                    for (ResolveInfo info : resInfo) {
                        Intent targetedShare = new Intent(Intent.ACTION_SEND);
                        targetedShare.setType("text/plain"); // put here your mime type
                        targetedShare.setPackage(info.activityInfo.packageName.toLowerCase());
                        targetedShareIntents.add(targetedShare);
                    }

                }

            }
        });
    }
    private void gettingData() {
        try {
            System.out.println("Print...........getdata....0000............");

            Intent intent = getIntent();
            if (intent != null) {
                if (intent.getSerializableExtra("videoLink") != null) {
                    link=intent.getStringExtra("videoLink");
                    videoName=intent.getStringExtra("videoTitle");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}