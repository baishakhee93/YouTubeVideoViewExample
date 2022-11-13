package com.baishakhee.youtubevideovioewexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baishakhee.youtubevideovioewexample.databinding.ActivityMainBinding;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @SuppressLint("StaticFieldLeak")
    public static String videoJson="";
    @SuppressLint("StaticFieldLeak")
    public static List<VideoModel> videoModels=new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    public static VideoRecyclerViewAdapter videoRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        gettingStudentDetails();
        // bind RecyclerView
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
       binding.viewVedio.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(MainActivity.this,YouTubeVideoViewActivity.class);
               startActivity(intent);
           }
       });

    }
    private void gettingStudentDetails(){
        String json = null;
        try {
            InputStream inputStream = getApplicationContext().getAssets().open("videoDetails.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
            try {
                JSONObject jsonObject=new JSONObject(json);
                JSONObject json1=jsonObject.getJSONObject("data");
                JSONArray jsonArray=json1.getJSONArray("Video");
                videoJson=String.valueOf(jsonArray);
                callingVideoList();

            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Information Getting Null", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void callingVideoList(){
        try{
            if(videoJson==null||videoJson.trim().length()==0||videoJson.equalsIgnoreCase("null")){
                return;
            }
            videoModels=new ArrayList<>();
            binding.recyclerView.setAdapter(null);
            JSONArray jsonArray=new JSONArray(videoJson);
            if(jsonArray.length()>0){
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    videoModels.add(new VideoModel(jsonObject.getString("id"),jsonObject.getString("Name"),jsonObject.getString("url"),
                            jsonObject.getString("image")));
                }
                 videoRecyclerViewAdapter=new VideoRecyclerViewAdapter(getApplicationContext(),videoModels);
                binding.recyclerView.setAdapter(videoRecyclerViewAdapter);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}