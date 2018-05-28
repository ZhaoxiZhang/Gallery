package com.zxzhang.gallery.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxzhang.gallery.R;
import com.zxzhang.gallery.data.MediaBean;
import com.zxzhang.gallery.fragment.ImageFragment;
import com.zxzhang.gallery.util.UIUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ImageActivity extends AppCompatActivity {
    private static final String TAG = "ImageActivity";
    private Toolbar mToolbar;
    private Intent mIntent;
    private ViewPager mVpImage;
    private List<MediaBean>mMediaList;
    private int imagePosition;
    private MediaBean mCurrentMedia;
    private Context mCtxSingleMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        initView();
        initData();

        mVpImage.setAdapter(new ImageAdapter((getSupportFragmentManager())));
        mVpImage.setCurrentItem(imagePosition);

        mVpImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mToolbar.setTitle((position + 1) + " " + "/" + " " + mMediaList.size());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_details:
                loadDetails(getMainDetails());
            default:
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_pager,menu);
        return true;
    }


    private void initData(){
        mCtxSingleMedia = getApplicationContext();
        mIntent = getIntent();
        mMediaList = mIntent.getParcelableArrayListExtra("mediaList");
        imagePosition = mIntent.getIntExtra("position",0);
        mCurrentMedia = mMediaList.get(imagePosition);
    }

    private void initView(){
        mVpImage = (ViewPager)findViewById(R.id.vp_image);
        initToolbar();
    }

    private void initToolbar(){
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private LinkedHashMap<String,String>getMainDetails(){
        LinkedHashMap<String,String>mediaMainDetails = new LinkedHashMap<>();
        mediaMainDetails.put(mCtxSingleMedia.getString(R.string.path),mCurrentMedia.getPath());
        mediaMainDetails.put(mCtxSingleMedia.getString(R.string.mimetype),mCurrentMedia.getMimeType());
        return mediaMainDetails;
    }

    private void loadDetails(LinkedHashMap<String,String> mediaMainDetails){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this,android.R.style.Theme_Material_Dialog_NoActionBar);
        View dialogLayout = getLayoutInflater().inflate(R.layout.dialog_media_details,null,false);

        dialogLayout.findViewById(R.id.dialog_media_detials);
        alertDialogBuilder.setView(dialogLayout);

        LinearLayout mediaDetailsTable = (LinearLayout)dialogLayout.findViewById(R.id.ll_list_details);

        for (Map.Entry<String,String>mp : mediaMainDetails.entrySet()){
            LinearLayout row = new LinearLayout(mCtxSingleMedia);
            TextView label = new TextView(mCtxSingleMedia);
            TextView value = new TextView(mCtxSingleMedia);

            row.setOrientation(LinearLayout.HORIZONTAL);
            //row.setWeightSum(10);

            row.setBackgroundColor(ContextCompat.getColor(mCtxSingleMedia,R.color.md_grey_800));
            label.setBackgroundColor(ContextCompat.getColor(mCtxSingleMedia,R.color.md_grey_800));
            value.setBackgroundColor(ContextCompat.getColor(mCtxSingleMedia,R.color.md_grey_800));

            label.setText(mp.getKey());
            label.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,3f));
            label.setTextSize(15);
            label.setTextColor(ContextCompat.getColor(mCtxSingleMedia,R.color.md_grey_500));
            label.setGravity(Gravity.END);

            value.setText(mp.getValue());
            value.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,8f));
            value.setTextSize(15);
            value.setTextColor(ContextCompat.getColor(mCtxSingleMedia,R.color.md_grey_100));
            value.setPadding(UIUtils.pxToDp(10,mCtxSingleMedia),0,0,0);

            row.addView(label);
            row.addView(value);
            mediaDetailsTable.addView(row,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        }


        AlertDialog detailsDialog = alertDialogBuilder.create();

        detailsDialog.setTitle(mCurrentMedia.getDispalyName());
        detailsDialog.show();
    }



    private class ImageAdapter extends FragmentStatePagerAdapter{

        public ImageAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return ImageFragment.newInstance(mMediaList.get(position).getPath());
        }

        @Override
        public int getCount() {
            return mMediaList.size();
        }
    }
}
