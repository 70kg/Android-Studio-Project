package com.com.mr_wrong.AsyncTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Mr_Wrong on 2015/5/1.
 */
public class MyAsyncTask extends AsyncTask<String, Integer, Bitmap> {
    private ImageView imageView;
    private ProgressBar progressBar;

    public MyAsyncTask(ImageView imageView, ProgressBar progressBar) {
        this.imageView = imageView;
        this.progressBar = progressBar;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(params[0]);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                inputStream = httpResponse.getEntity().getContent();
                //获取总长度
                long file_length = httpResponse.getEntity().getContentLength();
                int len = 0;
                byte[] data = new byte[1024];
                int total_length = 0;
                int value = 0;
                while ((len = inputStream.read(data)) != -1) {
                    if (this.isCancelled()) {
                        break;
                    }
                    total_length += len;
                    value = (int) (total_length / file_length * 100);
                    publishProgress(value);
                    outputStream.write(data, 0, len);
                }
                Thread.sleep(500);
                byte[] result = outputStream.toByteArray();
                bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
                inputStream.close();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {

        }
        return bitmap;
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if(this.isCancelled()){
            return;
        }
        progressBar.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
        progressBar.setVisibility(View.GONE);
    }
}
