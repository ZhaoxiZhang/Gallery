package com.zxzhang.gallery;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Toolbar mTbBar;
    private HashMap<String,List<String>>mGalleryMap = new HashMap<>();
    private RecyclerView mRvGalleryView;
    private GalleryAdapter adapter;
    private final static int SCAN_ON  = 1;


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SCAN_ON:
                    adapter = new GalleryAdapter(subImageFromGalleryMap(mGalleryMap),mRvGalleryView);
                    mRvGalleryView.setAdapter(adapter);
                    break;
                default:
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTbBar = (Toolbar)findViewById(R.id.tb_main_bar);
        setSupportActionBar(mTbBar);

        mRvGalleryView = (RecyclerView)findViewById(R.id.main_rv_galleryview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        mRvGalleryView.setLayoutManager(layoutManager);

        requestPermissions();

        getImages();
    }

    private void requestPermissions(){
        RxPermissions rxPermissions = new RxPermissions(MainActivity.this);
        rxPermissions
                .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted){

                        }
                    }
                });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.tb_settings:
                break;
            default:
        }
        return true;
    }

    private void getImages(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = MainActivity.this.getContentResolver();

                Cursor mCursor = mContentResolver.query(mImageUri,null,MediaStore.Images.Media.MIME_TYPE+
                        "=? or "+MediaStore.Images.Media.MIME_TYPE+ "=?",new String[]{"image/jpeg","image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);

                while (mCursor.moveToNext()){
                    String imagePath = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    String imageFolderPath = new File(imagePath).getParentFile().getName();
                    if (!mGalleryMap.containsKey(imageFolderPath)){
                        List<String>imagePathList = new ArrayList<String>();
                        imagePathList.add(imagePath);
                        mGalleryMap.put(imageFolderPath,imagePathList);
                    }else{
                        mGalleryMap.get(imageFolderPath).add(imagePath);
                    }
                }
                mCursor.close();
                mHandler.sendEmptyMessage(SCAN_ON);
            }
        }).start();
    }


    private List<ImageBean>subImageFromGalleryMap(HashMap<String,List<String>>mGalleryMap){
        List<ImageBean>imageList = new ArrayList<>();
        Iterator<Map.Entry<String,List<String>>>iterator = mGalleryMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,List<String>>entry = iterator.next();
            ImageBean imageBean = new ImageBean();
            String key = entry.getKey();
            List<String>value = entry.getValue();

            imageBean.setFolderPath(key);
            imageBean.setImagesCnt(value.size());
            imageBean.setTopImagePath(value.get(0));

            imageList.add(imageBean);
        }
        for (int i = 0;i < 10;i++){
            Log.d(TAG, "subImageFromGalleryMap: + " + imageList.get(i).getFolderPath());
        }
        return imageList;
    }
}
