package cn.edu.pku.gofish.Model;

<<<<<<< HEAD
import android.preference.PreferenceActivity;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.*;
import java.util.ArrayList;

=======
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

>>>>>>> 102f61fafd3d1fff7b5ace64eff90aa8933e9075
/**
 * Created by leonardo on 16/5/11.
 */
public class Record {
<<<<<<< HEAD
    private String title;
    private String describetext;
    private String price;
    private String type;
=======
    private int ID;
    private int user_id;
    int number;
    private String title;
    private String describetext;
    private float price;
>>>>>>> 102f61fafd3d1fff7b5ace64eff90aa8933e9075
    private ArrayList<String> imagePaths;
    private int imgcnt;
    private String usrname;
    private File[] file;
<<<<<<< HEAD
    AsyncHttpClient client=new AsyncHttpClient();

    public Record(String _usrname,String _title,String _describetext,String _pricetext,String _type,ArrayList<String> _imagePaths)
    {
        title=_title;
        describetext=_describetext;
        price=_pricetext;
        type=_type;
        imagePaths=_imagePaths;
        usrname=_usrname;
        //file=new File[9];
        imgcnt=imagePaths.size();
    }

    public void uploadFile(String url) throws Exception {
        int count =0;
        RequestParams params = new RequestParams();
        for(String tmp:imagePaths) {
            file[count] = new File(tmp);
            if (file[count].exists() && file[count].length() > 0) {
                count++;
            } else {
                throw new Exception("FileNotFoundException");
            }
        }
        params.put("uploadfiles",file);
        params.put("price",price);
        params.put("title",title);
        params.put("type",type);
        params.put("usrname",usrname);
        params.put("describetext",describetext);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, PreferenceActivity.Header[] headers, byte[] bytes) {

            }

            @Override
            public void onFailure(int i, PreferenceActivity.Header[] headers, byte[] bytes, Throwable throwable) throws Exception{
                throw new Exception("UploadFailException");
            }
            });
    }
=======
    AsyncHttpClient client = new AsyncHttpClient();

    private String url = "/api/items";
    public Record(int id)
    {
        ID = id;
    }
    public Record(String _usrname, String _title, String _describetext, float _pricetext, int _number,ArrayList<String> _imagePaths) {
        title = _title;
        describetext = _describetext;
        price = _pricetext;
        imagePaths = _imagePaths;
        usrname = _usrname;
        number = _number;
        //file=new File[9];
        imgcnt = imagePaths.size();
    }

    public void uploadFile(String url) throws Exception {
        int count = 0;
        RequestParams params = new RequestParams();
        for (String tmp : imagePaths) {
            file[count] = new File(tmp);
            if (file[count].exists() && file[count].length() > 0) {
                count++;
            } else {
                throw new Exception("FileNotFoundException");
            }
        }
        params.put("image", file);
        params.put("price", price);
        params.put("title", title);
        params.put("number", number);
        params.put("usrname", usrname);
        params.put("describetext", describetext);
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes) {

            }

            @Override
            public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable){

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
}
>>>>>>> 102f61fafd3d1fff7b5ace64eff90aa8933e9075
