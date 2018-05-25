package com.zxzhang.gallery;

/**
 * Created by 张昭锡 on 2018/5/25.
 */

public class ImageBean {
    private String topImagePath;
    private String folderPath;
    private int imagesCnt;

    public String getTopImagePath() {
        return topImagePath;
    }

    public void setTopImagePath(String topImagePath) {
        this.topImagePath = topImagePath;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public int getImagesCnt() {
        return imagesCnt;
    }

    public void setImagesCnt(int imagesCnt) {
        this.imagesCnt = imagesCnt;
    }
}
