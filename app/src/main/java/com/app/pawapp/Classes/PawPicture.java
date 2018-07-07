package com.app.pawapp.Classes;

import android.graphics.Bitmap;
import android.net.Uri;

public class PawPicture {

    private Uri uri;
    private String type;
    private Bitmap image;

    public PawPicture() {
    }

    public PawPicture(Uri uri, String type) {
        this.uri = uri;
        this.type = type;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
