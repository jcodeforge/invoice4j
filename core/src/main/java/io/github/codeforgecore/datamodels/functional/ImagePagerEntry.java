package io.github.codeforgecore.datamodels.functional;

import javax.swing.*;

public class ImagePagerEntry {

    private ImageIcon mImgIcon;
    private String mTxtTitle;
    private String mImgPath;

    private ImagePagerEntry(ImageIcon imgIcon, String imgPath, String txtTitle) {
        mImgIcon = imgIcon;
        mImgPath = imgPath;
        mTxtTitle = txtTitle;
    }

    public ImageIcon getImgIcon() {
        return mImgIcon;
    }

    public void setImgIcon(ImageIcon imgIcon) {
        mImgIcon = imgIcon;
    }

    public String getTxtTitle() {
        return mTxtTitle;
    }

    public void setTxtTitle(String txtTitle) {
        mTxtTitle = txtTitle;
    }

    public String getImgPath() {
        return mImgPath;
    }

    public void setImgPath(String imgPath) {
        mImgPath = imgPath;
    }

    public static ImagePagerEntry create(ImageIcon imgIcon, String imgPath, String txtTitle) {
        return new ImagePagerEntry(imgIcon, imgPath, txtTitle);
    }
}
