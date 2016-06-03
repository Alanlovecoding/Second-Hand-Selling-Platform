package cn.edu.pku.gofish.Model;


import android.util.Log;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.*;
import java.util.ArrayList;



import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * Created by leonardo on 16/5/11.
 */
public class Record {

    private int ID;
    private int user_id;
    int number;
    private String title;
    private String describetext;
    private float price;
    private ArrayList<String> imagePaths;
    private int imgcnt;
    private String usrname;

    private String status;


    private Bitmap[] file;

    static AsyncHttpClient client = new AsyncHttpClient();

    private String url = "http://gofish.hackpku.com:8003/api/items";
    public Record(int id)
    {
        ID = id;
    }
    public Record(int user_id, String _title, String _describetext, float _pricetext, int _number,ArrayList<String> _imagePaths,String status) {
        title = _title;
        describetext = _describetext;
        price = _pricetext;

        this.user_id = user_id;
        number = _number;
        //file=new File[9];
        if(imagePaths!=null) {
            imagePaths = _imagePaths;
            imgcnt = imagePaths.size();
        }
        else {
            imgcnt = 0;
            imagePaths = new ArrayList<String>();

        }
        this.status =  status;
    }

    public void uploadFile() throws Exception {
        int count = 0;
        FileInputStream fis=null;
        RequestParams params = new RequestParams();
        for (String tmp : imagePaths) {
            try {
                fis=new FileInputStream(tmp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            file[count] = bitmap;
            if (file[count]!=null) {
                count++;
            } else {
                throw new Exception("FileNotFoundException");
            }
        }
        params.put("title", title);
        params.put("number", number);
        //params.put("user_id", usrname);

        params.put("price", price);
        params.put("description", describetext);
        //params.put("image_file", image_file);
        params.put("status",status);
        Log.d("NET", "Record post begin"+params.toString());
        client.setBasicAuth("username","password/token");
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("NET","Record post done");

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable){
                Log.d("NET","Record post failed"+bytes);
            }
        });
    }

    public void downloadFile()
    {

        client.get(url + "/"+ID, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    user_id = Integer.parseInt(response.getString("user_id"));
                    number = Integer.parseInt(response.getString("number"));
                    price = Float.parseFloat(response.getString("price"));
                    String description = response.getString("description");
                    describetext = description;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

            }
        });

    }

    public String TextLine()
    {
        return usrname;
    }
    public int getID(){return ID;}
    public String getTitle(){return title;}
    public String getDescribetext(){return describetext;}
    public Bitmap[] getFile(){return file;}
    public int getImgcnt(){return imgcnt;}
}
