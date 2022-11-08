package com.baishakhee.youtubevideovioewexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.baishakhee.youtubevideovioewexample.databinding.ActivityMainBinding;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    String link="https://www.youtube.com/watch?v=vImoWYQ1OFg";
    private static final String TAG="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.youTubeVideoView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId =  link.split("v=")[1].substring(0, 11);;
                youTubePlayer.loadVideo(videoId, 0);
            }

        });


        binding.sharedValueSet.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.youTubeVideoView.release();
    }
}