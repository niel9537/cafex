package com.p3lb.cafex.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ImageModel {
    Context context;

    public ImageModel(Context context) {
        this.context = context;
    }

    public MultipartBody.Part multipartBody(Uri uri){
        String fileLoc = getRealPathFromUri(context, uri);
        File file = new File(fileLoc);
        RequestBody requestFile = RequestBody.create( MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("images[]", file.getName(), requestFile);

        return body;
    }

    public RequestBody requestBody(String nama){
        String descriptionString = nama;
        RequestBody description = RequestBody.create( okhttp3.MultipartBody.FORM, descriptionString);
        return description;
    }

    static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
