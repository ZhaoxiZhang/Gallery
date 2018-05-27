package com.zxzhang.gallery.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.zxzhang.gallery.R;
import com.zxzhang.gallery.adapter.PhotoAdapter;

import java.util.List;

public class PhotoActivity extends AppCompatActivity {
    private static final String TAG = "PhotoActivity";
    private List<String> mPhotoPathList;
    private Toolbar mToolbar;
    private Intent mIntent;
    private PhotoAdapter photoAdapter;
    private GridLayoutManager layoutManager;
    private RecyclerView mRvPhoto;
    private String mAlbumFolerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        mRvPhoto = (RecyclerView)findViewById(R.id.rv_photo);


        mIntent = getIntent();
        mPhotoPathList = mIntent.getStringArrayListExtra("photoPathList");
        mAlbumFolerName = mIntent.getStringExtra("albumFolderName");
        Log.d(TAG, "PhotoActivity onCreate: " + mPhotoPathList);

        initToolbar();

        layoutManager = new GridLayoutManager(PhotoActivity.this,3);
        photoAdapter = new PhotoAdapter(getApplicationContext(),mPhotoPathList);
        mRvPhoto.setLayoutManager(layoutManager);
        mRvPhoto.setAdapter(photoAdapter);
    }


    private void initToolbar(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle(mAlbumFolerName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }
}
