package com.zxzhang.gallery.data;

/**
 * Created by 张昭锡 on 2018/5/25.
 */

public class AlbumBean {
    private String albumFolderName;
    private String albumFolderPath;
    private int imageAmount;

    public String getAlbumFolderName() {
        return albumFolderName;
    }

    public void setAlbumFolderName(String albumFolderName) {
        this.albumFolderName = albumFolderName;
    }

    public String getAlbumFolderPath() {
        return albumFolderPath;
    }

    public void setAlbumFolderPath(String albumFolderPath) {
        this.albumFolderPath = albumFolderPath;
    }

    public int getImageAmount() {
        return imageAmount;
    }

    public void setImageAmount(int imageAmount) {
        this.imageAmount = imageAmount;
    }
}
