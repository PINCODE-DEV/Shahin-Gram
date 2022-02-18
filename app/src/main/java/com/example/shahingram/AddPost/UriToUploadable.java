package com.example.shahingram.AddPost;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UriToUploadable {

    private Activity context;

    public UriToUploadable(Activity context) {
        this.context = context;
    }

    private byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    public MultipartBody.Part getUploaderFile(Uri data, String sendKey, String fileName) {
        try {
            InputStream inputStream;
            if (data != null) {
                inputStream = context.getContentResolver().openInputStream(data);
            } else {
                inputStream = context.getContentResolver().openInputStream(Uri.parse(""));
            }
            byte[] bytes = getBytes(inputStream);
            String type = getFileExtension(data);
            RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), bytes);
            MultipartBody.Part file;
            file = MultipartBody.Part.createFormData(sendKey, fileName + "." + type, requestFile);
            Log.i("LOG", "getUploaderFile: "+file.toString());
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String convertMediaUriToPath(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }

}
