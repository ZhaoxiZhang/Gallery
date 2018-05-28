package com.zxzhang.gallery.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 张昭锡 on 2018/5/28.
 */

public class MediaBean implements Parcelable{
    private String path;
    private String mimeType;
    private String size;
    private String dispalyName;
    private String dateTaken;
    private String dateModified;
    private String description;

    public MediaBean(){}

    protected MediaBean(Parcel in) {
        path = in.readString();
        mimeType = in.readString();
        size = in.readString();
        dispalyName = in.readString();
        dateTaken = in.readString();
        dateModified = in.readString();
        description = in.readString();
    }

    public static final Creator<MediaBean> CREATOR = new Creator<MediaBean>() {
        @Override
        public MediaBean createFromParcel(Parcel in) {
            return new MediaBean(in);
        }

        @Override
        public MediaBean[] newArray(int size) {
            return new MediaBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(mimeType);
        dest.writeString(size);
        dest.writeString(dispalyName);
        dest.writeString(dateTaken);
        dest.writeString(dateModified);
        dest.writeString(description);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDispalyName() {
        return dispalyName;
    }

    public void setDispalyName(String dispalyName) {
        this.dispalyName = dispalyName;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
