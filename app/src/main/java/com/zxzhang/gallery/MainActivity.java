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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zxzhang.gallery.adapter.AlbumAdapter;
import com.zxzhang.gallery.data.AlbumBean;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private final int SCAN_OK = 1;
    private Toolbar mToolbar;
    private HashMap<String,List<String>>mAlbumMap = new HashMap<>();
    private RecyclerView mRvAlubum;
    private AlbumAdapter albumAdapter;
    private List<AlbumBean>mAlbumList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mRvAlubum = (RecyclerView)findViewById(R.id.rv_album);

        requestPermissions();

        getAlbums();

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case SCAN_OK:
                    Log.d(TAG, "handleMessage: size = " + mAlbumMap.size());
                    GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);
                    mRvAlubum.setLayoutManager(layoutManager);
                    mAlbumList = getAlbumInfoToList(mAlbumMap);
                    albumAdapter = new AlbumAdapter(mAlbumList,mAlbumMap);
                    mRvAlubum.setAdapter(albumAdapter);
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    private void requestPermissions(){
        RxPermissions rxPermissions = new RxPermissions(MainActivity.this);
        rxPermissions
                .requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted){
                            Log.d(TAG, "accept: 成功");
                            Toast.makeText(MainActivity.this,"权限授予成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Log.d(TAG, "accept: 失败");
                            Toast.makeText(MainActivity.this,"权限授予失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void getAlbums(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri mImageMediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = MainActivity.this.getContentResolver();

                Cursor mCursor = mContentResolver.query(mImageMediaUri,null,MediaStore.Images.Media.MIME_TYPE+
                        "=? or "+MediaStore.Images.Media.MIME_TYPE+ "=?",new String[]{"image/jpeg","image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);

                while (mCursor.moveToNext()){
                    String imagePath = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    String albumName = new File(imagePath).getParentFile().getName();
                    Log.d(TAG, "run: imagePath = " + imagePath);
                    Log.d(TAG, "run: albumName = " + albumName);
                    if (!mAlbumMap.containsKey(albumName)){
                        List<String>imagesList = new ArrayList<>();
                        imagesList.add(imagePath);
                        mAlbumMap.put(albumName,imagesList);
                    }else{
                        mAlbumMap.get(albumName).add(imagePath);
                    }
                    Log.d(TAG, "run: size = " + mAlbumMap.size());
                }
                mCursor.close();

                handler.sendEmptyMessage(SCAN_OK);

                Log.d(TAG, "run: size = " + mAlbumMap.size());
            }
        }).start();
    }



    private List<AlbumBean>getAlbumInfoToList(HashMap<String,List<String>>albumMap){
        if (mAlbumMap.size() == 0){
            Log.d(TAG, "getAlbumInfoToList: zyzhang size = " + mAlbumMap.size());
            return null;
        }
        List<AlbumBean>albumList = new ArrayList<>();
        Iterator<Map.Entry<String,List<String>>>iterator = albumMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,List<String>>entry = iterator.next();
            AlbumBean albumBean = new AlbumBean();
            String key = entry.getKey();
            List<String>value = entry.getValue();
            albumBean.setAlbumFolderName(key);
            albumBean.setAlbumFolderPath(value.get(0));
            Log.d(TAG, "getAlbumInfoToList: " + albumBean.getAlbumFolderPath());
            Log.d(TAG, "getAlbumInfoToList: " + albumBean.getAlbumFolderName());
            albumBean.setImageAmount(value.size());
            albumList.add(albumBean);
        }
        return albumList;
    }

}
